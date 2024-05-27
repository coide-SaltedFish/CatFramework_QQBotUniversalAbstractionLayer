package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.request

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Group

interface GroupRequestEvent: RequestEvent {
    val group: Group
    val comment: String
}