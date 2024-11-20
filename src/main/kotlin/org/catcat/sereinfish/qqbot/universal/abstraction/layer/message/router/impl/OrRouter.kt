package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.utils.tryMatch

/**
 * 或路由
 */
class OrRouter(
    val routers: List<MessageRouter>
): MessageRouter {
    companion object: MessageRouterEncode<OrRouter> {
        override val target: String = "or"

        override fun decode(vararg params: Any?): OrRouter {
            val routers = params.map {
                (it as? MessageRouter) ?: error("Or路由只能接受MessageRouter参数：${it?.let { it::class.java }}")
            }
            return OrRouter(routers)
        }
    }

//    override fun parser(context: RouterContext): Boolean {
//        for (router in routers) {
//            val childContext = context.clone()
//            childContext.handledMessages.clear()
//
//            if (router.match(childContext)) {
//                context.merge(childContext)
//                context.waitHandleMessages = childContext.waitHandleMessages
//                context.handledMessages.addAll(childContext.handledMessages)
//                return true
//            }
//        }
//        return false
//    }

    override fun parser(context: RouterContext): Boolean = tryMatch(context) {
        for (router in routers) {
            if (matchTry { router.match(tryMatchContext) }) {
                return@tryMatch true
            }
        }
        false
    }

    override fun encode(): String {
        return "[$target:${routers.joinToString(",") { it.encode() }}]"
    }
}