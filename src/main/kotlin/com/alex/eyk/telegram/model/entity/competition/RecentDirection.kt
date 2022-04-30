package com.alex.eyk.telegram.model.entity.competition

import com.alex.eyk.telegram.model.entity.user.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`user_id`")
    var user: User,

    @Column(name = "`direction`", nullable = false)
    var direction: Int
)
