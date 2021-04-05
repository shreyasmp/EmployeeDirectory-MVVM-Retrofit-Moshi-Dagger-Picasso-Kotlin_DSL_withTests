package com.shreyas.squaretakehomeapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shreyas.squaretakehomeapp.R
import com.shreyas.squaretakehomeapp.databinding.EmployeeDirectoryItemBinding
import com.shreyas.squaretakehomeapp.model.Employee
import com.squareup.picasso.Picasso
import javax.inject.Inject

open class EmployeeListAdapter @Inject constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(val binding: EmployeeDirectoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.employee = employee
        }
    }

    @VisibleForTesting
    internal lateinit var employeeList: List<Employee>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding: EmployeeDirectoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.employee_directory_item,
            parent,
            false
        )
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employeeList[position])
        holder.binding.apply {
            picasso.load(employeeList[position].photo_url_small).placeholder(R.drawable.no_photo)
                .into(employeePhoto)
        }
    }

    override fun getItemCount(): Int = employeeList.size

    fun updateEmployeeList(employeeDirectoryList: List<Employee>) {
        employeeList = employeeDirectoryList
    }
}