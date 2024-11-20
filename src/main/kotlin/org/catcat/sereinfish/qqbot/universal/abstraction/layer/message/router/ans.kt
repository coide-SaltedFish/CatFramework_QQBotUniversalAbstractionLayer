package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.router


/**
 * 标记函数为路由扩展函数
 *
 * 通常来说只能注解到 MessageRouter 类的扩展函数上
 * 作为扩展支持，通常不启用
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Router(
    val name: String = "", // 路由名称，为空则自动取函数名
)