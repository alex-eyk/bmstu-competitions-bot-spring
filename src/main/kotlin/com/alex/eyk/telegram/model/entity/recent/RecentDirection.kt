package com.alex.eyk.telegram.model.entity.recent

import com.alex.eyk.telegram.model.entity.user.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "`recent_directions`")
@Entity
data class RecentDirection(

    @Id
    @Column(name = "`id`", nullable = false)
    var id: Long,

    @ManyToOne
    @JoinColumn(name = "`chat`")
    var user: User,

    @Column(name = "`direction`", nullable = false)
    var direction: Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecentDirection

        if (direction != other.direction) return false

        return true
    }

    override fun hashCode(): Int {
        return direction
    }
}
