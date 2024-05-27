package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.request

interface FriendRequestEvent: RequestEvent {
    val comment: String // 验证消息
}