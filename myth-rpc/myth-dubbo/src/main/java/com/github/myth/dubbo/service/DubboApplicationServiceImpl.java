package com.github.myth.dubbo.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.github.myth.core.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("applicationService")
public class DubboApplicationServiceImpl implements ApplicationService {

    /**
     * dubbo ApplicationConfig
     */
    private final ApplicationConfig applicationConfig;

    @Autowired(required = false)
    public DubboApplicationServiceImpl(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }


    @Override
    public String acquireName() {
        return applicationConfig.getName();
    }
}
