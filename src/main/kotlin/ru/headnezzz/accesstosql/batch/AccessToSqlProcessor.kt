package ru.headnezzz.accesstosql.batch

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.mapper.AccessToSqlMapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTDelo
import ru.headnezzz.accesstosql.model.entity.sqlserver.SqlTDelo

@Component
class AccessToSqlProcessor(val accessToSqlMapper: AccessToSqlMapper): ItemProcessor<AccessTDelo, SqlTDelo> {

    override fun process(item: AccessTDelo): SqlTDelo {
        return accessToSqlMapper.accessToSql(item)
    }
}
