package org.catcat.sereinfish.qqbot.universal.abstraction.layer

import java.util.concurrent.ConcurrentHashMap

object BotManager {
    private val _bots = ConcurrentHashMap<Any, Bot>()
    val bots: Map<Any, Bot> get() = _bots

    fun register(bot: Bot) {
        if (_bots.containsKey(bot.id)) {
            throw IllegalArgumentException("Bot ${bot.id} has been registered")
        }
        _bots[bot.id] = bot
    }

    fun unregister(bot: Bot) {
        _bots.remove(bot.id)
    }

    fun unregister(id: Long) {
        _bots.remove(id)
    }

    fun get(id: Long): Bot? {
        return _bots[id]
    }
}