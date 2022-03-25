package io.amin.springbootkotlin.service

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val bankDataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = bankDataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = bankDataSource.retrieveBank(accountNumber)

    fun addBank(newBank: Bank): Bank = bankDataSource.createBank(newBank)

    fun updateBank(existingBank: Bank): Bank = bankDataSource.patchBank(existingBank)

    fun deleteBank(accountNumber: String): Unit = bankDataSource.deleteBank(accountNumber)

}