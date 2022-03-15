package io.amin.springbootkotlin.datasource.mock

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    private val banks = listOf<Bank>(
        Bank("1234", 0.3, 2),
        Bank("1234", 0.3, 2),
        Bank("1234", 0.3, 2),
        Bank("1234", 0.3, 2),
        Bank("1234", 0.3, 2),
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }
}