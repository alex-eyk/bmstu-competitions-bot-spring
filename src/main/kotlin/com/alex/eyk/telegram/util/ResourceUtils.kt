package com.alex.eyk.telegram.util

import com.ximand.properties.JarUtils
import java.io.File

object ResourceUtils {

    fun scanResourceDirectory(directoryPath: String): Set<File> {
        val dictionariesPath = JarUtils.getFileFromJarDirectoryPath(directoryPath, javaClass)
        val file = File(dictionariesPath)
        val innerFiles = file.listFiles()
            ?: throw IllegalStateException("Directory: $directoryPath not contains files")
        return innerFiles.toHashSet()
    }
}
