package com.example.whatsapp.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Users(
    var profileName: String? = null,
    var profilePhone: String? = null,
    var profileAbout: String? = null,
    var profileId: String? = null
)
