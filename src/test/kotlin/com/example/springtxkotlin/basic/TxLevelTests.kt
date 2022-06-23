package com.example.springtxkotlin.basic

import com.example.springtxkotlin.Log
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TxLevelTests(
    val levelService : LevelService
) {

    @Test
    fun orderTest() {
        levelService.write()
        levelService.read()
    }

    @TestConfiguration
    internal class TxLevelTestConfig {
        @Bean
        fun levelService(): LevelService {
            return LevelService()
        }
    }

    @Service
    @Transactional(readOnly = true)
    class LevelService{

        @Transactional
        fun write() {
            log.info("call write")
            printTxInfo()
        }


        fun read() {
            log.info("call read")
            printTxInfo()
        }

        private fun printTxInfo() {
            val txActive = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active={}", txActive)
            val isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            log.info("tx readOnly={}", isReadOnly)
            log.info("tx name = {}", TransactionSynchronizationManager.getCurrentTransactionName())
        }
    }

    companion object: Log

}