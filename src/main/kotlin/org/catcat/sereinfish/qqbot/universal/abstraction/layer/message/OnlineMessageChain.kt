package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User

interface OnlineMessageChain: MessageChain {
    val messageId: Int
    val bot: Bot
    val target: Contact
    val sender: User

    suspend fun recall(time: Long = 0L): Boolean
}