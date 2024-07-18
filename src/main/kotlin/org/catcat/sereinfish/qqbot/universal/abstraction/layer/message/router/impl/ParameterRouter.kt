package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

/**
 * 参数路由，根据条件提取内容
 *
 * @param name 提取的参数名称
 * @param router 匹配的路由
 */
class ParameterRouter(
    val name: String,
    val router: MessageRouter
): MessageRouter {

    companion object: MessageRouterEncode<ParameterRouter> {
        override val target = "param"
        override fun decode(vararg params: Any?): ParameterRouter {
            val name = (params[0] as? String) ?: error("无法获取参数名称：$params")
            val router = (params[1] as? MessageRouter) ?: error("无法获取参数路由：$params")

            return ParameterRouter(name, router)
        }
    }
    override fun parser(context: RouterContext): Boolean {
        val childContext = context.clone()
        childContext.handledMessages.clear() // 清空子列表

        val ret = router.match(childContext)
        if (ret.not()) return false
        // 提取参数
        val params = childContext.getParamMessageChain()

        // 环境同步
        context.merge(childContext)

        context[name] = params

        context.waitHandleMessages = childContext.waitHandleMessages
        context.handledMessages.addAll(childContext.handledMessages)

        return ret
    }

    override fun encode(): String {
        return "[param:$name,${router.encode()}]"
    }
}