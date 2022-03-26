package com.alex.eyk.telegram.core.handler.message.activity

import com.alex.eyk.telegram.core.entity.Activity
import com.alex.eyk.telegram.core.handler.AbstractHandler

abstract class ActivityMessageHandler(
    val activity: Activity
) : AbstractHandler()
