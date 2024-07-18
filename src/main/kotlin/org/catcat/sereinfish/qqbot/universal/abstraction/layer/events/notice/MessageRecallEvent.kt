package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 消息撤回事件
 */
interface MessageRecallEvent: NoticeEvent {
    // 消息发送者
    val sender: User

    // 撤回消息对象
    val operator: User

    // 被撤回的消息id
    val messageId: UniversalId
}