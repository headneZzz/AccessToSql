package ru.headnezzz.accesstosql.model.entity.sqlserver.imported

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Т_Дело")
class SqlTUnit {

    @Id
    @Column(name = "Код_Дела")
    var unitCode: Int? = null

    @Column(name = "Номер_Дела", length = 20)
    var unitNumber: String? = null

    @Column(name = "Код_Описи")
    var inventoryCode: Int? = null

    @Column(name = "Код_фонда")
    var fundCode: Int? = null

    @Column(name = "Наименование_дела")
    var unitName: String? = null

    @Column(name = "[Кол-во_листов]")
    var pagesCount: Short? = null

    @Column(name = "Физич_состояние", length = 50)
    var physicalState: String? = null

    @Column(name = "Отметка", length = 50)
    var mark: String? = null

    @Column(name = "Содержание", length = 50)
    var content: String? = null

    @Column(name = "Год_начала")
    var startYear: Short? = null

    @Column(name = "Год_конца")
    var endYear: Short? = null

    @Column(name = "Заголовок_Изменен")
    var isTitleChanged: Boolean? = null

    @Column(name = "Фото")
    var photo: String? = null

    @Column(name = "Исполнитель")
    var executor: Int? = null

    @Column(name = "Дата")
    var date: LocalDateTime? = null

    @Column(name = "Точная_дата", length = 50)
    var exactDate: String? = null

    override fun toString(): String {
        return "AccessTUnit(unitCode=$unitCode, unitNumber=$unitNumber, inventoryCode=$inventoryCode, fundCode=$fundCode, unitName=$unitName, pagesCount=$pagesCount, physicalState=$physicalState, mark=$mark, content=$content, startYear=$startYear, endYear=$endYear, isTitleChanged=$isTitleChanged, photo=$photo, executor=$executor, date=$date, exactDate=$exactDate)"
    }
}
