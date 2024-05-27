package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

class SimpleMessageRouterBuilder: MessageRouterBuilder {
    private val chain = SimpleMessageRouterChain()

    override fun add(router: MessageRouter): MessageRouterBuilder {
        chain.add(router)
        return this
    }

    override fun build(): MessageRouter = chain
}