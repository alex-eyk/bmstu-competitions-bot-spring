package com.alex.eyk.telegram.app.config

import com.ximand.properties.PropertiesProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun webProperties(): WebProperties {
        val propertiesProvider = PropertiesProvider()
        return propertiesProvider.createInstance(WebProperties::class.java)
    }
}
