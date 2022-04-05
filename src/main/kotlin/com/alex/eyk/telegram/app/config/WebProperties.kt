package com.alex.eyk.telegram.app.config

import com.ximand.properties.PropertiesPath
import com.ximand.properties.Property

@PropertiesPath("jarpath:/web.properties")
class WebProperties {

    @Property(name = "web.bmstu_url", defaultValue = "https://priem.bmstu.ru/lists/upload/enrollees/first/moscow-")
    var bmstuUrl: String = ""

    @Property(name = "web.update_delay", defaultValue = "10")
    var updateDelay: Long = 0
}
