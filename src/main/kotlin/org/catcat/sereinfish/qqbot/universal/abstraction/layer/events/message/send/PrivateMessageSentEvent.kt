package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message.send

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Contact
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.User

interface PrivateMessageSentEvent: MessageSentEvent {
    override val target: User
    val user: User
}