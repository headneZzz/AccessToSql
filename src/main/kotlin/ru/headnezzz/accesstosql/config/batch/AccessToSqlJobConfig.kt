package ru.headnezzz.accesstosql.config.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AccessToSqlJobConfig {

    @Bean
    fun job(
        jobBuilder: JobBuilderFactory,
        importTUnitStep: Step,
        importTInventoryStep: Step,
        importTFundStep: Step
    ): Job {
        return jobBuilder.get("accessToSqlJob")
            .start(importTFundStep)
            .next(importTInventoryStep)
            .next(importTUnitStep)
            .build()
    }
}
