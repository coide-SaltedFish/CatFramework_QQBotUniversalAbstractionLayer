package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

/**
 * 可选路由
 *
 * 尝试进行匹配，匹配成功则修改环境为匹配后，失败则不进行变更
 */
class OptionalRouter(
    val router: MessageRouter
): MessageRouter {

    companion object: MessageRouterEncode<OptionalRouter> {
        override val target: String = "optional"
        override fun decode(vararg params: Any?): OptionalRouter {
            val router = (params[0] as? MessageRouter) ?: error("可选路由需要输入MessageRouter参数：${params}")

            return OptionalRouter(router)
        }
    }

    override fun parser(context: RouterContext): Boolean {
        val childContext = context.clone()
        childContext.handledMessages.clear()

        if (router.match(childContext)) {
            context.merge(childContext) // 合并内容
            // 更新处理进度
            context.waitHandleMessages = childContext.waitHandleMessages
            context.handledMessages.addAll(childContext.handledMessages)
        }
        return true
    }

    override fun encode(): String {
        return "[optional:${router.encode()}]"
    }
}