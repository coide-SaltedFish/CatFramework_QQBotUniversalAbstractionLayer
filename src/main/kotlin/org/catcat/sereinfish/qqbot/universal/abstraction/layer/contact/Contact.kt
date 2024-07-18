package org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageReceipt
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 联系人对象接口
 *
 * 联系人定义
 * 1. 能够接受和发送消息的对象
 * 2. 具有id标识
 * 3. 具有昵称
 */
interface Contact {
    val bot: Bot

    /**
     * 联系人id，好友，群成员等为qq号，群为群号
     */
//    val id: Long
    val id: UniversalId

    /**
     * 名称
     */
    val name: String

    /**
     * 向该联系人发送消息，并且返回消息回执
     */
    suspend fun sendMessage(message: Message): MessageReceipt
}