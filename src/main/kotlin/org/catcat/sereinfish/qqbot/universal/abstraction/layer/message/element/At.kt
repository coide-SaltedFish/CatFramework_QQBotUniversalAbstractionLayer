package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface At: MessageContent {
    val target: Long

    override val content: String
        get() = "[At:${target}]"

    companion object: Message.Key<At> {
        override val typeName: String = "at"
    }

    override fun toLogString(): String {
        return content
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == At) this as M else null
    }
}