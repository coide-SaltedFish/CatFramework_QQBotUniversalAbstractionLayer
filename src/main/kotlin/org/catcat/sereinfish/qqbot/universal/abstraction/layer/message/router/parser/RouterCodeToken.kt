package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

/**
 * token
 */
internal data class RouterCodeToken (
    val type: RouterCodeTokenType,
    val lexeme: String,
    val line: Int,
    val column: Int
)