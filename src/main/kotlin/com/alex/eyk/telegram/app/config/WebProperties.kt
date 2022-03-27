package com.alex.eyk.telegram.app.config

import com.ximand.properties.PropertiesPath
import com.ximand.properties.Property

@PropertiesPath("jarpath:/web.properties")
class WebProperties {

    @Property(name = "web.bmstu_url")
    var bmstuUrl: String = ""
}
