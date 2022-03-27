package ru.headnezzz.accesstosql.repository.access

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.access.AccessTUnit

interface AccessTUnitRepository: JpaRepository<AccessTUnit, Int>
