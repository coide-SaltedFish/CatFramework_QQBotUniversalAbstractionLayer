package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.PlantText
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext

class TextRouter(
    val text: String
): MessageRouter {
    override fun parser(context: RouterContext): Boolean {
        // 尝试整合多个文本元素
        var i = 0

        val textMessage = buildString {
            for (element in context.tempMessage){
                if (element is PlantText) {
                    append(element.text)
                    i++
                } else break
            }
        }

        return if (text == textMessage){
            context.tempHandleMessage.addAll(context.tempMessage.subList(0, i))
            context.tempMessage.subList(0, i).clear()
            true
        }else if (textMessage.startsWith(text)){
            val str = textMessage.removePrefix(text)
            val pt = object : PlantText {
                override val text: String = str
                override fun encode(): Any {
                    error("该元素为临时消息元素，无法完成序列化")
                }
            }
            context.tempHandleMessage.add(object : PlantText {
                override val text: String = this@TextRouter.text
                override fun encode(): Any {
                    error("该元素为临时消息元素，无法完成序列化")
                }
            })
            context.tempMessage.subList(0, i).clear()
            context.tempMessage.insertElementAt(pt, 0)
            true
        }else false
    }
}