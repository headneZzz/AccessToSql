package ru.headnezzz.accesstosql.model.entity.sqlserver

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Т_Дело")
class SqlTDelo {
    @Id
    @Column(name = "Код_Дела")
    var kodDela: Int? = null

    @Column(name = "Номер_Дела", length = 20)
    var nomerDela: String? = null

    @Column(name = "Код_Описи")
    var kodOpisi: Int? = null

    @Column(name = "Код_фонда")
    var kodFonda: Int? = null

    @Column(name = "Наименование_дела")
    var naimenovanieDela: String? = null

    @Column(name = "[Кол-во_листов]")
    var kolvoListov: Short? = null

    @Column(name = "Физич_состояние", length = 50)
    var fizichSost: String? = null

    @Column(name = "Отметка", length = 50)
    var otmetka: String? = null

    @Column(name = "Содержание", length = 50)
    var soderzh: String? = null

    @Column(name = "Год_начала")
    var startDate: Short? = null

    @Column(name = "Год_конца")
    var endDate: Short? = null

    @Column(name = "Заголовок_Изменен")
    var isZagolovok: Boolean? = null

    @Column(name = "Фото")
    var photo: String? = null

    @Column(name = "Исполнитель")
    var executor: Int? = null

    @Column(name = "Дата")
    var date: LocalDateTime? = null

    @Column(name = "Точная_дата", length = 50)
    var exactDate: String? = null

    override fun toString(): String {
        return "SqlTDelo(kodDela=$kodDela, nomerDela=$nomerDela, kodOpisi=$kodOpisi, kodFonda=$kodFonda, naimenovanieDela=$naimenovanieDela, kolvoListov=$kolvoListov, fizichSost=$fizichSost, otmetka=$otmetka, soderzh=$soderzh, startDate=$startDate, endDate=$endDate, isZagolovok=$isZagolovok, photo=$photo, executor=$executor, date=$date, exactDate=$exactDate)"
    }
}
