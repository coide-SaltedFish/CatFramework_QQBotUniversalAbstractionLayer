package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

/**
 * 或路由
 */
class OrRouter(
    val routers: List<MessageRouter>
): MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        for (router in routers) {
            val childContext = context.clone()
            childContext.tempHandleMessage.clear()

            if (router.match(childContext)) {
                context.merge(childContext)
                context.tempMessage = childContext.tempMessage
                context.tempHandleMessage.addAll(childContext.tempHandleMessage)
                return true
            }
        }
        return false
    }
}