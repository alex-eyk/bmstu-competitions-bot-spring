package com.alex.eyk.telegram.core.entity.user

import com.alex.eyk.telegram.core.entity.Activity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "`user`")
@Entity
data class User(

    @Id
    @Column(name = "`chat`", unique = true, nullable = false)
    var chat: Long,

    @Column(name = "`lang`", nullable = false)
    var languageCode: String,

    @Column(name = "`enabled`", nullable = false)
    var enabled: Boolean = true,

    @Column(name = "`activity`", nullable = false)
    var activity: Activity = Activity.NONE
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (chat != other.chat) return false
        if (languageCode != other.languageCode) return false
        if (enabled != other.enabled) return false
        if (activity != other.activity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chat.hashCode()
        result = 31 * result + languageCode.hashCode()
        result = 31 * result + enabled.hashCode()
        result = 31 * result + activity.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(chat=$chat)"
    }
}
