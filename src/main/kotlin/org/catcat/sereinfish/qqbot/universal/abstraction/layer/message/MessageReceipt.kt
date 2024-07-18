package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 消息回执
 *
 * 发送消息后得到的回执，具有消息id属性以及发送的消息内容
 */
interface MessageReceipt {
    val bot: Bot
    val message: Message
    val messageId: UniversalId
    val target: Contact

    suspend fun recall(time: Long): Boolean

    /**
     * 撤回消息
     */
    suspend fun recall(): Boolean = recall(0L)
}