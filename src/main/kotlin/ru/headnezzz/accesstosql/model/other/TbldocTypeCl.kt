package ru.headnezzz.accesstosql.model.other

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tblDOC_TYPE_CL")
class TbldocTypeCl {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null
}
