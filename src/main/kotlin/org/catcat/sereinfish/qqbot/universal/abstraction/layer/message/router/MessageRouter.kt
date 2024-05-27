package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router

/**
 * 消息路由
 *
 * 用于进行消息内容匹配以及参数提取
 */
interface MessageRouter {

    /**
     * 传入上下文，对消息进行匹配
     */
    fun match(context: RouterContext): Boolean {
        val parserContext = context.clone()
        parserContext.tempHandleMessage.clear()

        val ret = parser(parserContext)
        if (ret) {
            context.merge(parserContext)
            context.tempMessage = parserContext.tempMessage
            context.tempHandleMessage.addAll(parserContext.tempHandleMessage)
        }
        return ret
    }

    /**
     * 进行解析，如果有参数被解析则会放入上下文
     *
     * @return 解析成功返回 true，失败返回 false
     */
    fun parser(context: RouterContext): Boolean
}