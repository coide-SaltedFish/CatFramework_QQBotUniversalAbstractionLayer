package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter

/**
 * 路由编码器
 *
 * 路由定义：
 * [target: args...]
 */
interface MessageRouterEncode<T: MessageRouter> {
    val target: String // 标签

    /**
     * 将字符串解码为路由
     */
    fun decode(vararg params: Any?): T
}