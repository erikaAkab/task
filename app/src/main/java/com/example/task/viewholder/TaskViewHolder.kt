package com.example.task.viewholder

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.task.R
import com.example.task.entities.OnTaskListFragmentInteractionListener
import com.example.task.entities.TaskEntity
import com.example.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteractionListener): RecyclerView.ViewHolder(itemView) {
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

        mTextDescription.setOnLongClickListener {
            showConfirmationDialog(task)
            true
        }

        mImageTask.setOnClickListener {
            if(task.complete) {
                listener.onUncompleteClick(task.id)
            } else {
                listener.onCompleteClick(task.id)
            }
        }
    }

    private fun showConfirmationDialog(task: TaskEntity) {
        android.app.AlertDialog.Builder(context)
            .setTitle("Remoção de tarefa")
            .setMessage("Deseja remover ${task.description}?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Remover", { dialog, which -> listener.onDeleteClick(task.id) })
            .setNegativeButton("Cancelar", null).show()
    }
}