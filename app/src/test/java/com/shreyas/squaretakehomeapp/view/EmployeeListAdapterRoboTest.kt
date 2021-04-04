package com.shreyas.squaretakehomeapp.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.common.truth.Truth.assertThat
import com.shreyas.squaretakehomeapp.model.Employee
import com.shreyas.squaretakehomeapp.model.EmployeeResponse
import com.shreyas.squaretakehomeapp.runner.DirectoryRobolectricTestRunner
import com.shreyas.squaretakehomeapp.utils.TestJsonUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import io.mockk.clearAllMocks
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@RunWith(DirectoryRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class EmployeeListAdapterRoboTest {

    private lateinit var adapter: EmployeeListAdapter

    private val mockPicasso = mock(Picasso::class.java)
    private val mockRequestCreator = mock(RequestCreator::class.java)
    private val mockImageView = mock(ImageView::class.java)

    @Inject
    lateinit var picasso: Picasso

    companion object {

        @BeforeClass
        @JvmStatic
        fun initMocks() {
            mockkStatic(
                Picasso::class
            )
        }

        @AfterClass
        @JvmStatic
        fun resetMocks() {
            unmockkStatic(
                Picasso::class
            )
        }
    }

    @Before
    fun setUp() {
        clearAllMocks()
        adapter = EmployeeListAdapter(mockPicasso)
        loadEmployeeList()
        MockitoAnnotations.openMocks(this)
        picasso = mockPicasso
        `when`(picasso.load(anyString())).thenReturn(mock(RequestCreator::class.java))
    }

    @Test
    fun `test the employee list item count is as expected`() {
        adapter.updateEmployeeList(loadEmployeeList())
        assertThat(adapter.itemCount).isEqualTo(loadEmployeeList().size)
    }

    @Test
    fun `test if view holder is not null`() {
        assertThat(createViewHolder()).isNotNull()
    }

    @Test
    fun `test if view holder is bind properly`() {
        val viewHolder = createViewHolder()
        adapter.updateEmployeeList(loadEmployeeList())
        adapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(loadEmployeeList()[0])
        viewHolder.binding.employeeName.text = loadEmployeeList()[0].full_name
        viewHolder.binding.employeeTeam.text = loadEmployeeList()[0].team

        `when`(mockPicasso.load(anyString())).thenReturn(mockRequestCreator)
        `when`(mockRequestCreator.error(anyInt())).thenReturn(mockRequestCreator)
        `when`(mockRequestCreator.into(mockImageView)).thenReturn(Unit)

        assertThat(viewHolder.binding.employeeName.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.employeeName.text).isEqualTo("Justine Mason")

        assertThat(viewHolder.binding.employeeTeam.visibility).isEqualTo(View.VISIBLE)
        assertThat(viewHolder.binding.employeeTeam.text).isEqualTo("Point of Sale")

        verify(mockPicasso).load(anyString())
        verify(mockRequestCreator).error(anyInt())
        verify(mockRequestCreator).into(mockImageView)
    }

    private fun createViewHolder(): EmployeeListAdapter.EmployeeViewHolder {
        val linearLayout = LinearLayout(getApplicationContext())
        return adapter.onCreateViewHolder(linearLayout, 0)
    }

    private fun loadEmployeeList(): MutableList<Employee> {
        val empList = mutableListOf<Employee>()
        val response =
            TestJsonUtils.getObjectFromJsonFile(
                jsonFile = "employee_list.json",
                EmployeeResponse::class.java
            )
        if (response != null) {
            empList.addAll(response.employees)
        }
        return empList
    }
}