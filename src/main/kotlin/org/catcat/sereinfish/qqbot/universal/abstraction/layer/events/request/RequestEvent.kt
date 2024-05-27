package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.request

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.BotEvent

interface RequestEvent: BotEvent {
    // 请求发起者
    val user: User

    /**
     * 批准请求
     *
     * @param approve 是否同意请求
     * @param reason 所需要的理由或者其他字段
     *
     * @return 处理结果，成功返回true，失败返回false
     */
    suspend fun approve(approve: Boolean = true, reason: String): Boolean
}