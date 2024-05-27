package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface AtAll: MessageContent, At {

    override val content: String
        get() = "[AtAll]"

    companion object: Message.Key<At> {
        override val typeName: String = "atAll"
    }

    override fun toLogString(): String {
        return content
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == AtAll) this as M else null
    }
}