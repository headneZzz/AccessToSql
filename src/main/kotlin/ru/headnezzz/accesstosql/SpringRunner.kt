package ru.headnezzz.accesstosql

import com.healthmarketscience.jackcess.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Component
import ru.headnezzz.accesstosql.model.FileRow
import ru.headnezzz.accesstosql.model.MappingFund
import ru.headnezzz.accesstosql.model.MappingInventory
import ru.headnezzz.accesstosql.model.entity.TblINVENTORY
import ru.headnezzz.accesstosql.model.entity.access.TDelo
import ru.headnezzz.accesstosql.model.entity.other.TblinventoryStructure
import ru.headnezzz.accesstosql.repository.*
import java.io.File
import java.time.Instant
import java.util.*
import javax.sql.DataSource


@Component
class SpringRunner(
    val dataSource: DataSource,
    val jdbcTemplate: JdbcTemplate,
    val fundRepository: FundRepository,
    val unitedFundDao: UnitedFundDao,
    val inventoryRepository: InventoryRepository,
    val tDeloRepository: TDeloRepository,
    val unitRepository: UnitRepository,
    val inventoryStructureRepository: InventoryStructureRepository
) : CommandLineRunner {

    private val log: Logger = LoggerFactory.getLogger(SpringRunner::class.java)

    override fun run(vararg args: String?) {
        val docsFromFile = getDocsFromFile()
        complexToArchiveFund(docsFromFile)
    }

    private fun complexToArchiveFund(fileRows: List<FileRow>) {
        var totalSize = 0
        for (fileRow in fileRows) {
            val units = unitRepository.main(fileRow.fund.trim(), fileRow.inventory.trim())
            log.info("Фонд {} опись {} найдено {}", fileRow.fund, fileRow.inventory, units.size)
            totalSize += units.size
            for (unit in units) {
                unit.isnUnit = unitRepository.getMaxIsnUnit() + 1
                unit.id = UUID.randomUUID()
                unit.ownerID = UUID.randomUUID()
                unit.statusID = UUID.randomUUID()
                unit.creationDateTime = Instant.now()
                if (inventoryStructureRepository.findByIsnInventoryCls(unit.isnInventoryCls) == null) {
                    inventoryStructureRepository.save(TblinventoryStructure(unit.isnInventoryCls))
                }
                unitRepository.save(unit)
            }
        }
        log.info("{}", totalSize)
    }

    private fun exportTDeloToSql() {
//        executeSqlScript("sql/create_t_delo.sql")
        executeSqlScript("sql/identity_on_t_delo.sql")

        val tDelo = DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Б_Ф_Общ.mdb")).getTable("Т_Дело")
        val rowCount = tDelo.rowCount
        log.info("Записей в Т_Дело: {}", rowCount)
        var counter = 0
        var tDela = ArrayList<TDelo>()
        val cursor = CursorBuilder.createCursor(tDelo)
        cursor.findFirstRow(mapOf("Код_Дела" to 704847))
        while (cursor.moveToNextRow()) {
            val row = cursor.currentRow
            tDela.add(toTDelo(row))
            if (tDela.size == 1000) {
                try {
                    tDeloRepository.saveAll(tDela)
                } catch (e: Exception) {
                    executeSqlScript("sql/identity_on_t_delo.sql")
                    log.warn("Обновил IDENTITY_INSERT ON")
                    tDeloRepository.saveAll(tDela)
                }
                counter += tDela.size
                log.info("Экспортировано: {}", counter)
                tDela = ArrayList()
            }
        }
        tDeloRepository.saveAll(tDela)
        println("Finished all threads")
    }

    private fun toTDelo(row: Row): TDelo {
        var tDelo = TDelo()
        tDelo.kodDela = row.getInt("Код_Дела")
        tDelo.nomerDela = row.getString("Номер_Дела")
        tDelo.kodOpisi = row.getInt("Код_Описи")
        tDelo.kodFonda = row.getInt("Код_фонда")
        tDelo.naimenovanieDela = row.getString("Наименование_дела")
        tDelo.kolvoListov = row.getShort("Кол-во_листов")
        tDelo.fizichSost = row.getString("Физич_состояние")
        tDelo.otmetka = row.getString("Отметка")
        tDelo.soderzh = row.getString("Содержание")
        tDelo.startDate = row.getShort("Год_начала")
        tDelo.endDate = row.getShort("Год_конца")
        tDelo.isZagolovok = row.getBoolean("Заголовок_Изменен")
        tDelo.photo = row.getString("Фото")
        tDelo.executor = row.getInt("Исполнитель")
        tDelo.date = row.getLocalDateTime("Дата")
        tDelo.exactDate = row.getString("Точная_дата")
        return tDelo
    }

    private fun getDocsFromFile(): List<FileRow> {
        val result = ArrayList<FileRow>()
        File("D:\\Users\\headneZzz\\Downloads\\af\\test.txt").forEachLine {
            val str = it.replace(
                "\\s".toRegex(),
                " "
            ).split(" ")
            result.add(FileRow(str[0], str[1]))
        }
        log.info("Считано фондов из файла: {}", result.size)
        return result
    }

    private fun getMappingTDelo(docsFromFile: Map<String, String>) {
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
                mappingFund = mappingFunds.find { mappingFund ->
                    mappingFund.fundCode == row.getInt("Код_фонда")
                            && mappingFund.isnFund.toLong() == i?.isnFund
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
        unitedFundDao.unitedFund().forEach {
            if (cursor.findFirstRow(mapOf("Номер_фонда" to it.unitedFundNum))) {
                mappingFunds.add(
                    MappingFund(
                        it.isnFund!!,
                        it.unitedFundNum!!,
                        cursor.currentRow.getString("Номер_фонда"),
                        cursor.currentRow.getInt("Код_фонда")
                    )
                )
            }
        }
        log.info("Соответствие фондов: {}", mappingFunds.size)
        return mappingFunds
    }

    private fun createTArchives() {
        val tArchives: Table =
            DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Комплекс_Каталог.mdb")).getTable("Т_Архивы")
        executeSqlScript("sql/create_t_archives.sql")

        for (row in tArchives) {
            val code = row.get("Код_архива")
            val name = row.get("Название")
            val mesto = row.get("Место")
            jdbcTemplate.execute("insert into Т_Архивы (Код_архива, Название, Место) values ($code, '$name', '$mesto')")
        }
    }

    private fun executeSqlScript(path: String) {
        val rdp = ResourceDatabasePopulator()
        rdp.addScript(ClassPathResource(path))
        rdp.execute(dataSource)
    }
}
