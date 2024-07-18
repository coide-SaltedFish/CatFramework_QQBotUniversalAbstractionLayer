package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageMateData
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

interface Reply: MessageMateData {
    val messageId: UniversalId

    companion object: Message.Key<Reply> {
        override val typeName: String = "reply"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Reply) this as M else null
    }
}