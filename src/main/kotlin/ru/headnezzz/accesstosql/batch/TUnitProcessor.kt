package ru.headnezzz.accesstosql.batch

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.mapper.AccessToSqlMapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTUnit
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTUnit

@Component
class TUnitProcessor(private val accessToSqlMapper: AccessToSqlMapper): ItemProcessor<AccessTUnit, SqlTUnit> {

    override fun process(item: AccessTUnit): SqlTUnit = accessToSqlMapper.mapTUnit(item)
}
