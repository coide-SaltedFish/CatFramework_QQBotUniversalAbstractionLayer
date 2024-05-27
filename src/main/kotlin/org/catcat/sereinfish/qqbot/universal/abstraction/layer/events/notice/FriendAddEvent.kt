package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

/**
 * 好友添加事件
 */
interface FriendAddEvent: NoticeEvent {
    // 新增好友id
    val userId: Long
}