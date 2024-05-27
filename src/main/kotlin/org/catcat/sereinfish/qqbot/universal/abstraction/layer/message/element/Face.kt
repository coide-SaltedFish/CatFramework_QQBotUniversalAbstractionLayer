package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface Face: MessageContent {
    val id: Int

    override val content: String get() ="[Face:$id]"

    companion object: Message.Key<Face> {
        override val typeName: String = "face"
    }

    override fun toLogString(): String = content

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Face) this as M else null
    }
}