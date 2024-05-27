package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群管理员设置事件
 */
interface GroupAdminSetEvent: GroupNoticeEvent {
    // 设置的管理员对象
    val user: Member
}