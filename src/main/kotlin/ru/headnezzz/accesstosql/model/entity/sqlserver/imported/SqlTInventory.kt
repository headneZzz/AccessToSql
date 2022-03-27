package ru.headnezzz.accesstosql.model.entity.sqlserver.imported

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Т_Описи")
class SqlTInventory {

    @Id
    @Column(name = "Код_Описи")
    var inventoryCode: Int? = null

    @Column(name = "Номер_Описи", length = 20)
    var inventoryNumber: String? = null

    @Column(name = "Название_описи", length = 255)
    var inventoryName: String? = null

    @Column(name = "Год_начала")
    var startYear: Short? = null

    @Column(name = "Год_конца")
    var endYear: Short? = null

    @Column(name = "Код_фонда")
    var fundCode: Int? = null

    @Column(name = "Кол_дел")
    var unitsCount: Short? = null

    @Column(name = "Аннотация")
    var annotation: String? = null

    @Column(name = "Кол_экземп")
    var copiesCount: Int? = null

    @Column(name = "Полка", length = 50)
    var shelf: String? = null

    @Column(name = "Кол_связок", length = 50)
    var bundlesCount: String? = null

    @Column(name = "Кол_коробок", length = 50)
    var boxesCount: String? = null

    @Column(name = "Фото")
    var photo: String? = null

    override fun toString(): String {
        return "AccessTInventory(inventoryCode=$inventoryCode, inventoryNumber=$inventoryNumber, inventoryName=$inventoryName, startYear=$startYear, endYear=$endYear, fundCode=$fundCode, unitsCount=$unitsCount, annotation=$annotation, copiesCount=$copiesCount, shelf=$shelf, bundlesCount=$bundlesCount, boxesCount=$boxesCount, photo=$photo)"
    }
}
