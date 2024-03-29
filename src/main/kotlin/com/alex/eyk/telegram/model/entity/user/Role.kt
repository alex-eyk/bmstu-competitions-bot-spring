package com.alex.eyk.telegram.model.entity.user

enum class Role(val level: Int) {
    USER(1);

    fun include(role: Role): Boolean {
        return this.level >= role.level
    }
}
