package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 取消管理员事件
 */
interface GroupAdminUnsetEvent: GroupNoticeEvent {
    // 设置的管理员对象
    val user: Member
}