package com.github.myth.core.spi;

import com.github.myth.common.bean.entity.MythTransaction;
import com.github.myth.common.config.MythConfig;
import com.github.myth.common.exception.MythRuntimeException;
import com.github.myth.common.serializer.ObjectSerializer;

import java.util.Date;
import java.util.List;

public interface CoordinatorRepository {

    int create(MythTransaction mythTransaction);

    int remove(String transId);

    /**
     * 更新数据
     *
     * @param tccTransaction 事务对象
     */
    int update(MythTransaction tccTransaction) throws MythRuntimeException;


    /**
     * 更新事务失败日志
     * @param mythTransaction 实体对象
     * @return rows 1 成功
     * @throws MythRuntimeException 异常信息
     */
    int updateFailTransaction(MythTransaction mythTransaction) throws  MythRuntimeException;


    /**
     * 更新 List<Participant>  只更新这一个字段数据
     *
     * @param tccTransaction 实体对象
     * @return rows 1 成功
     * @throws MythRuntimeException 异常
     */
    int updateParticipant(MythTransaction tccTransaction) throws MythRuntimeException;


    /**
     * 更新补偿数据状态
     */
    int updateStatus(String transId, Integer status) throws MythRuntimeException;

    MythTransaction findByTransId(String transId);


    /**
     * 获取延迟多长时间后的事务信息,只要为了防止并发的时候，刚新增的数据被执行
     *
     * @param date 延迟后的时间
     * @return List<MythTransaction>
     */
    List<MythTransaction> listAllByDelay(Date date);


    /**
     * 初始化操作
     *
     * @param modelName  模块名称
     * @param mythConfig 配置信息
     * @throws MythRuntimeException 自定义异常
     */
    void init(String modelName, MythConfig mythConfig) throws MythRuntimeException;

    /**
     * 设置scheme
     *
     * @return scheme 命名
     */
    String getScheme();


    /**
     * 设置序列化信息
     *
     * @param objectSerializer 序列化实现
     */
    void setSerializer(ObjectSerializer objectSerializer);
}
