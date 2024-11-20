package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.sereinfish.cat.frame.context.Context
import java.util.*

interface RouterContext: Context {
    val bot: Bot

    val message: MessageChain

    var waitHandleMessages: Vector<Message>

    /**
     * 用来保存已处理的消息元素
     */
    val handledMessages: ArrayList<Message>

    /**
     * 获取匹配参数消息链
     */
    fun getParamMessageChain(): MessageChain = bot.messageFactory().build {
        handledMessages.forEach {
            add(it)
        }
    }

    /**
     * 将指定上下文合并到当前上下文
     */
    fun merge(context: Context)

    /**
     * 将当前上下文合并到指定上下文
     */
    fun mergeTo(context: Context)

    /**
     * 复制一个内容相同的上下文
     */
    fun clone(): RouterContext
}