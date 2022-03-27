package ru.headnezzz.accesstosql.batch

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.mapper.AccessToSqlMapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTInventory
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTInventory

@Component
class TInventoryProcessor(private val accessToSqlMapper: AccessToSqlMapper): ItemProcessor<AccessTInventory, SqlTInventory> {

    override fun process(item: AccessTInventory): SqlTInventory = accessToSqlMapper.mapTInventory(item)
}
