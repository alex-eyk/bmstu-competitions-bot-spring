package com.alex.eyk.telegram.config

import com.ximand.properties.PropertiesProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class Config {

    @Lazy(false)
    @Bean
    fun webProperties(): WebProperties {
        val propertiesProvider = PropertiesProvider()
        return propertiesProvider.createInstance(WebProperties::class.java)
    }

    @Lazy(false)
    @Bean
    fun serverProperties(): ServerProperties {
        val propertiesProvider = PropertiesProvider()
        return propertiesProvider.createInstance(ServerProperties::class.java)
    }
}
