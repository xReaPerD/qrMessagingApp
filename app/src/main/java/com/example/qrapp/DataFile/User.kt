package com.example.qrapp.DataFile

class User {
    var name:String? = null
    var username:String? = null
    var email:String? = null
    var uid:String? = null
    var userImg:String? = null

    constructor(){}

    constructor(name:String?,username:String?,email:String?,uid:String?,userImg:String?){
        this.name = name
        this.username = username
        this.email = email
        this.uid = uid
        this.userImg = userImg
    }

}