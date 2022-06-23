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
class TxInternalTests(
    val callService : CallService
) {

    /**
     * internalCall을 호출할 경우 트랜잭션이 적용된다
     */
    @Test
    fun internalCall(){
        callService.internal()
    }

    /**
     * externalCall을 호출할 경우 프록시(callService)가 아닌 순수 callService의 internal을 호출하기 때문에
     *
     * 트랜잭션이 적용이 안된다
     */
    @Test
    fun externalCall(){
        callService.external()
    }

    @TestConfiguration
    internal class TxInternalConfig{
        @Bean
        fun callService() : CallService = CallService()
    }

    @Service
    class CallService{

        fun external(){
            log.info("call external")
            printTxInfo()

        }

        @Transactional
        fun internal(){
            log.info("call internal")
            printTxInfo()
        }

        private fun printTxInfo() {
            val txActive : Boolean = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active={}", txActive)
            log.info("tx name={}", TransactionSynchronizationManager.getCurrentTransactionName())
        }

    }

    companion object:Log

}