package io.amin.springbootkotlin.service

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = bankDataSource.retrieveBanks()

}