package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.PlantText
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

/**
 * 正则匹配
 */
class RegexRouter(
    val regex: Regex,
    val isGreedyMatch: Boolean = true
): MessageRouter {
    companion object: MessageRouterEncode<RegexRouter> {
        override val target: String = "regex"
        override fun decode(vararg params: Any?): RegexRouter {
            val regexString = (params[0] as? String) ?: error("无法获取正则参数：$params")
            val isGreedyMatch = (params.getOrNull(1) as? Boolean) ?: true

            return RegexRouter(regexString.toRegex(), isGreedyMatch)
        }
    }

    override fun parser(context: RouterContext): Boolean {
        // 读取字符串
        // 尝试整合多个文本元素
        var i = 0

        val textMessage = buildString {
            for (element in context.waitHandleMessages){
                if (element is PlantText) {
                    append(element.text)
                    i++
                } else break
            }
        }
        // 尝试正则匹配
        var mstr = "" // 待匹配文本
        var matchStr = "" // 匹配的文本
        for (c in textMessage) {
            mstr += c
            // 匹配
            if (regex.matches(mstr)){
                matchStr = mstr
                if (isGreedyMatch.not()) break
            }
        }
        if (matchStr.isEmpty()) return false

        return if (matchStr == textMessage){
            context.handledMessages.addAll(context.waitHandleMessages.subList(0, i))
            context.waitHandleMessages.subList(0, i).clear()
            true
        }else if (textMessage.startsWith(matchStr)){
            val str = textMessage.removePrefix(matchStr)
            val pt = context.bot.messageFactory().text(str)
            context.handledMessages.add(context.bot.messageFactory().text(matchStr))
            context.waitHandleMessages.subList(0, i).clear()
            context.waitHandleMessages.insertElementAt(pt, 0)
            true
        }else false
    }

    override fun encode(): String {
        return "[$target:\"$regex\"]"
    }
}