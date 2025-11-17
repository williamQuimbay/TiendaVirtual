package com.odinsystem.tiendavirtual.Modelos

class modeloCategoria {

    var id : String =""
    var categoria : String = ""


    constructor(){

    }

    constructor(categoria: String, id: String) {
        this.categoria = categoria
        this.id = id
    }


}