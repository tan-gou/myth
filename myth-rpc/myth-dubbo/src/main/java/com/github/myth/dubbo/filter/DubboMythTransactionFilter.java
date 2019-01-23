package com.github.myth.dubbo.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.github.myth.annotation.Myth;
import com.github.myth.common.bean.context.MythTransactionContext;
import com.github.myth.common.constant.CommonConstant;
import com.github.myth.common.utils.GsonUtils;
import com.github.myth.core.concurrent.threadlocal.TransactionContextLocal;

import java.lang.reflect.Method;
import java.util.Objects;


/**
 *
 */
@Activate(group = {Constants.SERVER_KEY, Constants.CONSUMER})
public class DubboMythTransactionFilter implements Filter {


    @Override
    @SuppressWarnings("unchecked")
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String methodName = invocation.getMethodName();
        Class clazz = invoker.getInterface();
        Class[] args = invocation.getParameterTypes();
        final Object[] arguments = invocation.getArguments();

        Method method = null;
        Myth myth = null;
        try {
            method = clazz.getDeclaredMethod(methodName, args);
            myth = method.getAnnotation(Myth.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (Objects.nonNull(myth)) {
            final MythTransactionContext mythTransactionContext =
                    TransactionContextLocal.getInstance().get();
            if (Objects.nonNull(mythTransactionContext)) {
                RpcContext.getContext()
                        .setAttachment(CommonConstant.MYTH_TRANSACTION_CONTEXT,
                                GsonUtils.getInstance().toJson(mythTransactionContext));
            }

        }

        return invoker.invoke(invocation);
    }
}
