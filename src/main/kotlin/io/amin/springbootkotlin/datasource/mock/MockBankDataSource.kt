package io.amin.springbootkotlin.datasource.mock

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    private val banks = listOf<Bank>(
        Bank("1234", 2.13, 12),
        Bank("1234", 3.30, 23),
        Bank("1234", 1.23, 21),
        Bank("1234", 1.30, 32),
        Bank("1234", 2.32, 22),
    )

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBank(accountNumber: String): Bank {
        return banks.first { it.accountNumber == accountNumber }
    }
}