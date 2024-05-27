package org.catcat.sereinfish.qqbot.universal.abstraction.layer

import org.sereinfish.cat.frame.plugin.Plugin

/**
 * ### 实际上是没用到的插件主类
 *
 * ## 插件作用
 * 该插件用于统一qq机器人相关接口，可以根据该插件的抽象层制作统一的事件处理器及消息处理
 */
object PluginMain: Plugin {
    override fun close() {
    }

    override fun start() {
        logger.info("已引入猫猫QQ机器人统一抽象接口层")
    }
}