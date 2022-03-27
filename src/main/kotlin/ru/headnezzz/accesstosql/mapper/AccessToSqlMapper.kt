package ru.headnezzz.accesstosql.mapper

import org.mapstruct.Mapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTUnit
import ru.headnezzz.accesstosql.model.entity.access.AccessTFund
import ru.headnezzz.accesstosql.model.entity.access.AccessTInventory
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTUnit
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTFund
import ru.headnezzz.accesstosql.model.entity.sqlserver.imported.SqlTInventory

@Mapper
interface AccessToSqlMapper {

    fun mapTUnit(accessTUnit: AccessTUnit): SqlTUnit

    fun mapTInventory(accessTDelo: AccessTInventory): SqlTInventory

    fun mapTFund(accessTDelo: AccessTFund): SqlTFund
}
