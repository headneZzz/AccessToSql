package ru.headnezzz.accesstosql.config.batch

import org.springframework.batch.core.Step
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
import ru.headnezzz.accesstosql.batch.ProcessedItemsCountListener
import ru.headnezzz.accesstosql.batch.TFundProcessor
import ru.headnezzz.accesstosql.model.entity.access.AccessTFund
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTFund
import javax.sql.DataSource

@Configuration
class TFundStepConfig {

    @Bean("importTFundStep")
    fun importTFundStep(
        processor: TFundProcessor,
        listener: ProcessedItemsCountListener,
        stepBuilderFactory: StepBuilderFactory,
        tFundAccessJdbcReader: JdbcCursorItemReader<AccessTFund>,
        tFundSqlJdbcWriter: JdbcBatchItemWriter<SqlTFund>
    ): Step {
        return stepBuilderFactory.get("tFund")
            .chunk<AccessTFund, SqlTFund>(10000)
            .reader(tFundAccessJdbcReader)
            .processor(processor)
            .writer(tFundSqlJdbcWriter)
            .listener(listener)
            .build()
    }

    @Bean
    @StepScope
    fun tFundAccessJdbcReader(@Qualifier("accessDataSource") accessDataSource: DataSource): JdbcCursorItemReader<AccessTFund> {
        return JdbcCursorItemReaderBuilder<AccessTFund>()
            .name("tFund")
            .dataSource(accessDataSource)
            .sql(SELECT_T_FUND)
            .rowMapper(BeanPropertyRowMapper(AccessTFund::class.java))
            .build()
    }

    @Bean
    @StepScope
    fun tFundSqlJdbcWriter(sqlServerDataSource: DataSource): JdbcBatchItemWriter<SqlTFund> {
        return JdbcBatchItemWriterBuilder<SqlTFund>()
            .dataSource(sqlServerDataSource)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql(INSERT_T_FUND)
            .build()
    }

    companion object {
        private const val SELECT_T_FUND = """
            select 
            Код_фонда as fundCode,
            Номер_фонда as fundNumber,
            Имя_Т_Дела as tUnitName,
            Наименование_фонда as fundName,
            Число_дел as unitsCount,
            Год_начала as startYear,
            Год_конца as endYear,
            Аннотация as annotation,
            ДСП as dsp,
            Код_Архива as archiveCode
            from Т_Фонд
        """

        private const val INSERT_T_FUND = """
            SET IDENTITY_INSERT dbo.Т_Фонд ON
            insert into dbo.Т_Фонд (Код_фонда, Номер_фонда, Имя_Т_Дела, Наименование_фонда, Число_дел, Год_начала, Год_конца, Аннотация, ДСП, Код_Архива)
            values (:fundCode, :fundNumber, :tUnitName, :fundName, :unitsCount, :startYear, :endYear, :annotation, :dsp, :archiveCode)
            SET IDENTITY_INSERT dbo.Т_Фонд OFF
        """
    }
}
