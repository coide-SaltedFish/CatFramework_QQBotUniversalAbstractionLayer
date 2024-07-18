package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 图片消息元素
 */
interface Image: MessageContent {

    /**
     * 图片id，计算图片md5获取或者提供图片链接提取
     */
    val id: UniversalId

    override val content: String get() = "[Image:$id]"

    companion object: Message.Key<Image> {
        override val typeName: String = "image"
    }

    /**
     * 尝试获取图片链接
     */
    suspend fun queryUrl(): String

    override fun toLogString(): String {
        return "[图片]"
    }

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == Image) this as M else null
    }
}