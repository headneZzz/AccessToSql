package ru.headnezzz.accesstosql.repository.sqlserver

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.sqlserver.TblINVENTORY
import java.util.*

interface InventoryRepository:JpaRepository<TblINVENTORY, UUID> {
}
