package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

interface MessageContent: SingleMessage {
    val content: String

    override fun contentString(): String {
        return content
    }
}