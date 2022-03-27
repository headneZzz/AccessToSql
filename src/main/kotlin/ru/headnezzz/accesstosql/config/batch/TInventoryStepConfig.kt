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
import ru.headnezzz.accesstosql.batch.TInventoryProcessor
import ru.headnezzz.accesstosql.model.entity.access.AccessTInventory
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTInventory
import javax.sql.DataSource

@Configuration
class TInventoryStepConfig {

    @Bean("importTInventoryStep")
    fun importTInventoryStep(
        processor: TInventoryProcessor,
        listener: ProcessedItemsCountListener,
        stepBuilderFactory: StepBuilderFactory,
        tInventoryAccessJdbcReader: JdbcCursorItemReader<AccessTInventory>,
        tInventorySqlJdbcWriter: JdbcBatchItemWriter<SqlTInventory>
    ): Step {
        return stepBuilderFactory.get("tInventory")
            .chunk<AccessTInventory, SqlTInventory>(10000)
            .reader(tInventoryAccessJdbcReader)
            .processor(processor)
            .writer(tInventorySqlJdbcWriter)
            .listener(listener)
            .build()
    }

    @Bean
    @StepScope
    fun tInventoryAccessJdbcReader(@Qualifier("accessDataSource") accessDataSource: DataSource): JdbcCursorItemReader<AccessTInventory> {
        return JdbcCursorItemReaderBuilder<AccessTInventory>()
            .name("tInventory")
            .dataSource(accessDataSource)
            .sql(SELECT_T_INVENTORY)
            .rowMapper(BeanPropertyRowMapper(AccessTInventory::class.java))
            .build()
    }

    @Bean
    @StepScope
    fun tInventorySqlJdbcWriter(sqlServerDataSource: DataSource): JdbcBatchItemWriter<SqlTInventory> {
        return JdbcBatchItemWriterBuilder<SqlTInventory>()
            .dataSource(sqlServerDataSource)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql(INSERT_T_INVENTORY)
            .build()
    }

    companion object {
        private const val SELECT_T_INVENTORY = """
            select 
            Код_Описи as inventoryCode,
            Номер_Описи as inventoryNumber,
            Название_описи as inventoryName,
            Год_начала as startYear,
            Год_конца as endYear,
            Код_фонда as fundCode,
            Кол_дел as unitsCount,
            Аннотация as annotation,
            Кол_экземп as copiesCount,
            Полка as shelf,
            Кол_связок as bundlesCount,
            Кол_коробок as boxesCount,
            Фото as photo
            from Т_Описи
        """

        private const val INSERT_T_INVENTORY = """
            SET IDENTITY_INSERT dbo.Т_Описи ON
            insert into dbo.Т_Описи (Код_Описи, Номер_Описи, Название_описи, Год_начала, Год_конца, Код_фонда, Кол_дел, Аннотация, Кол_экземп, Полка, Кол_связок, Кол_коробок, Фото)
            values (:inventoryCode, :inventoryNumber, :inventoryName, :startYear, :endYear, :fundCode, :unitsCount, :annotation, :copiesCount, :shelf, :bundlesCount, :boxesCount, :photo)
            SET IDENTITY_INSERT dbo.Т_Описи OFF
        """
    }
}
