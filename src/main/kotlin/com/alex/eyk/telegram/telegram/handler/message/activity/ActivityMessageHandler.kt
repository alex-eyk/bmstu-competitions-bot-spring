package com.alex.eyk.telegram.telegram.handler.message.activity

import com.alex.eyk.telegram.model.entity.user.Activity
import com.alex.eyk.telegram.telegram.handler.AbstractHandler

abstract class ActivityMessageHandler(
    val activity: Activity
) : AbstractHandler()
