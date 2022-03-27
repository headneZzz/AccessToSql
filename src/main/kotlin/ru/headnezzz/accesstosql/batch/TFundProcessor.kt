package ru.headnezzz.accesstosql.batch

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.mapper.AccessToSqlMapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTFund
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTFund

@Component
class TFundProcessor(private val accessToSqlMapper: AccessToSqlMapper): ItemProcessor<AccessTFund, SqlTFund> {

    override fun process(item: AccessTFund): SqlTFund = accessToSqlMapper.mapTFund(item)
}
