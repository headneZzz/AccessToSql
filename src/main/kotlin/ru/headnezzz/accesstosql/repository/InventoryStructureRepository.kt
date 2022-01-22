package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.other.TblinventoryStructure
import java.util.*

interface InventoryStructureRepository: JpaRepository<TblinventoryStructure, UUID> {
    fun findByIsnInventoryCls(isnInventoryCls: Long): TblinventoryStructure?
}
