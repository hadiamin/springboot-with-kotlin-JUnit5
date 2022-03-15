package io.amin.springbootkotlin.controller

import io.amin.springbootkotlin.datasource.BankDataSource
import io.amin.springbootkotlin.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val bankService: BankService) {

    @GetMapping
    fun getBanks() = bankService.getBanks()
}