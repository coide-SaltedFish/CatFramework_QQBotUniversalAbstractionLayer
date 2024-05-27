package org.catcat.sereinfish.qqbot.universal.abstraction.layer

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Friend
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.*
import java.io.InputStream

/**
 * qq bot的抽象接口
 */
interface Bot: Contact {
    override val id: Long
    override val name: String

    /**
     * 好友列表
     */
    val friends: Map<Long, Friend>

    /**
     * 群列表
     */
    val groups: Map<Long, Group>

    /**
     * bot版本
     */
    val version: String

    /**
     * 构建消息链
     */
    fun messageFactory(): MessageFactory

    // 构建资源使用
    fun externalResource(inputStream: InputStream): ExternalResource

    suspend fun getMessage(messageId: Int): MessageChain

    override suspend fun sendMessage(message: Message): MessageReceipt {
        error("无法向Bot自己发送消息")
    }
}