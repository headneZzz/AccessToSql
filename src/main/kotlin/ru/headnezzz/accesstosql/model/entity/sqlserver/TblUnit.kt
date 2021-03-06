package ru.headnezzz.accesstosql.model.entity.sqlserver

import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tblUNIT")
class TblUnit {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null

    @Column(name = "OwnerID", nullable = false)
    var ownerID: UUID? = null

    @Column(name = "CreationDateTime")
    var creationDateTime: Instant? = null

    @Column(name = "StatusID", nullable = false)
    var statusID: UUID? = null

    @Column(name = "Deleted", nullable = false)
    var deleted = false

    @Column(name = "ISN_UNIT", nullable = false)
    var isnUnit: Long? = 0

    @Column(name = "ISN_HIGH_UNIT")
    var isnHighUnit: Long? = null

    @Column(name = "ISN_INVENTORY")
    var isnInventory: Long? = null

    @Column(name = "ISN_DOC_TYPE")
    var isnDocType: Long? = null

    @Column(name = "ISN_LOCATION")
    var isnLocation: Long? = null

    @Column(name = "ISN_SECURLEVEL")
    var isnSecurlevel: Long? = null

    @Column(name = "SECURITY_CHAR", length = 1)
    var securityChar: String? = null

    @Column(name = "SECURITY_REASON", length = 1)
    var securityReason: String? = null

    @Column(name = "ISN_INVENTORY_CLS")
    var isnInventoryCls: Long = 0

    @Column(name = "ISN_STORAGE_MEDIUM")
    var isnStorageMedium: Long? = null

    @Column(name = "ISN_DOC_KIND")
    var isnDocKind: Long? = null

    @Column(name = "UNIT_KIND")
    var unitKind: Int? = null

    @Column(name = "UNIT_NUM_1", length = 8)
    var unitNum1: String = ""

    @Column(name = "UNIT_NUM_2", length = 2)
    var unitNum2: String? = null

    @Column(name = "VOL_NUM")
    var volNum: Int? = null

    @Column(name = "NAME")
    var name: String? = null

    @Column(name = "ANNOTATE")
    var annotate: String? = null

    @Column(name = "DELO_INDEX", length = 300)
    var deloIndex: String? = null

    @Column(name = "PRODUCTION_NUM", length = 20)
    var productionNum: String? = null

    @Column(name = "UNIT_CATEGORY", length = 1)
    var unitCategory: String? = null

    @Column(name = "NOTE")
    var note: String? = null

    @Column(name = "IS_IN_SEARCH", length = 1)
    var isInSearch: String? = null

    @Column(name = "IS_LOST", length = 1)
    var isLost: String? = null

    @Column(name = "HAS_SF", length = 1)
    var hasSf: String? = null

    @Column(name = "HAS_FP", length = 1)
    var hasFp: String? = null

    @Column(name = "HAS_DEFECTS", length = 1)
    var hasDefects: String? = null

    @Column(name = "ARCHIVE_CODE", length = 300)
    var archiveCode: String? = null

    @Column(name = "CATALOGUED", length = 1)
    var catalogued: String? = null

    @Column(name = "WEIGHT")
    var weight: Int? = null

    @Column(name = "UNIT_CNT")
    var unitCnt: Int? = null

    @Column(name = "START_YEAR")
    var startYear: Int? = null

    @Column(name = "START_YEAR_INEXACT", length = 1)
    var startYearInexact: String? = null

    @Column(name = "END_YEAR")
    var endYear: Int? = null

    @Column(name = "END_YEAR_INEXACT", length = 1)
    var endYearInexact: String? = null

    @Column(name = "MEDIUM_TYPE", length = 1)
    var mediumType: String? = null

    @Column(name = "BACKUP_COPY_CNT")
    var backupCopyCnt: Int? = null

    @Column(name = "HAS_TREASURES", length = 1)
    var hasTreasures: String? = null

    @Column(name = "IS_MUSEUM_ITEM", length = 1)
    var isMuseumItem: String? = null

    @Column(name = "PAGE_COUNT")
    var pageCount: Int? = null

    @Column(name = "CARDBOARDED", length = 1)
    var cardboarded: String? = null

    @Column(name = "ADDITIONAL_CLS")
    var additionalCls: String? = null

    @Column(name = "ALL_DATE", length = 300)
    var allDate: String? = null

    @Column(name = "ISN_SECURITY_REASON")
    var isnSecurityReason: Long? = null

    @Column(name = "UNIT_NUM_TXT", length = 25)
    var unitNumTxt: String? = null
}
