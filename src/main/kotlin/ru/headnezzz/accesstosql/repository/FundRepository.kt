package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.headnezzz.accesstosql.model.TblFUND
import java.util.*

interface FundRepository : JpaRepository<TblFUND, UUID> {

}
