package com.github.myth.core.concurrent.threadlocal;

import com.github.myth.common.bean.context.MythTransactionContext;

/**
 * 事务上下文
 */
public class TransactionContextLocal {

    private static final TransactionContextLocal TRANSACTION_CONTEXT_LOCAL = new TransactionContextLocal();

    private TransactionContextLocal() {

    }

    public static TransactionContextLocal getInstance() {
        return TRANSACTION_CONTEXT_LOCAL;
    }


    private static final ThreadLocal<MythTransactionContext> CURRENT_LOCAL = new ThreadLocal<>();

    public void set(MythTransactionContext context) {
        CURRENT_LOCAL.set(context);
    }

    public MythTransactionContext get() {
        return CURRENT_LOCAL.get();
    }

    public void remove() {
        CURRENT_LOCAL.remove();
    }
}
