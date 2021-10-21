package org.dps.admin.network

import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import org.dps.admin.model.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/compassLocation/rest/address/autocomplete?queryString=airtel")
    fun setSuggestionsAsync(@Query("city") cityName: String): Deferred<Response<Suggestion>>


    @POST("/api-admin/classes")
    fun createClassesAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @POST("/api-admin/assign-teacher")
    fun assignTeacherAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>


    @GET("/api-admin/student/{id}")
    fun getStudentByIdAsync(@Path("id") id:String): Deferred<Response<SingleStudentModel>>

    @GET("/api-admin/teacher")
    fun getTeacherByIdAsync(@Query("teacher_id") id:String): Deferred<Response<SingleTeacherModel>>






    @GET("/api-admin/search-students")
    fun getAllStudentByClassIdAsync(@Query("class_id") class_id: String): Deferred<Response<StudentModel>>

    @GET("/api-admin/find-assign-teacher")
    fun getAssignTeacherByClassIdAsync(@Query("class_id") class_id: String): Deferred<Response<AssignTeacherModel>>


    @GET("/api-admin/teachers")
    fun getTeacherAsync(): Deferred<Response<TeacherModel>>

    @GET("/api-admin/teacher")
    fun getTeacherOneAsync(@Query("teacher_id")id:String): Deferred<Response<TeacherSingleModel>>

    @GET("/api-admin/assign-teacher")
    fun getAssignClassTeacherAsync(): Deferred<Response<AssignClassTeacherModel>>

    @PUT("/api-admin/assign-teacher/{class_id}")
    fun assignClassTeacherAsync(@Body param:HashMap<String,String>,@Path("class_id")class_id:String): Deferred<Response<JsonObject>>

    @PUT("/api-admin/assign-rollno")
    fun assignRollNoAsync(@Query("class_id") class_id:String,
                          @Query("student_id") student_id:String,
                          @Query("rollno") rollno:String): Deferred<Response<JsonObject>>

    @DELETE("/api-admin/assign-teacher/{class_id} ")
    fun assignClassTeacherDeleteAsync(@Path("class_id")class_id:String): Deferred<Response<JsonObject>>


    @POST("/api-admin/class")
    fun createClassAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @DELETE("/api-admin/class/{class_id}")
    fun deleteClassAsync(@Path("class_id")class_id:String): Deferred<Response<JsonObject>>

    @GET("/api-admin/class")
    fun getClassAsync(): Deferred<Response<ClassModel>>


    @POST("/api-admin/student")
    fun createStudentAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @GET("/api-admin/student")
    fun getStudentsAsync(): Deferred<Response<StudentModel>>


    @POST("/api-admin/teacher")
    fun createTeacherAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>



    @DELETE("/api-admin/upload-student-file")
    fun deleteStudentUploadFileAsync(@Query("id") id:String, @Query("source") source:String): Deferred<Response<JsonObject>>

    @DELETE("/api-admin/upload-teacher-file")
    fun deleteTeacherUploadFileAsync(@Query("id") id:String, @Query("source") source:String): Deferred<Response<JsonObject>>


}