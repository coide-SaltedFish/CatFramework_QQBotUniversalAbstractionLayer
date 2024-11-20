package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.At
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

class AtRouter(
    val id: UniversalId
): MessageRouter {

    companion object: MessageRouterEncode<AtRouter> {
        override val target: String = "at"
        override fun decode(vararg params: Any?): AtRouter {
            TODO("默认at路由无法进行解析，请使用指定版本的at路由解析器")
        }
    }

    override fun parser(context: RouterContext): Boolean {
        val childContext = context.clone()
        childContext.handledMessages.clear()

        val ret = childContext.waitHandleMessages.firstOrNull()?.let { message ->
            if (message is At && message.target == id){
                childContext.waitHandleMessages.removeFirst()
                childContext.handledMessages.add(message)
                true
            }else false
        } ?: false

        if (ret) {
            context.merge(childContext)

            // 更新处理进度
            context.waitHandleMessages = childContext.waitHandleMessages
            context.handledMessages.addAll(childContext.handledMessages)
        }
        return ret
    }

    override fun encode(): String {
        return "[$target:$id]"
    }
}