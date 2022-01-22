package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.TblINVENTORY
import java.util.*

interface InventoryRepository:JpaRepository<TblINVENTORY, UUID> {
}
