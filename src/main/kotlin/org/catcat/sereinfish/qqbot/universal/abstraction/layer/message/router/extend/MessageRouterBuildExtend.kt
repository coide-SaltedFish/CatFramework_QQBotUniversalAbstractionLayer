package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.extend

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterBuilder
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.Router
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
@Router
fun MessageRouterBuilder.end() = EndRouter()

/**
 * 文本路由
 */
@Router
fun MessageRouterBuilder.text(text: String) = TextRouter(text)
@Router
fun MessageRouterBuilder.space() = TextRouter(" ")

/**
 * 可选路由
 */
@Router
fun MessageRouterBuilder.optional(block: MessageRouterBuilder.() -> Unit) =
    OptionalRouter(SimpleMessageRouterBuilder().apply(block).build())

@Router
fun MessageRouterBuilder.optional(router: MessageRouter) = OptionalRouter(router)

/**
 * 正则路由
 */
@Router
fun MessageRouterBuilder.regex(regex: Regex) = RegexRouter(regex)

// 量子叠加态的空格
@Router
fun MessageRouterBuilder.spaces() = optional(regex("\\s*".toRegex()))

@Router
fun MessageRouterBuilder.int() = regex("^[+-]?(\\d+)$".toRegex())
@Router
fun MessageRouterBuilder.number() = regex("^[+-]?([0-9]*[.])?[0-9]+\$".toRegex())
@Router
fun MessageRouterBuilder.email() = regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,6}\$".toRegex())

/**
 * 或路由
 */
@Router
fun MessageRouterBuilder.or(vararg routers: MessageRouterBuilder.() -> Unit) =
    OrRouter(routers.map {
        SimpleMessageRouterBuilder().apply(it).build()
    })

@Router
fun MessageRouterBuilder.or(vararg routers: MessageRouter) = OrRouter(routers.toList())

/**
 * 匹配 at
 */
@Router
fun MessageRouterBuilder.at(id: UniversalId) = AtRouter(id)
@Router
fun MessageRouterBuilder.at(user: User) = AtRouter(user.id)
@Router
fun MessageRouterBuilder.at(bot: Bot) = AtRouter(bot.id)

/**
 * 参数路由
 */
@Router
fun MessageRouterBuilder.parameter(key: String, block: MessageRouterBuilder.() -> Unit) =
    ParameterRouter(key, SimpleMessageRouterBuilder().apply(block).build())

@Router
fun MessageRouterBuilder.parameter(key: String, router: MessageRouter) = ParameterRouter(key, router)

@Router
fun MessageRouterBuilder.all() = AllRouter()

/**
 * 类型路由
 */
@Router
inline fun <reified T : Message> MessageRouterBuilder.type() = TypeRouter(T::class.java)