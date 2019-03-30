package com.example.task.business

import android.content.Context
import com.example.task.R
import com.example.task.constants.TaskConstants
import com.example.task.entities.UserEntity
import com.example.task.repository.UserRepository
import com.example.task.util.SecurityPreferences
import com.example.task.util.ValidationException
import java.lang.Exception

class UserBusiness(val context: Context) {
    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun insert(name: String, email: String, password: String) {
        try {
            if (name == "" || email == "" || password == "") {
                throw ValidationException(context.getString(R.string.informe_todos_campos))
            }

            if (mUserRepository.isEmailExistent(email)) {
                throw  ValidationException(context.getString(R.string.email_em_uso))
            }
            val userId = mUserRepository.insert(name, email, password)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)

        } catch (e: Exception) {
            throw e
        }
    }

    fun login(email: String, password: String) {
        val user: UserEntity? = mUserRepository.get(email, password)

        if (user != null) {
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)
        }
    }
}