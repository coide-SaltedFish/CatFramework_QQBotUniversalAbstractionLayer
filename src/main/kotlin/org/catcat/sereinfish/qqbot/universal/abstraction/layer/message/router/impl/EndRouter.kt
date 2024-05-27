package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

class EndRouter: MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        return context.tempMessage.isEmpty()
    }
}