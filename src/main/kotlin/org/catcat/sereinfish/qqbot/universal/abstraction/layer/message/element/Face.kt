package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

interface Face: MessageContent {
    val id: UniversalId

    override val content: String get() ="[Face:$id]"

    companion object: Message.Key<Face> {
        override val typeName: String = "face"
    }

    override fun toLogString(): String = content

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Face) this as M else null
    }
}