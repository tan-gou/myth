package com.github.myth.demo.springcloud.order.client;

import com.github.myth.annotation.Myth;
import com.github.myth.demo.springcloud.account.api.dto.AccountDTO;
import com.github.myth.demo.springcloud.account.api.entity.AccountDO;
import com.github.myth.demo.springcloud.account.api.service.AccountService;
import com.github.myth.demo.springcloud.order.configuration.MyConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "account-service", configuration = MyConfiguration.class)
public interface AccountClient {

    /**
     * 用户账户付款
     *
     * @param accountDO 实体类
     * @return true 成功
     */
    @PostMapping("/account-service/account/payment")
    @Myth(destination = "account", target = AccountService.class)
    Boolean payment(@RequestBody AccountDTO accountDO);


    /**
     * 获取用户账户信息
     *
     * @param userId 用户id
     * @return AccountDO
     */
    @PostMapping("/account-service/account/findByUserId")
    AccountDO findByUserId(@RequestParam("userId") String userId);
}
