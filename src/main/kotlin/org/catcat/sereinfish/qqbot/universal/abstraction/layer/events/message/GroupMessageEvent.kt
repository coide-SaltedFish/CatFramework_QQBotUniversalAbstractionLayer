package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群消息
 */
interface GroupMessageEvent: MessageEvent {
    /**
     * 来源群对象
     */
    val group: Group

    // 重写发送者类型
    override val sender: Member

    /**
     * 尝试撤回消息
     */
    suspend fun recall(time: Long = 0L): Boolean
}