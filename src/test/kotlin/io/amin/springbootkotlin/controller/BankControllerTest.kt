package io.amin.springbootkotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.amin.springbootkotlin.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(val mockMvc: MockMvc, val objectMapper: ObjectMapper){

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("getBanks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {

            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") {value("1234")}
                }
        }

    }

    @Nested
    @DisplayName("getBank")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number`() {

            // when/then
            val accountNumber = 1234

            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value(0.13) }
                    jsonPath("$.transactionFee") { value(12) }
                }

        }
        
        @Test
        fun `should return NOT FOUND if the account number does not exit`() {

            val accountNumber = "does_not_exit"
            
            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {
    
        @Test
        fun `should add new bank`() {
            // given
            val newBank = Bank("222", 2.7, 29)

            // when
            val requestBody = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            requestBody
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("222") }
                    jsonPath("$.trust") { value(2.7) }
                    jsonPath("transactionFee") { value(29) }
                }

        }

        @Test
        fun `should return BAD REQUEST if bank already exit`() {
            // given
            val existingBank = Bank("1234", 2.3, 30)

            // when
            val requestBody = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }

            // then
            requestBody
                .andDo { print() }
                .andExpect { status { isBadRequest() } }

        }
        
    }

}