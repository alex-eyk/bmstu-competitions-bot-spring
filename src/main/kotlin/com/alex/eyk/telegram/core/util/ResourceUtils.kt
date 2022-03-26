package com.alex.eyk.telegram.core.util

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.io.File

object ResourceUtils {

    fun scanResourceDirectory(directoryPath: String): Set<File> {
        val resolver = PathMatchingResourcePatternResolver()
        val directory: File = resolver.getResource(directoryPath).file
        val innerFiles = directory.listFiles()
            ?: throw IllegalStateException("Directory not contains files")
        return innerFiles.toHashSet()
    }
}
