package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

class EndRouter: MessageRouter {

    companion object: MessageRouterEncode<EndRouter> {
        override val target: String = "end"
        override fun decode(vararg params: Any?): EndRouter {
            if (params.isNotEmpty()) error("End路由不支持参数")
            return EndRouter()
        }
    }

    override fun parser(context: RouterContext): Boolean {
        return context.waitHandleMessages.isEmpty()
    }

    override fun encode(): String {
        return "[$target]"
    }
}