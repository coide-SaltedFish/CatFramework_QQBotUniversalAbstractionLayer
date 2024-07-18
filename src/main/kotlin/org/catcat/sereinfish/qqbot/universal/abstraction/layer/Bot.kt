package org.catcat.sereinfish.qqbot.universal.abstraction.layer

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Friend
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId
import java.io.File
import java.io.InputStream

/**
 * qq bot的抽象接口
 */
interface Bot: Contact, User {
    override val name: String
    override val nickname: String

    /**
     * 好友列表
     */
    val friends: Map<UniversalId, Friend>

    /**
     * 群列表
     */
    val groups: Map<UniversalId, Group>

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

    fun externalResource(file: File): ExternalResource

    /**
     * 将id字符串反向编码为id类型
     */
    fun decodeContactId(contactId: String): UniversalId

    /**
     * 将消息Json编码反序列化为消息
     */
    fun deserializeFromJson(json: String): MessageChain

    suspend fun getMessage(messageId: UniversalId): MessageChain

    override suspend fun sendMessage(message: Message): MessageReceipt {
        error("无法向Bot自己发送消息")
    }
}