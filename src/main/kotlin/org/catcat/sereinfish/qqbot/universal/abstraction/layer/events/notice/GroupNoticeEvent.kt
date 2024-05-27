package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageReceipt

/**
 * 群通知事件
 */
interface GroupNoticeEvent: NoticeEvent {
    /**
     * 来源群
     */
    val group: Group

    /**
     * 向群里发送消息
     */
    suspend fun sendMessage(message: Message): MessageReceipt
}