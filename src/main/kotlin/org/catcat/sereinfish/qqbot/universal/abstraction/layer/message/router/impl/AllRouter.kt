package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

class AllRouter: MessageRouter {

    companion object: MessageRouterEncode<AllRouter> {
        override val target: String = "all"
        override fun decode(vararg params: Any?): AllRouter {
            if (params.isNotEmpty()) error("all路由不支持参数")
            return AllRouter()
        }
    }

    override fun parser(context: RouterContext): Boolean {
        context.handledMessages.addAll(context.waitHandleMessages)
        context.waitHandleMessages.clear()
        return true
    }

    override fun encode(): String {
        return "[$target]"
    }
}