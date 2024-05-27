package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

interface Video: MessageContent {

    override val content: String get() = "[Video]"

    companion object: Message.Key<Video> {
        override val typeName: String = "video"
    }

    /**
     * 获取视频链接
     */
    suspend fun queryUrl(): String

    override fun toLogString(): String {
        return "[视频]"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Video) this as M else null
    }
}