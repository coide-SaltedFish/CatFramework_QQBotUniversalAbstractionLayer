package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent

/**
 * 如果尚未实现的消息元素，转为这个类型
 */
interface UnimplementedMessage: MessageContent {

    companion object: Message.Key<At> {
        override val typeName: String = "UnimplementedMessage"
    }

    /**
     * 消息元素原始数据
     */
    val data: String

    override val content: String get() = "[UnimplementedMessage]"

    override fun encode(): Any {
        error("未实现的消息元素无法进行编码")
    }

    override fun toLogString() = content

    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        return if (key == UnimplementedMessage) this as M else null
    }
}