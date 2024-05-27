package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群内戳一戳事件
 */
interface GroupPokeEvent: GroupNoticeEvent {
    /**
     * 戳的人
     */
    val sender: Member

    /**
     * 被戳的人
     */
    val target: Member
}