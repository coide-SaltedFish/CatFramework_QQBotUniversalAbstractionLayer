package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.At
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

class AtRouter(
    val id: Long
): MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        return context.tempMessage.firstOrNull()?.let { message ->
            context.tempMessage.removeAt(0)

            if (message is At){
                if (message.target == id) {
                    context.tempHandleMessage.add(message)
                    true
                }else false
            }else false
        } ?: false
    }
}