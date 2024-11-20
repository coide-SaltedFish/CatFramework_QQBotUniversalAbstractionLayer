package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.utils

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

/**
 * 在路由中尝试进行一次匹配
 */
fun MessageRouter.tryMatch(
    context: RouterContext,
    block: MessageRouterTryMatchDslFactory.() -> Boolean
): Boolean {
    val matchDslFactory = MessageRouterTryMatchDslFactory(context)
    return matchDslFactory.matchTry(block).also {
        if (it) matchDslFactory.marge()
    }
}

fun MessageRouterContext.tryMatch(block: MessageRouterTryMatchDslFactory.() -> Boolean): Boolean {
    val matchDslFactory = MessageRouterTryMatchDslFactory(this)
    return matchDslFactory.matchTry(block).also {
        if (it) matchDslFactory.marge()
    }
}

/**
 * 匹配文本
 */
fun MessageRouterTryMatchDslFactory.matchText(text: String): Boolean = matchTry {
    val message = popTextMessage()

    if (text == message.text){
        processAndMove() // 处理列表更新
        true
    }else if (message.text.startsWith(text)){
        val str = message.text.removePrefix(text)
        val pt = bot.messageFactory().text(str)
        // 处理列表更新
        tryMatchContext.handledMessages.add(bot.messageFactory().text(text))
        tryMatchContext.waitHandleMessages.subList(0, index).clear()
        tryMatchContext.waitHandleMessages.insertElementAt(pt, 0)
        true
    }else false
}