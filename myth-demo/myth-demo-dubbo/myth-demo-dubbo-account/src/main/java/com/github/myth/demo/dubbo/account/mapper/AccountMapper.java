package com.github.myth.demo.dubbo.account.mapper;

import com.github.myth.demo.dubbo.account.api.entity.AccountDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {

    /**
     * 扣减账户余额
     */
    @Update("update account set balance =#{balance}," +
            " update_time = #{updateTime}" +
            " where user_id =#{userId}  and  balance > 0  ")
    int update(AccountDO accountDO);


    @Select("select * from account where user_id =#{userId}")
    AccountDO findByUserId(String userId);
}
