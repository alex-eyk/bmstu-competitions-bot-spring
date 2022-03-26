package com.alex.eyk.telegram.core.config

import com.ximand.properties.PropertiesPath
import com.ximand.properties.Property

@PropertiesPath("jarpath:/app.properties")
class ServerProperties {

    @Property(name = "server.threads", defaultValue = "4")
    var threads = 4
}
