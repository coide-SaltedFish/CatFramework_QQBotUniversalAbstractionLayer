import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.SimpleMessageRouterBuilder
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.extend.text
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.*
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.RouterCodeLexer
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser.RouterCodeToken
import org.sereinfish.cat.frame.utils.logger

internal class RouterCodeParser(
    code: String,
    val routerEncodes: HashMap<String, MessageRouterEncode<*>>
) {
    private val logger = logger()

    private val lexer = RouterCodeLexer(code)
    private val tokens = lexer.let {
        mutableListOf<RouterCodeToken>().apply {
            var token = it.nextToken()
            while (token.type != RouterCodeTokenType.EOF) {
                add(token)
                token = it.nextToken()
            }
            add(token)
        }
//            .also {
//                it.forEachIndexed { index, routerCodeToken ->
//                    println("$index => $routerCodeToken")
//                }
//            }
    }

    private var position: Int = 0

    /**
     * 当前字符位置
     */
    private fun currentCharPosition(): Int {
        return tokens.subList(0, position).sumOf { it.lexeme.length } + 1
    }

    private fun currentToken(): RouterCodeToken = tokens[position]

    private fun beforeToken(): RouterCodeToken = tokens[maxOf(position - 1, 0)]

    private fun nextToken() {
        if (position < tokens.size - 1) {
            position++
        }
    }

    private fun expectToken(
        type: RouterCodeTokenType,
        errorInfo: (RouterCodeToken) -> String = { token ->
            "Expected token of type $type but found ${token.type} in ${currentCharPosition()}"
        }
    ): RouterCodeToken {
        val token = currentToken()
        if (token.type != type) {
            if (token.type == RouterCodeTokenType.EOF)
                throw IllegalArgumentException("Unexpected end of input in ${currentCharPosition()}.")
            else
                throw IllegalArgumentException(errorInfo(token))
        }
        nextToken()
        return token
    }

    fun parse(): MessageRouter {
        val builder = SimpleMessageRouterBuilder()
        while (currentToken().type != RouterCodeTokenType.EOF) {
            when (currentToken().type) {
                RouterCodeTokenType.STRING -> {
                    // 解析为字符串路由
                    builder.add(builder.text(currentToken().lexeme))
                    nextToken()
                }
                RouterCodeTokenType.LEFT_BRACKET -> {
                    // 解析路由
                    parseRoute(builder)
                }

                else -> throw IllegalArgumentException("Unexpected token ${currentToken().type} in ${currentCharPosition()}.")
            }
        }
        return builder.build()
    }

    /**
     * 解析路由
     */
    private fun parseRoute(builder: SimpleMessageRouterBuilder) {
        val routerStartPos = currentCharPosition()

        expectToken(RouterCodeTokenType.LEFT_BRACKET) {
            "Expected LEFT_BRACKET '[' after key '${beforeToken().lexeme}' in ${currentCharPosition()}."
        }
        val type = expectToken(RouterCodeTokenType.STRING) {
            if (it.type == RouterCodeTokenType.RIGHT_BRACKET){
                "Expected parameter name but found '${it.type}' in ${currentCharPosition()}."
            }else "Unexpected token type '${it.type}' in ${currentCharPosition()}."
        }
        val params = mutableListOf<Any?>()
        // 如果有参数
        if (currentToken().type == RouterCodeTokenType.COLON) {
            expectToken(RouterCodeTokenType.COLON) {
                "Expected COLON ':' after key '${beforeToken().lexeme}' in ${currentCharPosition()}."
            }
            // 解析参数
            while (currentToken().type != RouterCodeTokenType.RIGHT_BRACKET) {
                when (currentToken().type) {
                    RouterCodeTokenType.STRING -> {
                        params.add(currentToken().lexeme)
                        nextToken()
                    }
                    RouterCodeTokenType.LEFT_BRACKET -> {
                        val paramsRouterBuilder = SimpleMessageRouterBuilder()
                        while (currentToken().type == RouterCodeTokenType.LEFT_BRACKET) {
                            parseRoute(paramsRouterBuilder)
                        }
                        params.add(paramsRouterBuilder.build())
                    }
                    else -> throw IllegalArgumentException("Expected token of type STRING or LEFT_BRACKET but found ${currentToken().type} in ${currentCharPosition()}")
                }
                if (currentToken().type != RouterCodeTokenType.RIGHT_BRACKET)
                    expectToken(RouterCodeTokenType.COMMA) {
                        "Expected COMMA ',' after key '${beforeToken().lexeme}' at in ${currentCharPosition()}."
                    }
            }
        }
        builder.add(runCatching {
            buildRouter(type.lexeme, params.toTypedArray())
        }.getOrElse {
            logger.error("路由解析异常", it)
            error("Unexpected token '[${type.lexeme}:${params.joinToString { "'${it.toString()}'" }}]' at in ${routerStartPos}: ${it.message}")
        })
        expectToken(RouterCodeTokenType.RIGHT_BRACKET) {
            "Unmatched left bracket '[' at in ${currentCharPosition()}."
        }
    }

    /**
     * 构建一个路由
     */
    private fun buildRouter(type: String, params: Array<Any?>): MessageRouter {
        val encoder = routerEncodes.getOrElse(type){ error("无法找到路由编码器: ${type}") }
        return encoder.decode(*params)
    }
}

fun main() {
    val routerEncodes = HashMap<String, MessageRouterEncode<*>>().apply {
        put(AllRouter.target, AllRouter)
        put(AtRouter.target, AtRouter)
        put(EndRouter.target, EndRouter)
        put(OptionalRouter.target, OptionalRouter)
        put(OrRouter.target, OrRouter)
        put(ParameterRouter.target, ParameterRouter)
        put(RegexRouter.target, RegexRouter)
        put(TextRouter.target, TextRouter)
        put(TypeRouter.target, TypeRouter)
    }
    val input = """123:123"""
    println("code=$input")
    val parser = RouterCodeParser(input, routerEncodes)
    val ast = parser.parse()
    println(ast.encode())
}