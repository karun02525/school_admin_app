package org.dps.admin.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.dps.admin.App
import org.dps.admin.R
import org.dps.admin.model.ClassData
import org.dps.admin.model.ParentData
import org.dps.admin.model.StudentData
import org.dps.admin.model.TeacherData
import org.dps.admin.network.ApiStatus
import org.dps.admin.network.RestClient
import org.json.JSONObject

class ClassViewModel(private val restClient: RestClient) : ViewModel() {

     val msg = MutableLiveData<String>()
     val success = MutableLiveData<String>()
     val classData = MutableLiveData<List<ClassData>>()
     val parentsData = MutableLiveData<List<ParentData>>()
     val teacherList = MutableLiveData<List<TeacherData>>()
     val studentsList = MutableLiveData<List<StudentData>>()

    init {
     //   getClasses()
      //  getTeacher()
    }




    private fun getTeacher() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getTeacherAsync().await().let {
                    if (it.isSuccessful)
                        teacherList.value=it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun assignTeacherAsync(class_id: String, className: String, section: String,teacher_id: String) {
        val params: HashMap<String, Any> = HashMap()
        params["class_id"] = class_id
        params["class_name"] = className
        params["section"] = section
        params["teacher_id"] = teacher_id
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().assignTeacherAsync(params).await().let {
                    if (it.isSuccessful)
                        msg.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun assignRollNo(vararg data: String) {
        val params: HashMap<String, Any> = HashMap()
        params["class_id"] = data[0]
        params["class_name"] = data[1]
        params["student_id"] = data[2]
        params["section"] = data[3]
        params["roll_no"] = data[4]
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().assignRollNoAsync(params).await().let {
                    if (it.isSuccessful)
                        msg.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun createStudentAsync(params: HashMap<String, Any>) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createStudentAsync(params).await().let {
                    if (it.isSuccessful)
                        success.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun createParent(params: HashMap<String, Any>) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createParentAsync(params).await().let {
                    if (it.isSuccessful)
                        success.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    fun createTeacherAsync(params: HashMap<String, Any>) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createTeacherAsync(params).await().let {
                    if (it.isSuccessful)
                        success.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }
    fun getStudentDataAsync(class_id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getStudentDataAsync(class_id).await().let {
                    if (it.isSuccessful)
                        studentsList.value= it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

}
