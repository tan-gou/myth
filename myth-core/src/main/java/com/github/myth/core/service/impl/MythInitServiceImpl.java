package com.github.myth.core.service.impl;

import com.github.myth.common.config.MythConfig;
import com.github.myth.common.enums.RepositorySupportEnum;
import com.github.myth.common.enums.SerializeEnum;
import com.github.myth.common.serializer.KryoSerializer;
import com.github.myth.common.serializer.ObjectSerializer;
import com.github.myth.common.utils.LogUtil;
import com.github.myth.common.utils.ServiceBootstrap;
import com.github.myth.core.coordinator.CoordinatorService;
import com.github.myth.core.disruptor.publisher.MythTransactionEventPublisher;
import com.github.myth.core.helper.SpringBeanUtils;
import com.github.myth.core.schedule.ScheduledService;
import com.github.myth.core.service.MythInitService;
import com.github.myth.core.spi.CoordinatorRepository;
import com.github.myth.core.spi.repository.JdbcCoordinatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@Service
public class MythInitServiceImpl implements MythInitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MythInitServiceImpl.class);

    private final CoordinatorService coordinatorService;

    private final MythTransactionEventPublisher publisher;

    @Autowired
    private ScheduledService scheduledService;

    @Autowired
    public MythInitServiceImpl(CoordinatorService coordinatorService, MythTransactionEventPublisher publisher) {
        this.coordinatorService = coordinatorService;
        this.publisher = publisher;
    }


    /**
     * Myth分布式事务初始化方法
     */
    @Override
    public void initialization(MythConfig mythConfig) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.error("系统关闭")));
        try {
            loadSpiSupport(mythConfig);
            publisher.start(mythConfig.getBufferSize());
            coordinatorService.start(mythConfig);
            //如果需要自动恢复 开启线程 调度线程池，进行恢复
            if (mythConfig.getNeedRecover()) {
                scheduledService.scheduledAutoRecover(mythConfig);
            }

        } catch (Exception ex) {
            LogUtil.error(LOGGER, "Myth事务初始化异常:{}", ex::getMessage);
            //非正常关闭
            System.exit(1);
        }
        LogUtil.info(LOGGER, () -> "Myth事务初始化成功！");
    }

    /**
     * 根据配置文件初始化spi
     */
    private void loadSpiSupport(MythConfig mythConfig) {

        //spi  serialize
        final SerializeEnum serializeEnum = SerializeEnum.acquire(mythConfig.getSerializer());

        final ServiceLoader<ObjectSerializer> objectSerializers = ServiceBootstrap.loadAll(ObjectSerializer.class);

        final ObjectSerializer serializer =
                StreamSupport.stream(objectSerializers.spliterator(),
                        true)
                        .filter(objectSerializer ->
                                Objects.equals(objectSerializer.getScheme(), serializeEnum.getSerialize()))
                        .findFirst()
                        .orElse(new KryoSerializer());

        coordinatorService.setSerializer(serializer);
        SpringBeanUtils.getInstance().registerBean(ObjectSerializer.class.getName(), serializer);

        //spi  repository support
        final RepositorySupportEnum repositorySupportEnum = RepositorySupportEnum.acquire(mythConfig.getRepositorySupport());

        final ServiceLoader<CoordinatorRepository> recoverRepositories = ServiceBootstrap.loadAll(CoordinatorRepository.class);


        final CoordinatorRepository repository =
                StreamSupport.stream(recoverRepositories.spliterator(), false)
                        .filter(recoverRepository ->
                                Objects.equals(recoverRepository.getScheme(),
                                        repositorySupportEnum.getSupport())).findFirst()
                        .orElse(new JdbcCoordinatorRepository());

        //将CoordinatorRepository实现注入到spring容器
        repository.setSerializer(serializer);
        SpringBeanUtils.getInstance().registerBean(CoordinatorRepository.class.getName(), repository);

    }
}
