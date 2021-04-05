package com.shreyas.squaretakehomeapp.base

import com.shreyas.squaretakehomeapp.service.DirectoryService
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.getJsonAsString
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class MockServerBaseTest {

    private lateinit var mockServer: MockWebServer

    @Before
    open fun setup() {
        this.startMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean

    open fun startMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    // Methods to mock responses based on responseCode and response json in assets
    open fun mockHttpResponseFromFile(fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse().setResponseCode(responseCode).setBody(getJsonAsString(fileName))
        )

    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(MockResponse().setResponseCode(responseCode))

    // Create a test service
    fun provideTestDirectoryService(): DirectoryService {
        return Retrofit.Builder().baseUrl(mockServer.url("/")).addConverterFactory(
            MoshiConverterFactory.create()
        ).client(OkHttpClient.Builder().build()).build().create(DirectoryService::class.java)
    }
}