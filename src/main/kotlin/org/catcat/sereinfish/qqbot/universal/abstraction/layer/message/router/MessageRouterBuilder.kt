package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.OrRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.TextRouter

/**
 * 消息路由构建器接口
 */
interface MessageRouterBuilder {

    operator fun MessageRouter.unaryPlus() = add(this)
    operator fun String.unaryPlus() = add(TextRouter(this))

    operator fun plus(router: MessageRouter) = add(router)
    operator fun plus(text: String) = add(TextRouter(text))

    /**
     * 或路由的中缀写法
     */
    infix fun MessageRouter.or(router: MessageRouter) = add(OrRouter(listOf(this, router)))

    /**
     * 新增路由
     */
    fun add(router: MessageRouter): MessageRouterBuilder

    /**
     * 构建消息路由
     */
    fun build(): MessageRouter
}