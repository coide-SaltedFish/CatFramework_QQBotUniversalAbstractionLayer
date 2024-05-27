package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

interface Message {

    /**
     * 编码为可发送信息
     */
    fun encode(): Any

    /**
     * 编码为日志信息
     */
    fun toLogString(): String

    /**
     * 格式化为字符串消息
     */
    fun contentString(): String

    fun <M: Message> getOrNull(key: Key<M>): M?

    /**
     * key
     */
    interface Key<M: Message> {
        val typeName: String
    }
}