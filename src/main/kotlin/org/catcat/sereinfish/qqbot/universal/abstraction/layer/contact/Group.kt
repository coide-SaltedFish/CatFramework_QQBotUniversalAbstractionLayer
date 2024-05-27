package org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact

import org.catcat.sereinfish.qqbot.universal.abstraction.layer.Bot

/**
 * 群对象
 */
interface Group: Contact {

    /**
     * 群名，设置值同成员群名片
     */
    var groupName: String

    /**
     * 群成员列表
     */
    val members: Map<Long, Member>

    /**
     * 踢出指定群员
     *
     * @throws Exception 无权限则抛出异常 TODO 添加专用异常类型
     *
     * @param id 要踢出的群成员id
     * @param allowRejoinAfterKickout 是否允许再次申请加群
     */
    suspend fun kick(id: Long, allowRejoinAfterKickout: Boolean = false): Boolean


    /**
     * 禁言该成员一定时间
     *
     * @throws Exception 无权限则抛出异常 TODO 添加专用异常类型
     *
     * @param id 要禁言的群成员id
     * @param time 禁言时长，单位秒
     */
    suspend fun mute(id: Long, time: Int): Boolean

    /**
     * 全体禁言状态
     *
     * 设置值则同步设置群全体禁言状态，失败抛出无权限异常
     */
    suspend fun allMute(flag: Boolean = true): Boolean

    /**
     * bot退出该群聊
     *
     * @param isDismiss 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     */
    suspend fun leave(isDismiss: Boolean = true): Boolean

    /**
     * 设置指定群员的群管理状态
     */
    suspend fun admin(id: Long, set: Boolean): Boolean
}