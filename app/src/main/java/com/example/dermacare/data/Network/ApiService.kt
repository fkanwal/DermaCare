package com.example.dermacare.data.Network

import com.example.dermacare.data.model.Doctors
import retrofit2.http.GET

import retrofit2.http.Path

interface  ApiService{
    @GET("Doctors")
    suspend fun getDoctors ():List<Doctors>

//    @GET("Doctors/{id}")
//    suspend fun getDoctorsById(@Path("id")id:Int):Doctors

}