package ru.headnezzz.accesstosql.runner

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.model.FileRow
import ru.headnezzz.accesstosql.service.MigrationService
import java.io.File


@Component
class SpringRunner(
    val migrationService: MigrationService,
    val jobLauncher: JobLauncher,
    val job: Job
) : CommandLineRunner {
    private val log: Logger = LoggerFactory.getLogger(SpringRunner::class.java)

    @Value("\${config.fileWithDocsPath}")
    lateinit var fileWithDocsPath: String

    override fun run(vararg args: String?) {
        jobLauncher.run(job, createJobParameters())
        val docsFromFile = getDocsFromFile()
        migrationService.complexToArchiveFund(docsFromFile)
        readLine()
    }

    private fun createJobParameters(): JobParameters {
        val confMap: MutableMap<String, JobParameter> = HashMap()
        confMap["time"] = JobParameter(System.currentTimeMillis())
        return JobParameters(confMap)
    }

    private fun getDocsFromFile(): List<FileRow> {
        val result = ArrayList<FileRow>()
        File(fileWithDocsPath).forEachLine {
            val str = it.replace(
                "\\s".toRegex(),
                " "
            ).split(" ")
            result.add(FileRow(str[0], str[1]))
        }
        log.info("Считано фондов из файла: {}", result.size)
        return result
    }
}
