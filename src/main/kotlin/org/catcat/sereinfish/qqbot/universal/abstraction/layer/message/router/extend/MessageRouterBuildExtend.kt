package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.extend

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterBuilder
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.SimpleMessageRouterBuilder
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 消息路由构建扩展
 */
fun buildMessageRouter(block: MessageRouterBuilder.() -> Unit) =
    SimpleMessageRouterBuilder().apply(block).build()

/**
 * 结尾
 */
fun MessageRouterBuilder.end() = EndRouter()

/**
 * 文本路由
 */
fun MessageRouterBuilder.text(text: String) = TextRouter(text)
fun MessageRouterBuilder.space() = TextRouter(" ")

/**
 * 可选路由
 */
fun MessageRouterBuilder.optional(block: MessageRouterBuilder.() -> Unit) =
    OptionalRouter(SimpleMessageRouterBuilder().apply(block).build())

fun MessageRouterBuilder.optional(router: MessageRouter) = OptionalRouter(router)

/**
 * 正则路由
 */
fun MessageRouterBuilder.regex(regex: Regex) = RegexRouter(regex)

// 量子叠加态的空格
fun MessageRouterBuilder.spaces() = optional(regex("\\s*".toRegex()))

fun MessageRouterBuilder.int() = regex("^[+-]?(\\d+)$".toRegex())
fun MessageRouterBuilder.number() = regex("^[+-]?([0-9]*[.])?[0-9]+\$".toRegex())
fun MessageRouterBuilder.email() = regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}\$".toRegex())

/**
 * 或路由
 */
fun MessageRouterBuilder.or(vararg routers: MessageRouterBuilder.() -> Unit) =
    OrRouter(routers.map {
        SimpleMessageRouterBuilder().apply(it).build()
    })

fun MessageRouterBuilder.or(vararg routers: MessageRouter) = OrRouter(routers.toList())

/**
 * 匹配 at
 */
fun MessageRouterBuilder.at(id: UniversalId) = AtRouter(id)
fun MessageRouterBuilder.at(user: User) = AtRouter(user.id)
fun MessageRouterBuilder.at(bot: Bot) = AtRouter(bot.id)

/**
 * 参数路由
 */
fun MessageRouterBuilder.parameter(key: String, block: MessageRouterBuilder.() -> Unit) =
    ParameterRouter(key, SimpleMessageRouterBuilder().apply(block).build())

fun MessageRouterBuilder.parameter(key: String, router: MessageRouter) = ParameterRouter(key, router)

/**
 * 类型路由
 */
inline fun <reified T : MessageContent> MessageRouterBuilder.type() = TypeRouter(T::class.java)