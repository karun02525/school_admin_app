package org.dps.admin.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.dps.admin.App
import org.dps.admin.R
import org.dps.admin.model.*
import org.dps.admin.network.ApiStatus
import org.dps.admin.network.RestClient
import org.json.JSONObject

class AssignClassTeacherViewModel(private val restClient: RestClient) : ViewModel() {

     val msg = MutableLiveData<String>()
     val success = MutableLiveData<String>()
     val id = MutableLiveData<String>("")


    val classDataList = MutableLiveData<List<ClassData>>()
    val parentsDataList = MutableLiveData<List<ParentData>>()




     val teacherList = MutableLiveData<List<TeacherData>>()
     val teacherData = MutableLiveData<TeacherData>()
     val studentsList = MutableLiveData<List<StudentData>>()

     val teacherAssignList = MutableLiveData<List<DataModel>>()
     val teacherAssign = MutableLiveData<AssignTeacherModelData>()

    init {
      //  getClasses()
      //  getTeacher()
    }

    fun createClass(classname: String, section: MutableList<String>) {
        val params: HashMap<String, Any> = HashMap()
        params["classname"] = classname
        params["section"] = section
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createClassesAsync(params).await().let {
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



    fun getAssignClassTeacher() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getAssignClassTeacherAsync().await().let {
                    if (it.isSuccessful)
                        teacherAssignList.value=it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }


    fun assignClassTeacherAsync(class_id: String,teacher_id: String) {
        val params: HashMap<String, String> = HashMap()
        params["teacher_id"] = teacher_id
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().assignClassTeacherAsync(params,class_id).await().let {
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

    //Delete Teacher class
    fun assignClassTeacherDelete(class_id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().assignClassTeacherDeleteAsync(class_id).await().let {
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

    //Delete a class
    fun deleteClass(class_id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().deleteClassAsync(class_id).await().let {
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


    // Assign Roll no for Students
    fun assignRollNo(class_id:String,student_id:String,roll_no:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().assignRollNoAsync(class_id,student_id,roll_no).await().let {
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

    // Get Assign Teacher by class id
    fun getAssignTeacherByClassId(class_id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getAssignTeacherByClassIdAsync(class_id).await().let {
                    if (it.isSuccessful)
                        teacherAssign.value= it.body()!!.data
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }


    //Get One Teacher by id
    fun getTeacherOne(id:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getTeacherOneAsync(id).await().let {
                    if (it.isSuccessful)
                        teacherData.value=it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }
    //Get All Teacher
    fun getTeacher() {
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

    //Get All Students
    fun getStudents() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getStudentsAsync().await().let {
                    if (it.isSuccessful)
                        studentsList.value=it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    // Get All Student by class id
    fun getAllStudentByClassId(class_id: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getAllStudentByClassIdAsync(class_id).await().let {
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



    // Get All Classes
    fun getClasses() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getClassAsync().await().let {
                    if (it.isSuccessful)
                        classDataList.value=it.body()!!.data!!
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    //Get Student by id
    val singleStudentData = MutableLiveData<SingleStudentModelData>()
    fun getStudentById(id:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getStudentByIdAsync(id).await().let {
                    if (it.isSuccessful)
                        singleStudentData.value=it.body()!!.data
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }
    //Get Teacher by id
    val singleTeacherData = MutableLiveData<SingleTeacherModelData>()
    fun getTeacherById(id:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().getTeacherByIdAsync(id).await().let {
                    if (it.isSuccessful)
                        singleTeacherData.value=it.body()!!.data
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }


    //delete file user profile or documents
    val deleteFileSuccess = MutableLiveData("")
    fun deleteStudentUploadFile(id:String,source:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().deleteStudentUploadFileAsync(id,source).await().let {
                    if (it.isSuccessful)
                        deleteFileSuccess.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }


    //delete file user profile or documents
    fun deleteTeacherUploadFile(id:String,source:String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().deleteTeacherUploadFileAsync(id,source).await().let {
                    if (it.isSuccessful)
                        deleteFileSuccess.value = JSONObject(it.body().toString()).optString("message")
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }

    //Create a class
    fun createClass(classname: String) {
        val params: HashMap<String, Any> = HashMap()
        params["name"] = classname
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createClassAsync(params).await().let {
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



    //Create Student
    fun createStudentAsync(params: HashMap<String, Any>) {
        id.value=""
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createStudentAsync(params).await().let {
                    if (it.isSuccessful) {
                        success.value = JSONObject(it.body().toString()).optString("message")
                        id.value = JSONObject(it.body().toString()).optString("id")
                    }
                    else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }


    //Create Teacher
    fun createTeacher(params: HashMap<String, Any>) {
        id.value=""
        GlobalScope.launch(Dispatchers.Main) {
            try {
                restClient.webServices().createTeacherAsync(params).await().let {
                    if (it.isSuccessful) {
                        success.value = JSONObject(it.body().toString()).optString("message")
                        id.value = JSONObject(it.body().toString()).optString("id")
                    } else
                        msg.value = ApiStatus.isCheckAPIStatus(it.code(), it.errorBody())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                msg.value = App.appContext?.getString(R.string.no_internet_available)
            }
        }
    }



}
