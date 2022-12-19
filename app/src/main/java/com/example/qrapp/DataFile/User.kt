package com.example.qrapp.DataFile

class User {
    var name:String? = null
    var username:String? = null
    var email:String? = null
    var uid:String? = null
//    var userImg:Int? = null

    constructor(){}

    constructor(name:String?,username:String?,email:String?,uid:String?){
        this.name = name
        this.username = username
        this.email = email
        this.uid = uid
    }

}