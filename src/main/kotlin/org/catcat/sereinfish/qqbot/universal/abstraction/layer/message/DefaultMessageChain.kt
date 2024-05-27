package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.sereinfish.cat.frame.utils.toJson

internal class DefaultMessageChain: MessageChain, ArrayList<Message>() {
    override fun encode(): Any {
        val list = ArrayList<Any>()
        encode(list, this)

        return list
    }

    private fun encode(list: MutableList<Any>, messageChain: MessageChain){
        messageChain.forEach {
            if (it is MessageChain) encode(list, it)
            else list.add(it.encode())
        }
    }

    override fun serializeToJsonString(): String {
        return encode().toJson()
    }
}