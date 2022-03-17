package io.amin.springbootkotlin.datasource

import io.amin.springbootkotlin.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(newBank: Bank): Bank
}