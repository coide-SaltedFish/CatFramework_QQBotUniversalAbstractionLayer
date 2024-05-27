package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

class TypeRouter(
    val type: Class<out MessageContent>
): MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        return context.tempMessage.firstOrNull()?.let { message ->
            // 判断message是否是type类型的子类或者相等类型
            type.isAssignableFrom(message::class.java).also {
                if (it) {
                    context.tempMessage.removeAt(0)
                    context.tempHandleMessage.add(message)
                }
            }
        } ?: false
    }
}