package ru.headnezzz.accesstosql.model.entity.sqlserver.other

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tblARCHIVE")
class TblARCHIVE {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null
}
