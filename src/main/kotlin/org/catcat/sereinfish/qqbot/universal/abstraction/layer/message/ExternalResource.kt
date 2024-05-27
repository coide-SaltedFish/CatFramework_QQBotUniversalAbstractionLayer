package org.catcat.sereinfish.qqbot.universal.abstraction.layer.message

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.Image
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.Video
import org.catcat.sereinfish.qqbot.universal.abstraction.layer.message.element.Voice
import java.io.InputStream

/**
 * 用于上传文件
 */
interface ExternalResource {
    // 文件流
    val inputStream: InputStream

    // 完成文件使用后自动关闭
    var autoCloseable: Boolean

    suspend fun uploadAsImage(): Image

    suspend fun uploadAsVideo(): Video

    suspend fun uploadAsVoice(): Voice
}