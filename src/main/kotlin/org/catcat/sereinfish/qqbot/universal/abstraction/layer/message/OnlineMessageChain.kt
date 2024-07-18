package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

interface OnlineMessageChain: MessageChain {
    val messageId: UniversalId
    val bot: Bot
    val target: Contact
    val sender: User

    suspend fun recall(time: Long = 0L): Boolean

    suspend fun reply(message: Message): MessageReceipt
}