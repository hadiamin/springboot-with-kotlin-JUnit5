package io.amin.springbootkotlin.datasource

import io.amin.springbootkotlin.model.Bank

interface BankDataSource {

    fun getBanks(): Collection<Bank>
}