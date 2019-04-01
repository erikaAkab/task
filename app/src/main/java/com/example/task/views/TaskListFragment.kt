package com.example.task.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.task.R

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    companion object {
        fun newInstance(): TaskListFragment {
            return TaskListFragment()
        }
    }
}
