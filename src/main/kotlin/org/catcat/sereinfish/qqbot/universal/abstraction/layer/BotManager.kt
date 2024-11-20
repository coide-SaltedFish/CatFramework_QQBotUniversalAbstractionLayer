package org.catcat.sereinfish.qqbot.universal.abstraction.layer

import java.util.concurrent.ConcurrentHashMap

object BotManager {
    private val _bots = ConcurrentHashMap<String, Bot>()
    val bots: Map<String, Bot> get() = _bots

    fun register(bot: Bot) {
        if (_bots.containsKey(bot.id.encodeToString())) {
            throw IllegalArgumentException("Bot ${bot.id} has been registered")
        }
        _bots[bot.id.encodeToString()] = bot
    }

    fun unregister(bot: Bot) {
        _bots.remove(bot.id.encodeToString())
    }

    fun unregister(id: String) {
        _bots.remove(id)
    }

    fun get(id: String): Bot? {
        return _bots[id]
    }
}