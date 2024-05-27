package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import java.util.RandomAccess

/**
 * 消息链
 */
interface MessageChain: Message, List<Message>, RandomAccess {
    /**
     * 获取指定类型的第一个消息元素
     */
    operator fun <M: Message> get(key: Message.Key<M>): M? {
        return getOrNull(key)
    }

    /**
     * 获取指定类型的第一个消息元素
     * 没有则返回Null
     */
    override fun <M : Message> getOrNull(key: Message.Key<M>): M? {
        forEach {
            it.getOrNull(key)?.let {
                return it
            }
        }
        return null
    }

    override fun toLogString(): String {
        return buildString {
            this@MessageChain.forEach {
                append(it.toLogString())
            }
        }
    }

    override fun contentString(): String {
        return buildString {
            this@MessageChain.forEach {
                append(it.contentString())
            }
        }
    }

    /**
     * 消息序列化为字符串
     */
    fun serializeToJsonString(): String
}