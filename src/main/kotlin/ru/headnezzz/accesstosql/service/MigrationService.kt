package ru.headnezzz.accesstosql.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.headnezzz.accesstosql.model.FileRow
import ru.headnezzz.accesstosql.model.entity.sqlserver.TblInventoryStructure
import ru.headnezzz.accesstosql.repository.sqlserver.TblInventoryStructureRepository
import ru.headnezzz.accesstosql.repository.sqlserver.TblUnitRepository

@Service
class MigrationService(
    val tblUnitRepository: TblUnitRepository,
    val tblInventoryStructureRepository: TblInventoryStructureRepository
) {

    private val log: Logger = LoggerFactory.getLogger(MigrationService::class.java)

    fun complexToArchiveFund(fileRows: List<FileRow>) {
        var totalSize = 0
        // Разделяет цифры в начале и буквы в конце на 2 группы
        val regex = "(\\d*)(\\D*)".toRegex()
        for (fileRow in fileRows) {
            val units = tblUnitRepository.main(fileRow.fund.trim(), fileRow.inventory.trim())
            log.info("Фонд {} опись {} найдено {}", fileRow.fund, fileRow.inventory, units.size)
            totalSize += units.size
            for (unit in units) {
                unit.isnUnit = tblUnitRepository.getMaxIsnUnit() + 1
                val matchResult = regex.find(unit.unitNum1)
                unit.unitNum1 = matchResult!!.groups[1]!!.value
                unit.unitNum2 = matchResult.groups[2]?.value
                if (tblInventoryStructureRepository.findByIsnInventoryCls(unit.isnInventoryCls) == null) {
                    tblInventoryStructureRepository.save(TblInventoryStructure(unit.isnInventoryCls))
                }
                tblUnitRepository.save(unit)
            }
        }
        log.info("Всего импортировано: {}", totalSize)
    }
}
