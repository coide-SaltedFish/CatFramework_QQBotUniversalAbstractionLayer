package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

enum class RouterCodeTokenType {
    LEFT_BRACKET, // 左括号
    RIGHT_BRACKET, // 右括号
    COLON, // 冒号
    COMMA, // 逗号
    STRING, // 字符串
    EOF, // 结束符
}