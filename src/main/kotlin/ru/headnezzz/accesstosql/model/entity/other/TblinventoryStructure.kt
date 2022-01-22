package ru.headnezzz.accesstosql.model.entity.other

import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tblINVENTORY_STRUCTURE")
class TblinventoryStructure() {
    constructor(
        ISN_INVENTORY: Long
    ) : this() {
        this.id = UUID(0, ISN_INVENTORY)
        this.isnInventoryCls = ISN_INVENTORY
        this.ISN_INVENTORY = ISN_INVENTORY
    }

    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID = UUID.randomUUID()

    @Column(name = "OwnerID", nullable = false)
    var ownerID: UUID = UUID.fromString("12345678-9012-3456-7890-123456789012")

    @Column(name = "CreationDateTime")
    var creationDateTime: Instant = Instant.now()

    @Column(name = "DocID")
    var docId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")

    @Column(name = "RowID")
    var rowId: Int = 0

    @Column(name = "StatusID")
    var statusId: UUID = UUID.fromString("A4366C7E-ACBA-4A71-B59A-15D51107DBFE")

    @Column(name = "Deleted")
    var deleted = false

    @Column(name = "ISN_INVENTORY_CLS")
    var isnInventoryCls: Long = 0

    @Column(name = "ISN_HIGH_INVENTORY_CLS")
    var ISN_HIGH_INVENTORY_CLS: Long? = null

    @Column(name = "ISN_ARCHIVE")
    var ISN_ARCHIVE: Long? = null

    @Column(name = "ISN_FUND")
    var ISN_FUND: Long? = null

    @Column(name = "ISN_INVENTORY")
    var ISN_INVENTORY: Long = 0

    @Column(name = "CODE")
    var CODE: String? = null

    @Column(name = "NAME")
    var NAME: String = "..."

    @Column(name = "NOTE")
    var NOTE: String? = null

    @Column(name = "FOREST_ELEM")
    var FOREST_ELEM: String = "T"

    @Column(name = "PROTECTED")
    var PROTECTED: String = "N"

    @Column(name = "WEIGHT")
    var weight: Int = 0

}
