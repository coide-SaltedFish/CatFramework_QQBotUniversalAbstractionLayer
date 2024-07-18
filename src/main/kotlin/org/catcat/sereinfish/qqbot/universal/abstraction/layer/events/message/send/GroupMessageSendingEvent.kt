package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message.send

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group

interface GroupMessageSendingEvent: MessageSendingEvent {
    override val target: Group
    val group: Group
}