package ru.headnezzz.accesstosql.service

import com.healthmarketscience.jackcess.CursorBuilder
import com.healthmarketscience.jackcess.DatabaseBuilder
import com.healthmarketscience.jackcess.Row
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Service
import ru.headnezzz.accesstosql.model.entity.sqlserver.SqlTDelo
import ru.headnezzz.accesstosql.repository.sqlserver.TDeloRepository
import java.io.File
import java.util.ArrayList
import javax.sql.DataSource

@Service
@Deprecated("Работает медленно, переписал на spring batch")
class ImportService(
    val dataSource: DataSource,
    val tDeloRepository: TDeloRepository
) {

    private val log: Logger = LoggerFactory.getLogger(ImportService::class.java)

    /**
     * Переносит данные из access в ms sql для  в одном потоке, используя 1 поток и jackcess.
     */
    fun tDeloToSql() {
        executeSqlScript("sql/create_t_delo.sql")
        executeSqlScript("sql/identity_on_t_delo.sql")

        val tDelo = DatabaseBuilder.open(File("D:\\Users\\headneZzz\\Downloads\\af\\Б_Ф_Общ.mdb")).getTable("Т_Дело")
        val rowCount = tDelo.rowCount
        log.info("Записей в Т_Дело: {}", rowCount)
        var counter = 0
        var tDela = ArrayList<SqlTDelo>()
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
        println("Finished")
    }

    private fun toTDelo(row: Row): SqlTDelo {
        val sqlTDelo = SqlTDelo()
        sqlTDelo.kodDela = row.getInt("Код_Дела")
        sqlTDelo.nomerDela = row.getString("Номер_Дела")
        sqlTDelo.kodOpisi = row.getInt("Код_Описи")
        sqlTDelo.kodFonda = row.getInt("Код_фонда")
        sqlTDelo.naimenovanieDela = row.getString("Наименование_дела")
        sqlTDelo.kolvoListov = row.getShort("Кол-во_листов")
        sqlTDelo.fizichSost = row.getString("Физич_состояние")
        sqlTDelo.otmetka = row.getString("Отметка")
        sqlTDelo.soderzh = row.getString("Содержание")
        sqlTDelo.startDate = row.getShort("Год_начала")
        sqlTDelo.endDate = row.getShort("Год_конца")
        sqlTDelo.isZagolovok = row.getBoolean("Заголовок_Изменен")
        sqlTDelo.photo = row.getString("Фото")
        sqlTDelo.executor = row.getInt("Исполнитель")
        sqlTDelo.date = row.getLocalDateTime("Дата")
        sqlTDelo.exactDate = row.getString("Точная_дата")
        return sqlTDelo
    }

    private fun executeSqlScript(path: String) {
        val rdp = ResourceDatabasePopulator()
        rdp.addScript(ClassPathResource(path))
        rdp.execute(dataSource)
    }
}
