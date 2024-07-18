package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

/**
 * 好友添加事件
 */
interface FriendAddEvent: NoticeEvent {
    // 新增好友id
    val userId: UniversalId
}