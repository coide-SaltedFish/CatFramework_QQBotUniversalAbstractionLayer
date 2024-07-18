package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message.send

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.OnlineMessageChain

/**
 * 消息发送完成后事件
 */
interface MessageSentEvent: MessageSendEvent {
    override val message: OnlineMessageChain
}