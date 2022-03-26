package com.alex.eyk.telegram.core.entity.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun getByChat(chat: Long): User?
}
