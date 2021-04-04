package com.shreyas.squaretakehomeapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.base.MockServerBaseTest
import com.shreyas.squaretakehomeapp.service.DirectoryService
import com.shreyas.squaretakehomeapp.utils.ResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class DirectoryRepositoryImplTest : MockServerBaseTest() {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    override fun isMockServerEnabled(): Boolean = true

    private lateinit var repositoryImpl: DirectoryRepositoryImpl
    private lateinit var service: DirectoryService

    @Before
    fun start() {
        service = provideTestDirectoryService()
        repositoryImpl = DirectoryRepositoryImpl(service)
    }

    @Test
    fun `given response as OK when fetch employee list results in full list`() {
        runBlocking {
            mockHttpResponseFromFile("employee_list.json", HttpURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getEmployeeDirectory()) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertThat(response).isNotNull()
                    assertThat(response?.employees?.size).isEqualTo(11)
                }
            }
        }
    }

    @Test
    fun `given response as OK when fetch employee list results in empty list`() {
        runBlocking {
            mockHttpResponseFromFile("empty_list.json", HttpURLConnection.HTTP_OK)
            when (val result = repositoryImpl.getEmployeeDirectory()) {
                is ResultWrapper.SUCCESS -> {
                    val response = result.value.value
                    assertThat(response).isNotNull()
                    assertThat(response?.employees?.size).isEqualTo(0)
                }
            }
        }
    }

    @Test
    fun `given response as FAILURE when fetch employee list results in exception`() {
        runBlocking {
            mockHttpResponse(403)
            when (val result = repositoryImpl.getEmployeeDirectory()) {
                is ResultWrapper.FAILURE -> {
                    Assert.assertNotNull(result)
                    val expectedResponse = ResultWrapper.FAILURE(null)
                    assertThat(expectedResponse.code).isEqualTo((result).code)
                }
            }
        }
    }
}