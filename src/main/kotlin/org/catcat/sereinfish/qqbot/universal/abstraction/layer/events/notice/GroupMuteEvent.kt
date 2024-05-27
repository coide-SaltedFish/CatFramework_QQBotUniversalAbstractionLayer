package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群禁言事件
 */
interface GroupMuteEvent: GroupNoticeEvent {
    /**
     * 被禁言的对象
     */
    val user: Member

    /**
     * 操作者
     */
    val operator: Member

    /**
     * 禁言时间
     */
    val duration: Long

    /**
     * 取消禁言
     */
    suspend fun unmute(): Boolean = user.unmute()
}