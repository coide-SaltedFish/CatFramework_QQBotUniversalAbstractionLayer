package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.MessageContent
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.RouterContext
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.MessageRouterEncode

class TypeRouter(
    val type: Class<out Message>
): MessageRouter {

    companion object: MessageRouterEncode<TypeRouter> {
        override val target: String = "type"

        private val messageTypes = hashMapOf<String, Class<out Message>>(
            "at" to At::class.java,
            "atall" to AtAll::class.java,
            "face" to Face::class.java,
            "forward" to Forward::class.java,
            "image" to Image::class.java,
            "jsonmessage" to JsonMessage::class.java,
            "planttext" to PlantText::class.java,
            "text" to PlantText::class.java,
            "reply" to Reply::class.java,
            "video" to Video::class.java,
            "voice" to Voice::class.java,
        )

        fun registerTypes(name: String, type: Class<out Message>) {
            messageTypes[name.lowercase()] = type
        }

        override fun decode(vararg params: Any?): TypeRouter {
            val typeObj = params.getOrElse(0){ error("必须传入类型限定名") }
            val typeStr = (typeObj as? String) ?: error("类型路由只能接受类型限定名：$typeObj")
            val type = messageTypes.getOrElse(typeStr.lowercase()){
                runCatching { Class.forName(typeStr) }.getOrNull()
            } ?: error("无法识别的类型：$typeStr")
            // 检查类型是否继承 Message
            if (Message::class.java.isAssignableFrom(type).not()) {
                error("传入的类型[${type}]必须继承于[${Message::class.java}]")
            }
            return TypeRouter(type as Class<out Message>)
        }
    }

    override fun parser(context: RouterContext): Boolean {
        return context.waitHandleMessages.firstOrNull()?.let { message ->
            // 判断message是否是type类型的子类或者相等类型
            type.isAssignableFrom(message::class.java).also {
                if (it) {
                    context.waitHandleMessages.removeAt(0)
                    context.handledMessages.add(message)
                }
            }
        } ?: false
    }

    override fun encode(): String {
        return "[$target:\"${messageTypes.entries.associate { it.value to it.key }[type] ?: type.name}\"]"
    }
}