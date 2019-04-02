package com.example.task.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.task.R
import com.example.task.adapter.TaskListAdapter
import com.example.task.business.TaskBusiness
import com.example.task.constants.TaskConstants
import com.example.task.entities.OnTaskListFragmentInteractionListener
import com.example.task.util.SecurityPreferences

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TaskListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TaskListFragment : Fragment() {
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: OnTaskListFragmentInteractionListener
    private var mTaskFilter: Int = 0

    companion object {
        fun newInstance(taskFilter: Int): TaskListFragment {
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            mTaskFilter = it.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)
        mTaskBusiness = TaskBusiness(rootView.context)
        mSecurityPreferences = SecurityPreferences(rootView.context)
        mListener = object : OnTaskListFragmentInteractionListener {
            override fun onListClick(taskId: Int) {
                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(rootView.context, TaskFormActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)
            }

            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                loadTasks()
                Toast.makeText(rootView.context, getString(R.string.tarefa_removida_sucesso), Toast.LENGTH_LONG).show()
            }

            override fun onCompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, true)
                loadTasks()
            }

            override fun onUncompleteClick(taskId: Int) {
                mTaskBusiness.complete(taskId, false)
                loadTasks()
            }
        }

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener {
            startActivity(Intent(rootView.context, TaskFormActivity::class.java))
        }

        // Criando recycler view
        // 1 Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 Definir um adapter com os itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListener)

        // 3 Definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(rootView.context)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }
}
