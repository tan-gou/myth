/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.github.myth.annotation;


/**
 * 事务传播行为的种类
 *
 * @author xiaoyu
 */
public enum PropagationEnum {

    /**
     * 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择
     */
    PROPAGATION_REQUIRED(0),

    /**
     * 支持当前事务，如果当前没有事务，就以非事务方式执行
     */
    PROPAGATION_SUPPORTS(1),

    /**
     * 使用当前的事务，如果当前没有事务，就抛出异常
     */
    PROPAGATION_MANDATORY(2),

    /**
     * 新建事务，如果当前存在事务，把当前事务挂起
     */
    PROPAGATION_REQUIRES_NEW(3),

    /**
     * 以非事务方式执行操作，如果当前存在事务，就把当前事务挂
     */
    PROPAGATION_NOT_SUPPORTED(4),

    /**
     * 以非事务方式执行，如果当前存在事务，则抛出异常
     */
    PROPAGATION_NEVER(5),

    /**
     * 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类 似的操作
     */
    PROPAGATION_NESTED(6);


    private final int value;

    PropagationEnum(int value) {
        this.value = value;
    }


    public int getValue() {
        return this.value;
    }
}
