package com.github.myth.dubbo.interceptor;

import com.github.myth.core.interceptor.AbstractMythTransactionAspect;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 *  Dubbo 分布式事务切面
 */
@Aspect
@Component
public class DubboMythTransactionAspect extends AbstractMythTransactionAspect implements Ordered {

    @Autowired
    public DubboMythTransactionAspect(DubboMythTransactionInterceptor dubboMythTransactionInterceptor) {
        super.setMythTransactionInterceptor(dubboMythTransactionInterceptor);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
