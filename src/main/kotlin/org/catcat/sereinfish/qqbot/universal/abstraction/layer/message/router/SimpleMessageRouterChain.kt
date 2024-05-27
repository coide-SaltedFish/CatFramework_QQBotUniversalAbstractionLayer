package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

/**
 * 一个简单的路由链实现
 */
class SimpleMessageRouterChain: MessageRouterChain, ArrayList<MessageRouter>()