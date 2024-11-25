package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.forward.ForwardMessageFactory
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 消息构建器
 */
interface MessageFactory {

    /**
     * 实例化消息对象
     */
    fun at(target: UniversalId): At

    fun atAll(): AtAll

    fun text(text: String): PlantText

    fun face(id: UniversalId): Face

    fun reply(messageId: UniversalId): Reply

    fun forward(): ForwardMessageFactory

    /**
     * 添加消息元素
     */
    fun add(message: Message): MessageFactory

    operator fun Message.unaryPlus() = add(this)
    operator fun String.unaryPlus() = add(text(this))

    operator fun plus(element: SingleMessage) = add(element)
    operator fun plus(text: String) = add(text(text))

    /**
     * 传入字符串反序列化为消息
     */
    fun deserializeFromJsonString(data: String): MessageChain

    /**
     * 构建消息链
     */
    fun build(): MessageChain

    fun build(block: MessageFactory.() -> Unit): MessageChain {
        block()
        return build()
    }
}