package org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact

/**
 * 用户
 *
 * 群成员、好友、陌生人的集合
 */
interface User: Contact {
    /**
     * 昵称
     */
    val nickname: String

    fun queryFaceImage(): ByteArray
}