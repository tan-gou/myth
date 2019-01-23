package com.github.myth.motan.service;

import com.github.myth.core.service.ApplicationService;
import com.weibo.api.motan.config.springsupport.BasicServiceConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("applicationService")
public class MotanApplicationServiceImpl implements ApplicationService {


    private final BasicServiceConfigBean basicServiceConfigBean;

    @Autowired
    public MotanApplicationServiceImpl(BasicServiceConfigBean basicServiceConfigBean) {
        this.basicServiceConfigBean = basicServiceConfigBean;
    }

    /**
     * 获取applicationName
     */
    @Override
    public String acquireName() {
        return basicServiceConfigBean.getModule();
    }
}
