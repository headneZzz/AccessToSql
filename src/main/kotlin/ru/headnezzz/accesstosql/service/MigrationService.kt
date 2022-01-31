package ru.headnezzz.accesstosql.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.headnezzz.accesstosql.model.FileRow
import ru.headnezzz.accesstosql.model.entity.sqlserver.TblinventoryStructure
import ru.headnezzz.accesstosql.repository.sqlserver.InventoryStructureRepository
import ru.headnezzz.accesstosql.repository.sqlserver.UnitRepository

@Service
class MigrationService(
    val unitRepository: UnitRepository,
    val inventoryStructureRepository: InventoryStructureRepository
) {

    private val log: Logger = LoggerFactory.getLogger(MigrationService::class.java)

    fun complexToArchiveFund(fileRows: List<FileRow>) {
        var totalSize = 0
        val regex = "(\\d*)(\\D*)".toRegex()
        for (fileRow in fileRows) {
            val units = unitRepository.main(fileRow.fund.trim(), fileRow.inventory.trim())
            log.info("Фонд {} опись {} найдено {}", fileRow.fund, fileRow.inventory, units.size)
            totalSize += units.size
            for (unit in units) {
                unit.isnUnit = unitRepository.getMaxIsnUnit() + 1
                val matchResult = regex.find(unit.unitNum1)
                unit.unitNum1 = matchResult!!.groups[1]!!.value
                unit.unitNum2 = matchResult.groups[2]?.value
                if (inventoryStructureRepository.findByIsnInventoryCls(unit.isnInventoryCls) == null) {
                    inventoryStructureRepository.save(TblinventoryStructure(unit.isnInventoryCls))
                }
                unitRepository.save(unit)
            }
        }
        log.info("{}", totalSize)
    }
}
