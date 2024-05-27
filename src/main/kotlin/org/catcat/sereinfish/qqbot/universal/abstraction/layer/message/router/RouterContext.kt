package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.message.MessageEvent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.DefaultMessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageChain
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.sereinfish.cat.frame.context.Context
import org.sereinfish.cat.frame.context.TypeParser
import org.sereinfish.cat.frame.context.property.value
import org.sereinfish.cat.frame.context.property.valueOrElse
import org.sereinfish.cat.frame.context.property.valueOrPut
import java.util.Vector
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue

/**
 * 路由上下文
 *
 * 单独独立于事件上下文的一个上下文，也可以属于事件上下文
 */
class RouterContext(
    event: MessageEvent,
    override val data: ConcurrentHashMap<String, Any?> = ConcurrentHashMap(),
    override var typeParser: Vector<TypeParser<*>> = Vector()
) : Context {
    val mergeBlackList = hashSetOf("event", "tempMessage", "tempHandleMessage")

    val event by value<MessageEvent>()

    val message = event.message
    val sender = event.sender
    val target = event.target

    /**
     * 用来临时保存消息元素可以用作消息匹配
     */
    var tempMessage by valueOrPut { Vector<Message>() }

    /**
     * 用来保存已处理的消息元素
     */
    val tempHandleMessage by valueOrPut { ArrayList<Message>() }

    init {
        data["event"] = event
        message.filterIsInstance<MessageContent>().forEach {
            tempMessage.add(it)
        }
    }

    fun getParamMessageChain(): MessageChain = DefaultMessageChain().apply {
        tempHandleMessage.forEach {
            add(it)
        }
    }

    /**
     * 将另一个上下文内容合并到当前上下文
     */
    fun merge(context: Context){
        context.data.forEach { (s, any) ->
            if (mergeBlackList.contains(s).not()){
                this[s] = any
            }
        }
    }

    fun mergeTo(context: Context){
        data.forEach { (s, any) ->
            if (mergeBlackList.contains(s).not()){
                context[s] = any
            }
        }
    }

    /**
     * 复制一个内容相同的上下文
     */
    fun clone(): RouterContext {
        val context = RouterContext(event)
        context.data.putAll(data)
        context["tempMessage"] = Vector<Message>().apply {
            addAll(tempMessage)
        }
        context["tempHandleMessage"] = ArrayList<Message>().apply {
            addAll(tempHandleMessage)
        }

        return context
    }
}