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

    @GET("/api/admin/students")
    fun getStudentDataAsync(@Query("class_id") class_id: String): Deferred<Response<StudentModel>>

    @POST("/api/admin/classes")
    fun createClassesAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @POST("/api/admin/assign-teacher")
    fun assignTeacherAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @POST("/api/admin/assign-rollno")
    fun assignRollNoAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>


    @GET("/api/student/{id}")
    fun getStudentByIdAsync(@Path("id") id:String): Deferred<Response<SingleStudentModel>>

    @GET("/api/parent/{id}")
    fun getParentByIdAsync(@Path("id") id:String): Deferred<Response<SingleParentModel>>

    @GET("/api/teacher/{id}")
    fun getTeacherByIdAsync(@Path("id") id:String): Deferred<Response<SingleTeacherModel>>

    @DELETE("/api/admin/delete-upload-file")
    fun deleteUploadFileAsync(@Query("id") id:String,
                              @Query("type") type:String,
                              @Query("source") source:String): Deferred<Response<JsonObject>>


    @GET("/api/class")
    fun getClassAsync(): Deferred<Response<ClassModel>>

    @GET("/api/parent")
    fun getParentsAsync(): Deferred<Response<ParentListModel>>

    @GET("/api/student")
    fun getStudentsAsync(): Deferred<Response<ParentListModel>>

    @GET("/api/teacher")
    fun getTeacherAsync(): Deferred<Response<TeacherModel>>

    @GET("/api/admin/assign-teacher")
    fun getAssignClassTeacherAsync(): Deferred<Response<AssignClassTeacherModel>>

    @PUT("/api/admin/assign-teacher/{class_id}")
    fun assignClassTeacherAsync(@Body param:HashMap<String,String>,@Path("class_id")class_id:String): Deferred<Response<JsonObject>>

    @DELETE("/api/admin/assign-teacher/{class_id} ")
    fun assignClassTeacherDeleteAsync(@Path("class_id")class_id:String): Deferred<Response<JsonObject>>

    @POST("/api/parent")
    fun createParentAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @POST("/api/student")
    fun createStudentAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>

    @POST("/api/teacher")
    fun createTeacherAsync(@Body param:HashMap<String,Any>): Deferred<Response<JsonObject>>



}