package com.example.qrapp.DataFile

class Contacts {
    var name:String? = null
    var username:String? = null
    var uid:String? = null
    var userImg:String? = null

    constructor(){}

    constructor(name:String?,username:String?,uid:String?,userImg:String?){
        this.name = name
        this.username = username
        this.uid = uid
        this.userImg = userImg

    }
}