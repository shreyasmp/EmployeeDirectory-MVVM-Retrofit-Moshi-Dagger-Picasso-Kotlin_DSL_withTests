package com.shreyas.squaretakehomeapp.viewmodel

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.base.BaseViewModelTest
import com.shreyas.squaretakehomeapp.model.Employee
import com.shreyas.squaretakehomeapp.model.EmployeeResponse
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils.getObjectFromJsonFile
import com.shreyas.squaretakehomeapp.utils.testObserver
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class EmployeeListViewModelTest : BaseViewModelTest() {

    @SpyK
    private lateinit var viewModel: EmployeeListViewModel

    @Mock
    private lateinit var employeeListResponseObserver: Observer<List<Employee>>

    @Before
    override fun setUp() {
        super.setUp()
        viewModel = EmployeeListViewModel(repository)
    }

    @Test
    fun `view model is not null`() {
        assertThat(viewModel).isNotNull()
    }

    @Test
    fun `test employee list live data is null at first`() {
        val employeeListLiveData = viewModel.employeeList.testObserver()
        assertThat(employeeListLiveData.observedValues).isEmpty()
    }

    @Test
    fun `test employee list live data has success data`() {
        val employeeResponse = getObjectFromJsonFile(
            jsonFile = "employee_list.json",
            tClass = EmployeeResponse::class.java
        )
        viewModel._employeeList.value = employeeResponse?.employees
        val employeeListLiveData = viewModel.employeeList.testObserver()
        assertThat(employeeListLiveData.observedValues).containsExactly(employeeResponse?.employees)
    }

    @Test
    fun `test employee list live data has error`() {
        val error = null
        viewModel._employeeList.value = error
        val errorListLiveData = viewModel.employeeList.testObserver()
        assertThat(errorListLiveData.observedValues).containsExactly(error)
    }

    @Test
    fun `test fetch employee list http success is as expected`() {
        val response = getObjectFromJsonFile(
            jsonFile = "employee_list.json",
            tClass = EmployeeResponse::class.java
        )
        viewModel._employeeList.value = response?.employees
        coroutineTestRule.runBlockingTest {
            viewModel.employeeList.observeForever(employeeListResponseObserver)
            `when`(repository.getEmployeeDirectory()).thenAnswer {
                viewModel._employeeList
            }
            viewModel.fetchEmployeeList()
            assertThat(viewModel.employeeList.value).isEqualTo(viewModel._employeeList.value)
            verify(repository, times(2)).getEmployeeDirectory()
        }
    }

    @Test
    fun `test fetch employee list http success is not as expected`() {
        val exception = mock(Exception::class.java)
        coroutineTestRule.runBlockingTest {
            viewModel.employeeList.observeForever(employeeListResponseObserver)
            `when`(repository.getEmployeeDirectory()).thenAnswer {
                exception.message
            }
            viewModel.fetchEmployeeList()
            assertThat(viewModel.employeeList.value).isNull()
            assertThat(viewModel.employeeList.value).isEqualTo(exception.message)
            verify(repository, times(2)).getEmployeeDirectory()
        }
    }

    @After
    fun tearDown() {
        viewModel.employeeList.removeObserver(employeeListResponseObserver)
    }
}