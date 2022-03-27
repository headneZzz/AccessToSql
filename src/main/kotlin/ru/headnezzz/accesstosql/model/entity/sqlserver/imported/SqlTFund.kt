package ru.headnezzz.accesstosql.model.entity.sqlserver.imported

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Т_Фонд")
class SqlTFund {

    @Id
    @Column(name = "Код_фонда")
    var fundCode: Int? = null

    @Column(name = "Номер_фонда", length = 20)
    var fundNumber: String? = null

    @Column(name = "Имя_Т_Дела", length = 20)
    var tUnitName: String? = null

    @Column(name = "Наименование_фонда")
    var fundName: String? = null

    @Column(name = "Число_дел")
    var unitsCount: Short? = null

    @Column(name = "Год_начала")
    var startYear: Short? = null

    @Column(name = "Год_конца")
    var endYear: Short? = null

    @Column(name = "Аннотация")
    var annotation: String? = null

    @Column(name = "ДСП")
    var dsp: Boolean? = null

    @Column(name = "Код_Архива")
    var archiveCode: Int? = null

    override fun toString(): String {
        return "SqlTFund(fundCode=$fundCode, fundNumber=$fundNumber, tUnitName=$tUnitName, fundName=$fundName, unitsCount=$unitsCount, startYear=$startYear, endYear=$endYear, annotation=$annotation, dsp=$dsp, archiveCode=$archiveCode)"
    }
}
