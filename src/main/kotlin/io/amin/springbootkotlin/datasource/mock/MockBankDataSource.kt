package io.amin.springbootkotlin.datasource.mock

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    private val banks = mutableListOf<Bank>(
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
        return banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
    }

    override fun createBank(newBank: Bank): Bank {
        if (banks.any { it.accountNumber == newBank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${newBank.accountNumber} already exit.")
        }
        banks.add(newBank)
        return newBank
    }
}