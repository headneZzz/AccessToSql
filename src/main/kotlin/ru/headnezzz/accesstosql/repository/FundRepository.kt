package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.TblFUND
import java.util.*

interface FundRepository : JpaRepository<TblFUND, UUID> {

}
