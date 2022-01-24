package ru.headnezzz.accesstosql

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableBatchProcessing
class AccessToSqlApplication

fun main(args: Array<String>) {
    runApplication<AccessToSqlApplication>(*args)
}
