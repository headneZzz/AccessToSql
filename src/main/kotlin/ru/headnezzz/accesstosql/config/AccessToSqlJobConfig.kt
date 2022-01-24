package ru.headnezzz.accesstosql.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import ru.headnezzz.accesstosql.batch.AccessToSqlProcessor
import ru.headnezzz.accesstosql.batch.ProcessedItemsCountListener
import ru.headnezzz.accesstosql.model.entity.access.AccessTDelo
import ru.headnezzz.accesstosql.model.entity.sqlserver.SqlTDelo
import javax.sql.DataSource


@Configuration
class AccessToSqlJobConfig {

    @Bean
    fun job(jobBuilder: JobBuilderFactory, jobStep: Step): Job {
        return jobBuilder.get("accessToSqlJob")
            .start(jobStep)
            .build()
    }

    @Bean
    fun jobStep(
        processor: AccessToSqlProcessor,
        listener: ProcessedItemsCountListener,
        stepBuilderFactory: StepBuilderFactory,
        accessJdbcReader: JdbcCursorItemReader<AccessTDelo>,
        sqlServerJdbcWriter: JdbcBatchItemWriter<SqlTDelo>
    ): Step {
        return stepBuilderFactory.get("tDelo")
            .chunk<AccessTDelo, SqlTDelo>(10000)
            .reader(accessJdbcReader)
            .processor(processor)
            .writer(sqlServerJdbcWriter)
            .listener(listener)
            .build()
    }

    private val QUERY = """
select Код_Дела          as kodDela,
       Номер_Дела        as nomerDela,
       Код_Описи         as kodOpisi,
       Код_фонда         as kodFonda,
       Наименование_дела as naimenovanieDela,
       [Кол-во_листов]   as kolvoListov,
       Физич_состояние   as fizichSost,
       Отметка           as otmetka,
       Содержание        as soderzh,
       Год_начала        as startDate,
       Год_конца         as endDate,
       Заголовок_Изменен as isZagolovok,
       Фото              as photo,
       Исполнитель       as executor,
       Дата              as date,
       Точная_дата       as exactDate
from Т_Дело
"""

    @Bean
    @StepScope
    fun accessJdbcReader(@Qualifier("accessDataSource") accessDataSource: DataSource): JdbcCursorItemReader<AccessTDelo> {
        return JdbcCursorItemReaderBuilder<AccessTDelo>()
            .name("tDelo")
            .dataSource(accessDataSource)
            .sql(QUERY)
            .rowMapper(BeanPropertyRowMapper(AccessTDelo::class.java))
            .build()
    }

    private val INSERT_QUERY = """
        SET IDENTITY_INSERT dbo.Т_Дело ON
insert into dbo.Т_Дело (Код_Дела, Номер_Дела, Код_Описи, Код_фонда, Наименование_дела, "Кол-во_листов", Физич_состояние,
                        Отметка, Содержание, Год_начала, Год_конца, Заголовок_Изменен, Фото, Исполнитель, Дата,
                        Точная_дата)
values (:kodDela, :nomerDela, :kodOpisi, :kodFonda, :naimenovanieDela, :kolvoListov, :fizichSost, :otmetka, :soderzh,
        :startDate, :endDate, :isZagolovok, :photo, :executor, :date, :exactDate)
SET IDENTITY_INSERT dbo.Т_Дело OFF
"""

    @Bean
    @StepScope
    fun sqlServerJdbcWriter(sqlServerDataSource: DataSource): JdbcBatchItemWriter<SqlTDelo> {
        return JdbcBatchItemWriterBuilder<SqlTDelo>()
            .dataSource(sqlServerDataSource)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql(INSERT_QUERY)
            .build()
    }
}
