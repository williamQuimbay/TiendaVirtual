package com.odinsystem.tiendavirtual.Modelos

import android.net.Uri

class ModeloImagenSeleccionada {

    var id = ""
    var imgUri : Uri ?= null
    var imgUrl : String ?= null
    var deInternet = false

    constructor()

    constructor(id: String, imgUri: Uri?, imgUrl: String?, deInternet: Boolean) {
        this.id = id
        this.imgUri = imgUri
        this.imgUrl = imgUrl
        this.deInternet = deInternet
    }


}