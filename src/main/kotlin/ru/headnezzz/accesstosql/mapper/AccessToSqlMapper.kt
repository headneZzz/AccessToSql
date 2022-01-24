package ru.headnezzz.accesstosql.mapper

import org.mapstruct.Mapper
import ru.headnezzz.accesstosql.model.entity.access.AccessTDelo
import ru.headnezzz.accesstosql.model.entity.sqlserver.SqlTDelo

@Mapper(componentModel = "spring")
interface AccessToSqlMapper {
    fun accessToSql(accessTDelo: AccessTDelo): SqlTDelo
}
