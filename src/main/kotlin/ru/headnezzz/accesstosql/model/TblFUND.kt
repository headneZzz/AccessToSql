package ru.headnezzz.accesstosql.model

import lombok.ToString
import java.util.UUID
import java.time.Instant
import ru.headnezzz.accesstosql.model.other.TblARCHIVE
import ru.headnezzz.accesstosql.model.other.TbldocTypeCl
import ru.headnezzz.accesstosql.model.other.TblPERIOD
import ru.headnezzz.accesstosql.model.other.TblSECURLEVEL
import ru.headnezzz.accesstosql.model.other.TblsecurityReason
import javax.persistence.*

@Entity
@ToString
@Table(name = "tblFUND")
class TblFUND {
    @Id
    @Column(name = "ID", nullable = false)
    var id: UUID? = null

    @Column(name = "OwnerID", nullable = false)
    var ownerID: UUID? = null

    @Column(name = "CreationDateTime", nullable = false)
    var creationDateTime: Instant? = null

    @Column(name = "StatusID")
    var statusID: UUID? = null

    @Column(name = "Deleted", nullable = false)
    var deleted: Int? = null

    @Column(name = "ISN_FUND", nullable = false)
    var isnFund: Long? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "ISN_ARCHIVE", nullable = false)
    var isnArchive: TblARCHIVE? = null

    @Column(name = "FUND_NUM_1", length = 1)
    var fundNum1: String? = null

    @Column(name = "FUND_NUM_2", nullable = false, length = 5)
    var fundNum2: String? = null

    @Column(name = "FUND_NUM_3", length = 1)
    var fundNum3: String? = null

    @ManyToOne
    @JoinColumn(name = "ISN_DOC_TYPE")
    var isnDocType: TbldocTypeCl? = null

    @Column(name = "FUND_CATEGORY", length = 1)
    var fundCategory: String? = null

    @ManyToOne
    @JoinColumn(name = "ISN_PERIOD")
    var isnPeriod: TblPERIOD? = null

    @Column(name = "FUND_KIND", length = 1)
    var fundKind: String? = null

    @Lob
    @Column(name = "FUND_NAME_SHORT")
    var fundNameShort: String? = null

    @Lob
    @Column(name = "FUND_NAME_FULL")
    var fundNameFull: String? = null

    @Column(name = "INVENTORY_COUNT")
    var inventoryCount: Int? = null

    @Column(name = "AUTO_INVENTORY_COUNT")
    var autoInventoryCount: Int? = null

    @Column(name = "DOC_START_YEAR")
    var docStartYear: Int? = null

    @Column(name = "DOC_START_YEAR_INEXACT", length = 1)
    var docStartYearInexact: String? = null

    @Column(name = "DOC_END_YEAR")
    var docEndYear: Int? = null

    @Column(name = "DOC_END_YEAR_INEXACT", length = 1)
    var docEndYearInexact: String? = null

    @Column(name = "DOC_RECEIPT_YEAR")
    var docReceiptYear: Int? = null

    @Column(name = "LAST_CHECKED_YEAR")
    var lastCheckedYear: Int? = null

    @Column(name = "LAST_DOC_CHECK_YEAR")
    var lastDocCheckYear: Int? = null

    @Column(name = "IS_IN_SEARCH", length = 1)
    var isInSearch: String? = null

    @Column(name = "IS_LOST", length = 1)
    var isLost: String? = null

    @Lob
    @Column(name = "ANNOTATE")
    var annotate: String? = null

    @Column(name = "PROPERTY", length = 1)
    var property: String? = null

    @Column(name = "PRESENCE_FLAG", length = 1)
    var presenceFlag: String? = null

    @Column(name = "ABSENCE_REASON", length = 1)
    var absenceReason: String? = null

    @Lob
    @Column(name = "MOVEMENT_NOTE")
    var movementNote: String? = null

    @Column(name = "HAS_MUSEUM_ITEMS", length = 1)
    var hasMuseumItems: String? = null

    @Column(name = "TREASURE_UNITS_COUNT")
    var treasureUnitsCount: Int? = null

    @Column(name = "HAS_UNDOCUMENTED_PERIODS", length = 1)
    var hasUndocumentedPeriods: String? = null

    @Column(name = "HAS_INCLUSIONS", length = 1)
    var hasInclusions: String? = null

    @Column(name = "WAS_RENAMED", length = 1)
    var wasRenamed: String? = null

    @Column(name = "WEIGHT")
    var weight: Int? = null

    @Column(name = "KEEP_PERIOD", length = 1)
    var keepPeriod: String? = null

    @ManyToOne
    @JoinColumn(name = "ISN_SECURLEVEL")
    var isnSecurlevel: TblSECURLEVEL? = null

    @Column(name = "SECURITY_CHAR", length = 1)
    var securityChar: String? = null

    @Column(name = "SECURITY_REASON", length = 1)
    var securityReason: String? = null

    @ManyToOne
    @JoinColumn(name = "ISN_OAF")
    var isnOaf: TblFUND? = null

    @Lob
    @Column(name = "OAF_NOTE")
    var oafNote: String? = null

    @Column(name = "CARD_COUNT")
    var cardCount: Int? = null

    @Column(name = "ARCHIVE_DB_COUNT")
    var archiveDbCount: Int? = null

    @Column(name = "FUND_DB_COUNT")
    var fundDbCount: Int? = null

    @Column(name = "INNER_DB_COUNT")
    var innerDbCount: Int? = null

    @Column(name = "LIST_COUNT")
    var listCount: Int? = null

    @Column(name = "PERSONAL_UNDESCRIBED_DOC_COUNT")
    var personalUndescribedDocCount: Int? = null

    @Column(name = "HAS_ELECTRONIC_DOCS", length = 1)
    var hasElectronicDocs: String? = null

    @Column(name = "HAS_TRADITIONAL_DOCS", length = 1)
    var hasTraditionalDocs: String? = null

    @Column(name = "CARRIER_TYPE", length = 1)
    var carrierType: String? = null

    @Column(name = "UNDESCRIBED_DOC_COUNT")
    var undescribedDocCount: Int? = null

    @Column(name = "UNDECSRIBED_PAGE_COUNT")
    var undecsribedPageCount: Int? = null

    @Lob
    @Column(name = "JOIN_REASON")
    var joinReason: String? = null

    @Lob
    @Column(name = "ADDITIONAL_NSA")
    var additionalNsa: String? = null

    @Lob
    @Column(name = "KEYWORDS")
    var keywords: String? = null

    @Lob
    @Column(name = "FUND_HISTORY")
    var fundHistory: String? = null

    @Lob
    @Column(name = "NOTE")
    var note: String? = null

    @Column(name = "INVENTORY_STATE", length = 1)
    var inventoryState: String? = null

    @ManyToOne
    @JoinColumn(name = "ISN_SECURITY_REASON")
    var isnSecurityReason: TblsecurityReason? = null

    @Column(name = "ForbidRecalc", length = 1)
    var forbidRecalc: String? = null
}
