package com.example.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.task.R
import com.example.task.entities.OnTaskListFragmentInteractionListener
import com.example.task.entities.TaskEntity
import com.example.task.viewholder.TaskViewHolder

class TaskListAdapter(val taskList: List<TaskEntity>, val listener: OnTaskListFragmentInteractionListener): RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)
    }
}