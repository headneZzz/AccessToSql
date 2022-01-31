package ru.headnezzz.accesstosql.service

import com.healthmarketscience.jackcess.Cursor
import com.healthmarketscience.jackcess.CursorBuilder
import com.healthmarketscience.jackcess.DatabaseBuilder
import com.healthmarketscience.jackcess.Table
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.headnezzz.accesstosql.model.MappingFund
import ru.headnezzz.accesstosql.model.MappingInventory
import ru.headnezzz.accesstosql.model.entity.sqlserver.TblINVENTORY
import ru.headnezzz.accesstosql.repository.sqlserver.InventoryRepository
import java.io.File

@Service
@Deprecated("Работает некорректно и медленно")
class JackcessService(
    val inventoryRepository: InventoryRepository
) {

    private val log: Logger = LoggerFactory.getLogger(JackcessService::class.java)

    /**
     * Сразу мапим данные из access и ms sql без переноса, используя jackcess.
     */
    fun getMappingTDelo(docsFromFile: Map<String, String>) {
        val mappingInventories = getMappingInventories()
        val mappingTDelo = HashMap<MappingInventory, Int>()
        val tDelo: Table =
            DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Б_Ф_Общ.mdb")).getTable("Т_Дело")

        for (row in tDelo) {
            val it = mappingInventories.find { it.inventoryCode == row.getInt("Код_Описи") }
            if (it != null && docsFromFile[it.unitedFundNum] == it.inventoryNomer) {
                mappingTDelo[it] = row.getInt("Код_Описи")
            }
        }


        println(mappingTDelo.size)
    }

    private fun getMappingInventories(): List<MappingInventory> {
        val mappingFunds = getMappingFunds()
        val mappingInventories: MutableList<MappingInventory> = ArrayList()
        val inventories = inventoryRepository.findAll()
        val tOpisi: Table =
            DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Комплекс_Каталог.mdb")).getTable("Т_Описи")
        for (row in tOpisi) {

            var mappingFund: MappingFund? = null
            var it: TblINVENTORY? = null
            val its = inventories.filter { inventory -> inventory.inventoryNum1!! == row.getString("Номер_Описи") }
            for (i in its) {
                mappingFund = mappingFunds.find {
                    it.fundCode == row.getInt("Код_фонда")
                            && it.isnFund.toLong() == i?.isnFund
                }
                if (mappingFund != null) {
                    it = i
                    break
                }
            }

            if (mappingFund != null && it != null) {
                mappingInventories.add(
                    MappingInventory(
                        it.isnInventory!!,
                        mappingFund.unitedFundNum,
                        it.inventoryNum1!!,
                        row.getString("Номер_Описи"),
                        row.getInt("Код_Описи")
                    )
                )
            }

        }
        log.info("Соответствие описей: {}", mappingInventories.size)
        return mappingInventories
    }

    private fun getMappingFunds(): List<MappingFund> {
        val mappingFunds: MutableList<MappingFund> = ArrayList()
        val tDelo: Table =
            DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Комплекс_Каталог.mdb")).getTable("Т_Фонд")
        val cursor: Cursor = CursorBuilder.createCursor(tDelo)

        log.info("Соответствие фондов: {}", mappingFunds.size)
        return mappingFunds
    }

}
