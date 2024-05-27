package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface PlantText: MessageContent {
    val text: String

    override val content: String get() = text

    companion object: Message.Key<PlantText> {
        override val typeName: String = "plantText"
    }

    override fun toLogString(): String = text.replace("\n", "\\n")

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == PlantText) this as M else null
    }
}