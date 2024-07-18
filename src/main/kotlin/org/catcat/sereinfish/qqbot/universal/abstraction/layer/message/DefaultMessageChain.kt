package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.sereinfish.cat.frame.utils.toJson

/**
 * 默认消息链
 *
 * 不支持编码
 */
internal class DefaultMessageChain: MessageChain, ArrayList<Message>() {
    override fun encode(): Any {
        error("此消息链无法完成消息编码，请检查消息链来源是否正确：DefaultMessageChain")
//        val list = ArrayList<Any>()
//        encode(list, this)
//
//        return list
    }

//    private fun encode(list: MutableList<Any>, messageChain: MessageChain){
//        messageChain.forEach {
//            if (it is MessageChain) encode(list, it)
//            else list.add(it.encode())
//        }
//    }

    override fun serializeToJsonString(): String {
        return encode().toJson()
    }
}