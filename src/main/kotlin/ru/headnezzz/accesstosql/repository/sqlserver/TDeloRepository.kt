package ru.headnezzz.accesstosql.repository.sqlserver

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.sqlserver.SqlTDelo

interface TDeloRepository: JpaRepository<SqlTDelo, Int> {

}
