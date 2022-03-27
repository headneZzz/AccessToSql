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
import ru.headnezzz.accesstosql.batch.TUnitProcessor
import ru.headnezzz.accesstosql.model.entity.access.AccessTUnit
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTUnit
import javax.sql.DataSource

@Configuration
class TUnitStepConfig {

    @Bean("importTUnitStep")
    fun importTUnitStep(
        processor: TUnitProcessor,
        listener: ProcessedItemsCountListener,
        stepBuilderFactory: StepBuilderFactory,
        accessJdbcReader: JdbcCursorItemReader<AccessTUnit>,
        sqlServerJdbcWriter: JdbcBatchItemWriter<SqlTUnit>
    ): Step {
        return stepBuilderFactory.get("tUnit")
                .chunk<AccessTUnit, SqlTUnit>(10000)
                .reader(accessJdbcReader)
                .processor(processor)
                .writer(sqlServerJdbcWriter)
                .listener(listener)
                .build()
    }

    @Bean
    @StepScope
    fun accessJdbcReader(@Qualifier("accessDataSource") accessDataSource: DataSource): JdbcCursorItemReader<AccessTUnit> {
        return JdbcCursorItemReaderBuilder<AccessTUnit>()
            .name("tUnit")
            .dataSource(accessDataSource)
            .sql(SELECT_T_UNIT)
            .rowMapper(BeanPropertyRowMapper(AccessTUnit::class.java))
            .build()
    }

    @Bean
    @StepScope
    fun sqlServerJdbcWriter(sqlServerDataSource: DataSource): JdbcBatchItemWriter<SqlTUnit> {
        return JdbcBatchItemWriterBuilder<SqlTUnit>()
            .dataSource(sqlServerDataSource)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql(INSERT_T_UNIT)
            .build()
    }

    companion object {
        private const val SELECT_T_UNIT = """
            select Код_Дела          as unitCode,
            Номер_Дела        as unitNumber,
            Код_Описи         as inventoryCode,
            Код_фонда         as fundCode,
            Наименование_дела as unitName,
            [Кол-во_листов]   as pagesCount,
            Физич_состояние   as physicalState,
            Отметка           as mark,
            Содержание        as content,
            Год_начала        as startYear,
            Год_конца         as endYear,
            Заголовок_Изменен as isTitleChanged,
            Фото              as photo,
            Исполнитель       as executor,
            Дата              as date,
            Точная_дата       as exactDate
            from Т_Дело
        """

        private const val INSERT_T_UNIT = """
            SET IDENTITY_INSERT dbo.Т_Дело ON
            insert into dbo.Т_Дело (Код_Дела, Номер_Дела, Код_Описи, Код_фонда, Наименование_дела, "Кол-во_листов", Физич_состояние,
                            Отметка, Содержание, Год_начала, Год_конца, Заголовок_Изменен, Фото, Исполнитель, Дата,
                            Точная_дата)
            values (:unitCode, :unitNumber, :inventoryCode, :fundCode, :unitName, :pagesCount, :physicalState, :mark, :content,
            :startYear, :endYear, :isTitleChanged, :photo, :executor, :date, :exactDate)
            SET IDENTITY_INSERT dbo.Т_Дело OFF
        """
    }
}
