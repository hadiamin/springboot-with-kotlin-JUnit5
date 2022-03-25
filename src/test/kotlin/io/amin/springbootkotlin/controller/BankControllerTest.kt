package io.amin.springbootkotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.amin.springbootkotlin.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

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
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
    
        @Test
        fun `should update an exiting bank`() {
            // given
            val existingBank = Bank("1234", 2.13, 30)
            
            // when
            val patchRequest = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }
            
            // then
            patchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(existingBank))
                    }
                }
            mockMvc.get("$baseUrl/${existingBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(existingBank)) } }
            
        }

        @Test
        fun `should return BAD REQUEST if no bank with given account number exist`() {
            // given
            val badRequest = Bank("non_exiting_bank", 2.13, 30)

            // when
            val requestBody = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(badRequest)
            }

            // then
            requestBody
                .andDo { print() }
                .andExpect { status { isNotFound() } }

        }
        
    }
    
    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class DeleteExistingBank {
    
        @Test
        fun `should the bank with given account number`() {
            // given
            val accountNumber = "1234"
            
            // when
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }
            
            // then
            
            
        }
        
    }

}