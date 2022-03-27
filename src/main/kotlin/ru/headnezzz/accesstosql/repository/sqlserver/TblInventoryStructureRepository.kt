package ru.headnezzz.accesstosql.repository.sqlserver

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.sqlserver.TblInventoryStructure
import java.util.*

interface TblInventoryStructureRepository: JpaRepository<TblInventoryStructure, UUID> {
    fun findByIsnInventoryCls(isnInventoryCls: Long): TblInventoryStructure?
}
