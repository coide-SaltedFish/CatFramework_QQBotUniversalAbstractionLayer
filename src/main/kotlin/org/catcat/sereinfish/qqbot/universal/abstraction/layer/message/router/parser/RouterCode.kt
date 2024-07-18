package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.MessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.extend.buildMessageRouter
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.impl.*

/**
 * 路由码解析器
 */
object RouterCode {
    private val routerEncodes = HashMap<String, MessageRouterEncode<*>>()

    init {
        resgister(AtRouter)
        resgister(EndRouter)
        resgister(OptionalRouter)
        resgister(OrRouter)
        resgister(ParameterRouter)
        resgister(RegexRouter)
        resgister(TextRouter)
        resgister(TypeRouter)
    }

    /**
     * 注册解析器
     */
    fun resgister(encode: MessageRouterEncode<*>) {
        routerEncodes[encode.target] = encode
    }

    /**
     * 将字符串解析为路由
     */
    fun parse(code: String): MessageRouter {
        return parseCodeToNodes(code).map {
            parseNode(it)
        }.let {
            if (it.size == 1)
                it.first()
            else
                buildMessageRouter {
                    it.forEach {
                        add(it)
                    }
                }
        }
    }

    /**
     * 将字符串解析为路由节点
     */
    private fun parseCodeToNodes(code: String): List<RouterCodeNode> {
        val nodes = splitString(code)
        println("split -> " + nodes.joinToString { it })
        return nodes.map {
            RouterCodeNode(it)
        }
    }

    private fun parseString(input: String): List<String> {
        val result = ArrayList<String>()
        var level = 0
        var current = ""

        for (c in input) {
            when(c) {
                '[' -> {
                    if (level == 0 && current.isNotEmpty()) {
                        result.add(current)
                        current = ""
                    }
                    level ++
                    current += c
                }
                ']' -> {
                    level --
                    current += c
                    if (level == 0) {
                        result.add(current)
                        current = ""
                    }
                }
                else -> current += c
            }
        }
        if (current.isNotEmpty())
            result.add(current)
        return result
    }

    private fun splitString(input: String): List<String> {
        val result = mutableListOf<String>()
        val stack = mutableListOf<Char>()
        var currentSegment = StringBuilder()
        var inQuotes = false
        var quoteChar = ' '

        for (char in input) {
            if (char == '"' || char == '\'') {
                if (inQuotes && char == quoteChar) {
                    inQuotes = false
                } else if (!inQuotes) {
                    inQuotes = true
                    quoteChar = char
                }
            }

            if (!inQuotes) {
                when (char) {
                    '[' -> {
                        if (stack.isEmpty()) {
                            if (currentSegment.isNotEmpty()) {
                                result.add(currentSegment.toString())
                                currentSegment = StringBuilder()
                            }
                        }
                        stack.add(char)
                    }
                    ']' -> {
                        if (stack.isNotEmpty()) {
                            stack.removeAt(stack.lastIndex)
                            if (stack.isEmpty()) {
                                currentSegment.append(char)
                                result.add(currentSegment.toString())
                                currentSegment = StringBuilder()
                                continue
                            }
                        }
                    }
                }
            }

            currentSegment.append(char)
        }

        if (currentSegment.isNotEmpty()) {
            result.add(currentSegment.toString())
        }

        return result
    }

    /**
     * 将节点解析为路由
     */
    private fun parseNode(node: RouterCodeNode): MessageRouter {
        val encoder = routerEncodes.getOrElse(node.target){ error("无法找到路由编码器: ${node.target}") }
        // 构建路由参数
        val params = node.params.map { if (it.isText()) it.code else parseNode(it) }.toTypedArray()
        return encoder.decode(*params)
    }

    /**
     * 将路由解析为字符串
     */
    fun encode(router: MessageRouter): String {
        return router.encode()
    }
}

fun main() {
    val chain = RouterCode.parse("""[optional:[text:"[[[][[123"]]123[or:[text:123],[type:text]]""")
    println(chain.encode())
}