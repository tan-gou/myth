package com.github.myth.springcloud.service;

import com.github.myth.core.service.ApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("applicationService")
public class SpringCloudApplicationServiceImpl implements ApplicationService {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public String acquireName() {
        return appName;
    }
}
