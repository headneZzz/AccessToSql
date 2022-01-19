package ru.headnezzz.accesstosql.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tblINVENTORY")
class TblINVENTORY {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null

    @Column(name = "ISN_INVENTORY", nullable = false)
    var isnInventory: Long? = null

    @Column(name = "ISN_FUND")
    var isnFund: Long? = null

    @Column(name = "INVENTORY_NUM_1", nullable = false, length = 3)
    var inventoryNum1: String? = null
}
