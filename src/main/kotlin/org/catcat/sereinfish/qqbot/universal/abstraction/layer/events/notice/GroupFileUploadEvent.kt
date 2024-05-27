package org.catcat.sereinfish.qqbot.universal.abstraction.layer.events.notice

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact.Member

/**
 * 群文件上传事件
 */
interface GroupFileUploadEvent: GroupNoticeEvent {
    // 上传的成员
    val sender: Member
    // 文件信息
    val file: FileInfo

    /**
     * 上传的文件信息
     */
    interface FileInfo {
        // 文件id
        val id: String
        // 文件名称
        val name: String
        // 文件大小
        val size: Long
        val busid: Long
    }
}