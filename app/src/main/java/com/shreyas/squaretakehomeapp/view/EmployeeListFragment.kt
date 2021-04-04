package com.shreyas.squaretakehomeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.base.BaseFragment
import com.shreyas.squaretakehomeapp.databinding.FragmentEmployeeDirectoryBinding
import com.shreyas.squaretakehomeapp.viewmodel.EmployeeListViewModel
import javax.inject.Inject

class EmployeeListFragment : BaseFragment<EmployeeListViewModel>() {

    companion object {
        val TAG: String = EmployeeListFragment::class.java.simpleName
    }

    @VisibleForTesting
    internal lateinit var binding: FragmentEmployeeDirectoryBinding

    @Inject
    lateinit var employeeListAdapter: EmployeeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_employee_directory,
            container,
            false
        )
        binding.apply {
            lifecycleOwner = this@EmployeeListFragment
            viewModel = this@EmployeeListFragment.viewModel
        }

        subscribeUI()

        return binding.root
    }

    override fun getTitle(): String = getString(R.string.employee_directory)

    private fun subscribeUI() {
        viewModel.employeeList.observe(viewLifecycleOwner, { employeeList ->
            if (employeeList.isNotEmpty()) {
                binding.employeeList.layoutManager = LinearLayoutManager(context)
                binding.employeeList.adapter = employeeListAdapter
                employeeListAdapter.updateEmployeeList(employeeList)
            }
        })
    }
}