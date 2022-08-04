package com.alex.eyk.telegram.telegram.handler.perm

import com.alex.eyk.telegram.model.entity.user.User
import com.alex.eyk.telegram.telegram.method.AbsentApiMethod

class IgnoreStrategy : PermissionDeniedStrategy {

    override fun onPermissionDenied(user: User): AbsentApiMethod {
        return AbsentApiMethod()
    }
}
