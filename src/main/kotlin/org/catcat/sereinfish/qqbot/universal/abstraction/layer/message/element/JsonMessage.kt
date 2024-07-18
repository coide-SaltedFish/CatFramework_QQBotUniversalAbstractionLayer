package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface JsonMessage: MessageContent {
    val data: String

    override val content: String
        get() = "[Json:${data}]"

    companion object: Message.Key<JsonMessage> {
        override val typeName: String = "json"
    }

    override fun toLogString(): String {
        return "[Json]"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == JsonMessage) this as M else null
    }
}