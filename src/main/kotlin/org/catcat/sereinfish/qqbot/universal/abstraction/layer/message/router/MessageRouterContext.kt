package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message.MessageEvent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.DefaultMessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.sereinfish.cat.frame.context.Context
import org.sereinfish.cat.frame.context.TypeParser
import org.sereinfish.cat.frame.context.property.value
import org.sereinfish.cat.frame.context.property.valueOrPut
import java.util.Vector
import java.util.concurrent.ConcurrentHashMap

/**
 * 路由上下文
 *
 * 单独独立于事件上下文的一个上下文，也可以属于事件上下文
 */
open class MessageRouterContext(
    override val bot: Bot,
    override val message: MessageChain,
    override val data: ConcurrentHashMap<String, Any?> = ConcurrentHashMap(),
    override var typeParser: Vector<TypeParser<*>> = Vector()
) : RouterContext {
    val mergeBlackList = hashSetOf("waitHandleMessages", "handledMessages")

    /**
     * 用来临时保存消息元素可以用作消息匹配
     */
    override var waitHandleMessages by valueOrPut { Vector<Message>() }

    /**
     * 用来保存已处理的消息元素
     */
    override val handledMessages by valueOrPut { ArrayList<Message>() }

    /**
     * 初始化路由上下文
     */
    init {
//        message.filterIsInstance<MessageContent>().forEach {
//            tempMessage.add(it)
//        }

        message.forEach {
            waitHandleMessages.add(it)
        }
    }

    /**
     * 将另一个上下文内容合并到当前上下文
     */
    override fun merge(context: Context){
        context.data.forEach { (s, any) ->
            if (mergeBlackList.contains(s).not()){
                this[s] = any
            }
        }
    }

    /**
     * 将当前上下文合并到指定上下文
     */
    override fun mergeTo(context: Context){
        data.forEach { (s, any) ->
            if (mergeBlackList.contains(s).not()){
                context[s] = any
            }
        }
    }

    /**
     * 复制一个内容相同的上下文
     */
    override fun clone(): MessageRouterContext {
        val context = MessageRouterContext(bot, message, typeParser = Vector(typeParser))
        context.data.putAll(data)
        context["waitHandleMessages"] = Vector<Message>().apply {
            addAll(waitHandleMessages)
        }
        context["handledMessages"] = ArrayList<Message>().apply {
            addAll(handledMessages)
        }

        return context
    }
}