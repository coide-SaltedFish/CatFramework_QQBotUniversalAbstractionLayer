package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群消息撤回事件
 */
interface GroupMessageRecallEvent: MessageRecallEvent, GroupNoticeEvent {
    override val sender: Member

    override val operator: Member
}