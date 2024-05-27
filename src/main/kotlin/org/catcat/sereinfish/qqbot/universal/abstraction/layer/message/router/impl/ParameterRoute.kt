package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

/**
 * 参数路由，根据条件提取内容
 *
 * @param name 提取的参数名称
 * @param router 匹配的路由
 */
class ParameterRoute(
    val name: String,
    val router: MessageRouter
): MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        val childContext = context.clone()
        childContext.tempHandleMessage.clear() // 清空子列表

        val ret = router.match(childContext)
        if (ret.not()) return false
        // 提取参数
        val params = childContext.getParamMessageChain()

        // 环境同步
        context.merge(childContext)

        context[name] = params

        context.tempMessage = childContext.tempMessage
        context.tempHandleMessage.addAll(childContext.tempHandleMessage)

        return ret
    }
}