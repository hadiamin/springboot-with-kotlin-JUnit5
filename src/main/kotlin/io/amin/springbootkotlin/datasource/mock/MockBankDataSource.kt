package io.amin.springbootkotlin.datasource.mock

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    override fun retrieveBanks(): Collection<Bank> {
        return listOf(Bank("", 0.0, 1))
    }
}