package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.sereinfish.cat.frame.event.Event

/**
 * qq机器人的所有事件接口
 */
interface QQEvent: Event {

    /**
     * 事件来源bot
     */
    val bot: Bot

    /**
     * 事件发生时间戳
     */
    val time: Long

    /**
     * 事件全局唯一id
     */
    val uuid: String

}