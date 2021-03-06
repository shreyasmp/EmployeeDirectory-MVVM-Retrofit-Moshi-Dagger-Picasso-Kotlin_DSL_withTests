package com.shreyas.squaretakehomeapp.viewmodel

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.shreyas.squaretakehomeapp.base.BaseViewModelTest
import com.shreyas.squaretakehomeapp.model.Employee
import com.shreyas.squaretakehomeapp.model.EmployeeResponse
import com.shreyas.squaretakehomeapp.repository.NetworkStatusRepository
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.squaretakehomeapp.utils.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class EmployeeListViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: EmployeeListViewModel

    @Mock
    private lateinit var employeeListResponseObserver: Observer<List<Employee>>

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = EmployeeListViewModel(repository, networkRepository)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `employee list live data is null at first`() {
        val employeeListLiveData = viewModel.employeeList.testObserver()
        assertThat(employeeListLiveData.observedValues).isEmpty()
    }

    @Test
    fun `employee list live data has success data`() {
        val employeeResponse = getObjectFromJsonFile(
            jsonFile = "employee_list.json",
            tClass = EmployeeResponse::class.java
        )
        viewModel._employeeList.value = employeeResponse?.employees
        val employeeListLiveData = viewModel.employeeList.testObserver()
        assertThat(employeeListLiveData.observedValues).containsExactly(employeeResponse?.employees)
    }

    @Test
    fun `employee list live data has error`() {
        val error = null
        viewModel._employeeList.value = error
        val errorListLiveData = viewModel.employeeList.testObserver()
        assertThat(errorListLiveData.observedValues).containsExactly(error)
    }

    @Test
    fun `on http success fetch employee list is as expected`() {
        coroutineTestRule.runBlockingTest {
            // Setup
            val response = getObjectFromJsonFile(
                jsonFile = "employee_list.json",
                tClass = EmployeeResponse::class.java
            )
            viewModel._employeeList.value = response?.employees
            viewModel.employeeList.observeForever(employeeListResponseObserver)

            // Expectation
            doReturn(viewModel.employeeList).`when`(repository).getEmployeeDirectory()

            // Execution
            viewModel.fetchEmployeeList()

            // Assertion
            assertThat(viewModel.employeeList.value).isEqualTo(response?.employees)

            // Verification
            verify(repository, times(1)).getEmployeeDirectory()
            verify(employeeListResponseObserver).onChanged(viewModel.employeeList.value)
        }
    }

    @Test
    fun `on http error fetch employee list is empty`() {
        coroutineTestRule.runBlockingTest {
            val response = getObjectFromJsonFile(
                jsonFile = "empty_list.json",
                tClass = EmployeeResponse::class.java
            )
            viewModel._employeeList.value = response?.employees
            viewModel.employeeList.observeForever(employeeListResponseObserver)

            doThrow(RuntimeException::class).`when`(repository).getEmployeeDirectory()

            viewModel.fetchEmployeeList()

            assertThat(viewModel.employeeList.value).isEmpty()

            verify(employeeListResponseObserver).onChanged(viewModel.employeeList.value)
        }
    }

    @Test
    fun `network status for WiFi is as expected`() {
        val wifiStatus = NetworkStatusRepository.NetworkStatus.WiFi
        assertThat(viewModel.isNetworkStatusWiFi(wifiStatus)).isTrue()
    }

    @Test
    fun `network status for Cellular is as expected`() {
        val cellularStatus = NetworkStatusRepository.NetworkStatus.Cellular
        assertThat(viewModel.isNetworkStatusCellular(cellularStatus)).isTrue()
    }

    @Test
    fun `network status for No Network is as expected`() {
        val noStatus = NetworkStatusRepository.NetworkStatus.NoNetworkAvailable
        assertThat(viewModel.isNetworkStatusNotAvailable(noStatus)).isTrue()
    }

    @After
    fun tearDown() {
        viewModel.employeeList.removeObserver(employeeListResponseObserver)
    }
}