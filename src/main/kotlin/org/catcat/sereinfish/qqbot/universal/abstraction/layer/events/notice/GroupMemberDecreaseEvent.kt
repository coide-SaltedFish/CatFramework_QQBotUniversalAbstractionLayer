package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群成员减少事件
 */
interface GroupMemberDecreaseEvent: GroupNoticeEvent {
    val type: DecreaseType // 退群类型

    val user: Member
    val operator: Member

    enum class DecreaseType {
        LEAVE, // 主动退群
        KICK, // 成员被踢
        KICK_ME // bot 被踢
    }
}