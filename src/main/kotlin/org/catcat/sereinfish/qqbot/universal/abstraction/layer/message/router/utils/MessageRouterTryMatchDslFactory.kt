package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.utils

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.PlantText
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

/**
 * 消息路由匹配快捷工具
 *
 * 1. 进行匹配尝试，成功则合并上下文
 */
class MessageRouterTryMatchDslFactory(
    private val parentsContext: RouterContext
) {
    var index: Int = 0

    /**
     * 生成一个临时上下文用于处理匹配
     */
    val tryMatchContext = parentsContext.clone().apply {
        handledMessages.clear()
    }

    val bot = parentsContext.bot
    val message = parentsContext.message

    /**
     * 弹出一个消息元素
     */
    fun pop(): Message = tryMatchContext.waitHandleMessages[index ++]

    fun popLoop(block: (Message) -> Unit) {
        while (index < tryMatchContext.waitHandleMessages.size) {
            val element = pop()
            block(element)
        }
    }

    /**
     * 弹出文本消息
     */
    fun popTextMessage(): PlantText {
        val text = buildString {
            for (i in index..<tryMatchContext.waitHandleMessages.size){
                val element = tryMatchContext.waitHandleMessages[i]
                if (element is PlantText) {
                    append(element.text)
                    index ++
                } else break
            }
        }
        return bot.messageFactory().text(text)
    }

    /**
     * 进行一次尝试匹配
     *
     * 匹配成功会将临时上下文合并到当前上下文，失败则不会
     */
    fun matchTry(
        block: MessageRouterTryMatchDslFactory.() -> Boolean
    ): Boolean {
        val matchDslFactory = MessageRouterTryMatchDslFactory(tryMatchContext)
        return matchDslFactory.block().also {
            if (it) matchDslFactory.marge()
        }
    }

    /**
     * 将当前上下文已处理消息加入已处理列表
     * 并且从待处理列表移除
     */
    fun processAndMove() {
        tryMatchContext.handledMessages.addAll(tryMatchContext.waitHandleMessages.subList(0, index))
        tryMatchContext.waitHandleMessages.subList(0, index).clear()
    }

    /**
     * 进行上下文合并
     *
     * 将当前临时上下文合并到上级上下文
     */
    fun marge() {
        parentsContext.merge(tryMatchContext)
        parentsContext.waitHandleMessages = tryMatchContext.waitHandleMessages
        parentsContext.handledMessages.addAll(tryMatchContext.handledMessages)
    }
}