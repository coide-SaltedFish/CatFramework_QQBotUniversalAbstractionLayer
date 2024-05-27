package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Friend

/**
 * 好友消息撤回事件
 */
interface FriendMessageRecallEvent: MessageRecallEvent {
    override val sender: Friend
}