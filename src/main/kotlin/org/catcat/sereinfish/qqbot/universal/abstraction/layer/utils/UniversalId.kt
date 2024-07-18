package org.catcat.sereinfish.qqbot.universal.abstraction.layer.utils

/**
 * 联系人id
 */
interface UniversalId {
    val id: Any

    /**
     * 判断是否与另一个联系人id相等
     */
    override fun equals(other: Any?): Boolean

    /**
     * 将联系人id编码为字符串
     */
    fun encodeToString(): String

    override fun toString(): String
}

abstract class UniversalIdAbstract: UniversalId {
    override fun equals(other: Any?): Boolean {
        return if (other is UniversalId)
            id == (other as UniversalId).id
        else
            id == other
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}