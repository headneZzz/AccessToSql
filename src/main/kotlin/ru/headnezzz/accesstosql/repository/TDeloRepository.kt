package ru.headnezzz.accesstosql.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.headnezzz.accesstosql.model.entity.access.TDelo

interface TDeloRepository: JpaRepository<TDelo, Int> {

}
