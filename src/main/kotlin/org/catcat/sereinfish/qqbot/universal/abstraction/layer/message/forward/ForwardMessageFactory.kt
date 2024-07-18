package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.forward

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.Message
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.Forward
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils.UniversalId

interface ForwardMessageFactory {
    val nodes: MutableList<Forward.Node>

    fun node(userId: UniversalId, name: String, message: Message): Forward.Node

    fun add(node: Forward.Node) = nodes.add(node)

    fun build(): Forward
}