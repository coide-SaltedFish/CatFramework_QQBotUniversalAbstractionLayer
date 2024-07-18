package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router.parser

class RouterCodeNode(
    code: String
) {
    companion object {
        private val commaRegex = "(?:\\[.*?\\]|\\\".*?\\\"|[^,\\[]+)|(?<!\\\\),".toRegex()
        private val isTextRegex = """^\s*\[\s*(.*)\s*]\s*$""".toRegex()

        // 用于检查是否是正确的字符串
        private val checkStringRegex = """^(["']).*\1$""".toRegex()
    }

    private val _code = code

    private val codesSplit by lazy {
        if ("""^\s*\[.*]\s*$""".toRegex().matches(_code))
            code.substring(1, code.length - 1).split(":", limit = 2)
        else
            listOf("text", _code)
    }
    private var _target = codesSplit[0]

    val target get() = if (isText()) "text" else _target
    val params: List<RouterCodeNode> by lazy { params() }
    val code = value() // 获取解析后的路由码

    /**
     * 判断是否为字符串
     */
    fun isText(): Boolean {
        return isTextRegex.matches(_code).not()
    }

    fun isRouter() = isText().not()

    private fun value(): String {
        return if (isText()) {
            if ("""^\s*(['"]).*\1\s*$""".toRegex().matches(_code.trim())) { // 判断是否是引号包围的字符串
                if (checkStringRegex.matches(_code.trim()))
                    """^\s*(['"])(.*?)\1\s*$""".toRegex().find(_code.trim())?.groups?.get(2)?.value ?: error("无法取出对应字符串：${_code.trim()}")
                else
                    error("这似乎是一个字符串，但无法通过字符串检查：${_code.trim()}")
            }else _code
        }else {
            return "[$target:${params.joinToString(",") { it.code }}]"
        }
    }

    /**
     * 解析路由码参数
     *
     * 按照未转义以及未被引号包含的逗号分割
     */
    private fun params(): List<RouterCodeNode> {
        println("$target -> params: ${codesSplit[1]}")
        // 判断是否是引号包围的字符串
        if ("""^\s*(['"]).*\1\s*$""".toRegex().matches(codesSplit[1].trim()).not()) {
            // 是否是路由格式
            if ("""^\s*\[.*]\s*$""".toRegex().matches(codesSplit[1].trim()).not())
                // 检查是否符合要求
                if ("""[\[\]:'"]""".toRegex().containsMatchIn(codesSplit[1].trim()))
                    error("参数包含不允许的字符{${"""[]:'"]"""}}：${codesSplit[1].trim()}")
        }

//        return commaRegex.findAll(codesSplit[1]).map { it.value }.filter { it.isNotEmpty() && it != "," }.toList().map {
//            println("param -> $it")
//            RouterCodeNode(it)
//        }

        return splitParamsString(codesSplit[1]).map {
            println("param -> $it")
            RouterCodeNode(it)
        }
    }

    private fun splitParamsString(input: String): List<String> {
        val regex = Regex("""\[.*?\]|\".*?\"|[^,\[]+|(?<!\\),""")
        val matches = regex.findAll(input).map { it.value }.toList()

        val result = mutableListOf<String>()
        val currentSegment = StringBuilder()
        var bracketDepth = 0
        var inQuotes = false
        var quoteChar = ' '

        for (match in matches) {
            if (match == ",") {
                if (bracketDepth == 0 && !inQuotes) {
                    if (currentSegment.isNotEmpty()) {
                        result.add(currentSegment.toString())
                        currentSegment.clear()
                    }
//                    result.add(match)
                } else {
                    currentSegment.append(match)
                }
            } else {
                for (char in match) {
                    if (char == '"' || char == '\'') {
                        if (inQuotes && char == quoteChar) {
                            inQuotes = false
                        } else if (!inQuotes) {
                            inQuotes = true
                            quoteChar = char
                        }
                    }

                    if (!inQuotes) {
                        if (char == '[') {
                            bracketDepth++
                        } else if (char == ']') {
                            bracketDepth--
                        }
                    }

                    currentSegment.append(char)
                }
            }
        }

        if (currentSegment.isNotEmpty()) {
            result.add(currentSegment.toString())
        }

        return result
    }

    override fun toString(): String {
        return if (isText()) "[text:\"$_code\"]" else buildString {
            append("[$target:")
            append(params.map { it.toString() }.joinToString(",") { it })
            append("]")
        }
    }
}

fun main() {
    val code = """[or:[text:"123,3333"],"123,hello",[text: "hello\nworld!"],[type: text]]"""
    println(code)
    val node = RouterCodeNode(code)

    println("code: ${node.code}")
}