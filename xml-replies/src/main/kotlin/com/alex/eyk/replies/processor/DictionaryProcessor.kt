package com.alex.eyk.replies.processor

import com.alex.eyk.replies.dictionary.Argument
import com.alex.eyk.replies.dictionary.Dictionary
import com.alex.eyk.replies.processor.generator.ArgsBuilderGenerator
import com.alex.eyk.replies.processor.generator.TypeGenerator
import com.alex.eyk.replies.processor.generator.key.ReplyKeysGenerator
import com.alex.eyk.replies.processor.generator.key.WordKeysGenerator
import com.alex.eyk.replies.xml.impl.ArgumentsParser
import com.alex.eyk.replies.xml.impl.DictionaryParser
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.ximand.properties.PropertiesProvider
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

class DictionaryProcessor : AbstractProcessor() {

    private lateinit var defaultDictionary: Dictionary
    private lateinit var defaultDictionaryFile: File

    private lateinit var generatedDir: String

    companion object {

        private const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        private const val ARGS_BUILDER_SUFFIX = "ArgumentsBuilder"
        private const val BUILDER_PACKAGE = "com.alex.eyk.dictionary.builder"
        private const val KEYS_PACKAGE = "com.alex.eyk.dictionary.keys"
    }

    private val properties: ProcessorProperties = PropertiesProvider()
        .createInstance(ProcessorProperties::class.java)

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf("*")
    }

    override fun process(
        annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment
    ): Boolean {
        this.generatedDir = getGeneratedDir()
        initDefaultDictionary(getAllDictionaryFiles())
        createArgBuilders()
        createDataKeysObjects()
        return true
    }

    private fun getGeneratedDir(): String {
        return processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
            ?: throw IllegalStateException(
                "Unable to get dir for generated kotlin files"
            )
    }

    private fun initDefaultDictionary(dictionaryFiles: Array<File>) {
        for (file in dictionaryFiles) {
            if (file.isSchema()) {
                continue
            }
            val dictionary = DictionaryParser().parse(file)
            if (dictionary.default) {
                this.defaultDictionary = dictionary
                this.defaultDictionaryFile = file
                return
            }
        }
        throw IllegalStateException("Unable to found default dictionary")
    }

    private fun createArgBuilders() {
        val replyArgsMap = ArgumentsParser().parse(defaultDictionaryFile)
        val generator: TypeGenerator<List<Argument>> = ArgsBuilderGenerator()
        for (arg in replyArgsMap) {
            val typeSpec = generator.generate(arg.key + ARGS_BUILDER_SUFFIX, arg.value)
            val fileSpec = createFileSpec(BUILDER_PACKAGE, typeSpec)
            fileSpec.writeTo(File(generatedDir))
        }
    }

    private fun createDataKeysObjects() {
        val replyKeysTypeSpec = ReplyKeysGenerator()
            .generate(properties.generatedReplyFilename, defaultDictionary)
        val wordKeysTypeSpec = WordKeysGenerator()
            .generate(properties.generatedWordFilename, defaultDictionary)
        for (typeSpec in listOf(replyKeysTypeSpec, wordKeysTypeSpec)) {
            val fileSpec = createFileSpec(KEYS_PACKAGE, typeSpec)
            fileSpec.writeTo(File(generatedDir))
        }
    }

    private fun createFileSpec(package_: String, typeSpec: TypeSpec): FileSpec {
        return FileSpec
            .builder(package_, typeSpec.name!!)
            .addType(typeSpec)
            .build()
    }

    private fun getAllDictionaryFiles(): Array<File> {
        val directory = File(properties.dictionariesPath)
        return directory.listFiles()
            ?: throw IllegalStateException(
                "No one dictionary file found, path: ${properties.dictionariesPath}"
            )
    }

    private fun File.isSchema(): Boolean {
        return name.endsWith(".xsd")
    }
}
