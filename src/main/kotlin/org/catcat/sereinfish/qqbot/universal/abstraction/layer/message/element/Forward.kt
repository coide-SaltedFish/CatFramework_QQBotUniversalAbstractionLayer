package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface Forward: MessageContent {
    override val content: String get() = "[转发消息]"

    val nodes: List<Node>

    companion object: Message.Key<Forward> {
        override val typeName: String = "forward"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Forward) this as M else null
    }

    interface Node {
        val message: MessageChain
    }
}