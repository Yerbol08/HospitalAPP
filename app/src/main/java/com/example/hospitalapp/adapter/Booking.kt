package com.example.hospitalapp.adapter

data class Booking(
    val doctor: String,
    val date: String,
    val time: String,
    val text: String,
    val name: String,
    val f_name: String,
    val userEmail: String,
)
{
    constructor() : this("", "", "", "", "", "", "")
}
