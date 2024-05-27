package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageMateData

interface Reply: MessageMateData {
    val messageId: Int

    companion object: Message.Key<Reply> {
        override val typeName: String = "reply"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Reply) this as M else null
    }
}