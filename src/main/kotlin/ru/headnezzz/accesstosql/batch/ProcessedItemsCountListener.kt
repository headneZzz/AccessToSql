package ru.headnezzz.accesstosql.batch

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.stereotype.Service


@Service
class ProcessedItemsCountListener : ChunkListener {
    private val log: Logger = LoggerFactory.getLogger(ProcessedItemsCountListener::class.java)

    private var startTime: Long = 0

    override fun beforeChunk(context: ChunkContext) {
        startTime = System.currentTimeMillis()
    }

    override fun afterChunk(context: ChunkContext) {
        log.info(
            "{}[{}]: Processed {} items, takes {} ms",
            context.stepContext.jobName,
            context.stepContext.stepName,
            context.stepContext.stepExecution.writeCount,
            System.currentTimeMillis() - startTime
        )
    }

    override fun afterChunkError(context: ChunkContext) {
        // Do nothing afterChunkError
    }
}
