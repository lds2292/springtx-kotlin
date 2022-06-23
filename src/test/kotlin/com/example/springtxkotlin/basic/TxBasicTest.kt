package com.example.springtxkotlin.basic

import com.example.springtxkotlin.Log
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.aop.support.AopUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronizationManager

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TxBasicTest(
    val basicService : BasicService
) {

    /**
     * 빈으로 생성된 BasicService가 프록시 객체인지 확인한다
     */
    @Test
    fun proxyCheck(){
        log.info("app class = {}", basicService.javaClass)
        assertTrue(AopUtils.isAopProxy(basicService))
    }

    @Test
    fun txTest(){
        basicService.tx()
        basicService.nonTx()
    }

    @TestConfiguration
    internal class TransactionBasicConfig{
        @Bean
        fun basicService() : BasicService = BasicService()
    }


    @Service
    class BasicService{

        @Transactional
        fun tx(){
            log.info("call tx")
            val txActive: Boolean = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active = {}", txActive)
        }

        fun nonTx(){
            log.info("nonTx tx")
            val txActive: Boolean = TransactionSynchronizationManager.isActualTransactionActive()
            log.info("tx active = {}", txActive)
        }
    }

    companion object:Log

}