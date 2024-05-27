package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.QQEvent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageReceipt

/**
 * 消息事件
 */
interface MessageEvent: QQEvent {

    /**
     * 消息发送者
     *
     * 好友消息是 Frined，群消息是 Member
     */
    val sender: User

    /**
     * 消息目标
     *
     * 如果是好友消息则同 sender
     * 如果是群消息则是 Group
     */
    val target: Contact

    /**
     * 原始消息内容
     */
    val rawMessage: String

    /**
     * 消息内容
     */
    val message: MessageChain

    /**
     * 向消息来源发送消息
     */
    suspend fun sendMessage(message: Message): MessageReceipt

    /**
     * 回复来源消息
     */
    suspend fun reply(message: Message): MessageReceipt
}