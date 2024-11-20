package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.utils.matchText
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.utils.tryMatch

class TextRouter(
    val text: String
): MessageRouter {

    companion object: MessageRouterEncode<TextRouter> {
        override val target: String = "text"

        override fun decode(vararg params: Any?): TextRouter {
            if (params.isEmpty()) error("构建文本路由元素失败，缺少文本参数：params is empty")
            val text = (params[0] as? String) ?: error("无法获取文本参数：$params")
            return TextRouter(text)
        }
    }

    override fun parser(context: RouterContext): Boolean {
        return tryMatch(context) {
            matchText(text)
        }
        // 尝试整合多个文本元素
//        var i = 0
//
//        val textMessage = buildString {
//            for (element in context.waitHandleMessages){
//                if (element is PlantText) {
//                    append(element.text)
//                    i++
//                } else break
//            }
//        }
//
//        return if (text == textMessage){
//            // 将当前处理了的消息添加到已处理消息列表中
//            context.handledMessages.addAll(context.waitHandleMessages.subList(0, i))
//            // 清除已处理过的消息元素
//            context.waitHandleMessages.subList(0, i).clear()
//            true
//        }else if (textMessage.startsWith(text)){
//            // 移除匹配部分
//            val str = textMessage.removePrefix(text)
//
//            // 将剩余部分重新组合为消息元素
//            val pt = object : PlantText {
//                override val text: String = str
//                override fun encode(): Any {
//                    error("该元素为临时消息元素，无法完成序列化")
//                }
//            }
//            // 将已处理的部分添加到已处理列表中
//            context.handledMessages.add(object : PlantText {
//                override val text: String = this@TextRouter.text
//                override fun encode(): Any {
//                    error("该元素为临时消息元素，无法完成序列化")
//                }
//            })
//            // 清除已处理过的消息元素
//            context.waitHandleMessages.subList(0, i).clear()
//            // 将剩余文本元素添加到消息元素列表中
//            context.waitHandleMessages.insertElementAt(pt, 0)
//            true
//        }else false
    }

    override fun encode(): String {
        return "[$target:\"${text}\"]"
    }
}