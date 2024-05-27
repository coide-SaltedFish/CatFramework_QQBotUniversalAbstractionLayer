package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

/**
 * 语音消息
 */
interface Voice: MessageContent {
    override val content: String get() = "[Voice]"

    companion object: Message.Key<Voice> {
        override val typeName: String = "voice"
    }

    /**
     * 查询语音链接
     */
    suspend fun queryUrl(): String

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Voice) this as M else null
    }

    override fun toLogString(): String {
        return "[语音]"
    }
}