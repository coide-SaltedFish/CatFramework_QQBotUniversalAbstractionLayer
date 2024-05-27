package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.AnonymousMember

/**
 * 匿名群消息
 */
interface AnonymousGroupMessageEvent: GroupMessageEvent {
    // 重写为匿名成员类型
    override val sender: AnonymousMember
}