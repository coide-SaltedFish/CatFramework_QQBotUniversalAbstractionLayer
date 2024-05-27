package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

/**
 * 消息路由链
 */
interface MessageRouterChain: MessageRouter, List<MessageRouter>, RandomAccess {

    /**
     * 路由链匹配
     */
    override fun parser(context: RouterContext): Boolean {
        for (router in this){
            if (router.match(context).not()) return false
        }
        return true
    }
}