package com.example.task.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.task.R
import com.example.task.entities.OnTaskListFragmentInteractionListener
import com.example.task.entities.TaskEntity
import com.example.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val listener: OnTaskListFragmentInteractionListener): RecyclerView.ViewHolder(itemView) {
    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDueDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(task: TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDueDate.text = task.dueDate

        if(task.complete) {
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        mTextDescription.setOnClickListener {
            listener.onListClick(task.id)
        }
    }
}