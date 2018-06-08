package com.github.myth.core.bootstrap;

import com.github.myth.common.config.MythConfig;
import com.github.myth.core.helper.SpringBeanUtils;
import com.github.myth.core.service.MythInitService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class MythTransactionBootstrap extends MythConfig implements ApplicationContextAware {

    private MythInitService mythInitService;

    @Autowired
    public MythTransactionBootstrap(MythInitService mythInitService) {
       this.mythInitService = mythInitService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.getInstance().setCfgContext((ConfigurableApplicationContext) applicationContext);
        start(this);
    }


    private void start(MythConfig mythConfig) {
        mythInitService.initialization(mythConfig);
    }
}
