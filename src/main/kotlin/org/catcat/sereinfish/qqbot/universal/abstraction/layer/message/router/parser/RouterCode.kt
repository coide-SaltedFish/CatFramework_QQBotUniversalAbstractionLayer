package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

import RouterCodeParser
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.At
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.Image
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.extend.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.*

/**
 * 路由码解析器
 */
object RouterCode {
    private val routerEncodes = HashMap<String, MessageRouterEncode<*>>()

    init {
        resgister(AllRouter)
        resgister(AtRouter)
        resgister(EndRouter)
        resgister(OptionalRouter)
        resgister(OrRouter)
        resgister(ParameterRouter)
        resgister(RegexRouter)
        resgister(TextRouter)
        resgister(TypeRouter)
    }

    /**
     * 注册解析器
     */
    fun resgister(encode: MessageRouterEncode<*>) {
        routerEncodes[encode.target] = encode
    }

    /**
     * 将字符串解析为路由
     */
    fun parse(code: String): MessageRouter {
        return RouterCodeParser(code, routerEncodes).parse()
    }
    /**
     * 将路由解析为字符串
     */
    fun encode(router: MessageRouter): String {
        return router.encode()
    }
}

fun main() {
    val code = RouterCode.parse("说：[param:content,[all]]")

    println(code)
}