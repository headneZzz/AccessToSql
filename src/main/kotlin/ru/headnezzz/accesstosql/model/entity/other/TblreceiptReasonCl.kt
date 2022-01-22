package ru.headnezzz.accesstosql.model.entity.other

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tblRECEIPT_REASON_CL")
class TblreceiptReasonCl {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null
}
