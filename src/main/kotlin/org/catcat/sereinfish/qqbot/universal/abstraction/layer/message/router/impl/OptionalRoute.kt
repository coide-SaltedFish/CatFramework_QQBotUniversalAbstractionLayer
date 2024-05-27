package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
/**
 * 可选路由
 *
 * 尝试进行匹配，匹配成功则修改环境为匹配后，失败则不进行变更
 */
class OptionalRoute(
    val router: MessageRouter
): MessageRouter {

    override fun parser(context: RouterContext): Boolean {
        val childContext = context.clone()
        childContext.tempHandleMessage.clear()

        if (router.match(childContext)) {
            context.merge(childContext) // 合并内容
            // 更新处理进度
            context.tempMessage = childContext.tempMessage
            context.tempHandleMessage.addAll(childContext.tempHandleMessage)
        }
        return true
    }
}