package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User

/**
 * 好友消息事件
 */
interface PrivateMessageEvent: MessageEvent {

    val type: PrivateType
    // 重写发送者类型
    override val sender: User

    enum class PrivateType {
        FRIEND, GROUP, OTHER
    }
}