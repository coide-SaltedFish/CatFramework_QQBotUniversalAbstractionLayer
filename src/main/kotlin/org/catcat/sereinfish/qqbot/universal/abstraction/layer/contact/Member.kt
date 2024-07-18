package org.catcat.sereinfish.qqbot.universal.abstraction.layer.contact

/**
 * 成员
 * 一般指群成员
 */
interface Member: User {
    val group: Group

    /**
     * 备注名称
     */
    val remarkNickname: String

    /**
     * 是否是群管理员
     *
     * 设置值则等同于变更，失败抛出异常 TODO 无权限则抛出异常 添加专用异常类型
     */
    var admin: Boolean

    /**
     * 群备注名称
     *
     * 设置该名称则对应修改群名片，失败抛出异常 TODO 无权限则抛出异常 添加专用异常类型
     */
    var cardName: String

    /**
     * 获取最终的名字
     */
    val cardNameOrRemarkNameOrNickName: String get() =
        if (cardName.isNotEmpty()) cardName
        else if (remarkNickname.isNotEmpty()) remarkNickname
        else name


    /**
     * 专属头衔，设置同群名片
     */
    var specialTitle: String

    /**
     * 禁言该成员一定时间
     *
     * @throws Exception 无权限则抛出异常 TODO 添加专用异常类型
     *
     * @param time 禁言时长，单位秒
     */
    suspend fun mute(time: Int): Boolean

    /**
     * 取消成员禁言
     */
    suspend fun unmute(): Boolean

    /**
     * 踢出该群员
     *
     * @throws Exception 无权限则抛出异常 TODO 添加专用异常类型
     *
     * @param allowRejoinAfterKickOut 是否允许再次申请加群
     */
    suspend fun kick(allowRejoinAfterKickOut: Boolean = false): Boolean
}