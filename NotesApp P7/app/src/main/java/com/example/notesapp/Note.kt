package com.example.notesapp


import com.google.firebase.database.Exclude

//data for Note object
//  has an id,name,and description/content
data class Note(
    @get:Exclude
    var noteId:String = "",
    var noteName: String = "",
    var noteContent: String = ""

)