package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

/**
 * 词法分析器
 * 将路由码解析为 token 列表
 */
internal class RouterCodeLexer(
    val input: String
) {
    private var position: Int = 0
    private var line: Int = 1
    private var column: Int = 1

    val tokens = mutableListOf<RouterCodeToken>()

    companion object {
        const val EOF = '\u0000'
        // 关键字列表
        private val keywords = setOf<String>()
    }

    /**
     * 下一个字符
     */
    private fun nextChar(): Char {
        if (position >= input.length) return EOF // 表示EOF
        val char = input[position]
        position++
        if (char == '\n') {
            line++
            column = 1
        } else {
            column++
        }
        return char
    }

    /**
     * 查看下一个字符但不移动指针
     */
    private fun peekChar(): Char {
        return if (position >= input.length) EOF else input[position]
    }
    // 跳过空白符和注释
    private fun skipWhitespace() {
        while (peekChar().isWhitespace()) {
            nextChar()
        }
    }

    // 处理字符串'
    private fun readString(quote: Regex, prefix: String = ""): RouterCodeToken {
        val start = position
        val startColumn = column
        val stringBuilder = StringBuilder(prefix)
        while (true) {
            val char = nextChar()
            if ((char == '\u0000' || char == '\n') && quote.matches("$char").not()) {
                // 未关闭的字符串
                throw IllegalStateException("Unterminated string at line $line, column $startColumn")
            }

            if (quote.matches("$char")) {
                break
            }
            if (char == '\\') {
                // 处理转义字符
                val next = nextChar()
                when (next) {
                    'n' -> stringBuilder.append('\n')
                    't' -> stringBuilder.append('\t')
                    'r' -> stringBuilder.append('\r')
                    '\\' -> stringBuilder.append('\\')
                    '"' -> stringBuilder.append('"')
                    '\'' -> stringBuilder.append('\'')
                    else -> stringBuilder.append(next)
                }
            } else {
                stringBuilder.append(char)
            }
        }
        return RouterCodeToken(RouterCodeTokenType.STRING, stringBuilder.toString(), line, startColumn)
    }

    // 获取下一个Token
    fun nextToken(): RouterCodeToken {
        skipWhitespace()

        val start = position
        val startColumn = column

        val char = nextChar()
        if (char == '\u0000') return RouterCodeToken(RouterCodeTokenType.EOF, "", line, startColumn)

        return when (char) {
            '[' -> RouterCodeToken(RouterCodeTokenType.LEFT_BRACKET, char.toString(), line, startColumn)
            ']' -> RouterCodeToken(RouterCodeTokenType.RIGHT_BRACKET, char.toString(), line, startColumn)
            ':' -> RouterCodeToken(RouterCodeTokenType.COLON, char.toString(), line, startColumn)
            ',' -> RouterCodeToken(RouterCodeTokenType.COMMA, char.toString(), line, startColumn)
            '"' -> readString("\"".toRegex()) // 处理双引号字符串
            '\'' -> readString("\'".toRegex())
            else -> {
                readString("""[\[\]:,"'\u0000]""".toRegex(), "$char").also {
                    if (peekChar() != EOF) position --
                }
            }
        }
    }
}

fun main() {
    val sourceCode = """
        123:[text:"账号："]
    """.trimIndent()

    val lexer = RouterCodeLexer(sourceCode)
    while (true) {
        val token = lexer.nextToken()
        println("Token(type=${token.type}, lexeme='${token.lexeme}', line=${token.line}, column=${token.column})")
        if (token.type == RouterCodeTokenType.EOF) break
    }
}