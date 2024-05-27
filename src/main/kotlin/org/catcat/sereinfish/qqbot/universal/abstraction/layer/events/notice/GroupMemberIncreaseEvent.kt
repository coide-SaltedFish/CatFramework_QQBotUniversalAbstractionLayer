package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群成员增加事件
 */
interface GroupMemberIncreaseEvent: GroupNoticeEvent {
    // 进群方式
    val type: IncreaseType

    val user: Member
    val operator: Member

    /**
     * 进群类型
     */
    enum class IncreaseType {
        APPROVE, // 管理员同意入群
        INVITE // 邀请入群
    }
}