package Models

import java.io.Serializable

class Talaba:Serializable{
    var id:Int? = null
    var familiya:String? = null
    var ism:String? = null
    var telefon:String? = null
    var registratsiyaVaqti:String? = null
    var guruh:Guruh? = null

    constructor(
        id: Int?,
        familiya: String?,
        ism: String?,
        telefon: String?,
        registratsiyaVaqti: String?,
        guruh: Guruh?
    ) {
        this.id = id
        this.familiya = familiya
        this.ism = ism
        this.telefon = telefon
        this.registratsiyaVaqti = registratsiyaVaqti
        this.guruh = guruh
    }

    constructor(
        familiya: String?,
        ism: String?,
        telefon: String?,
        registratsiyaVaqti: String?,
        guruh: Guruh?
    ) {
        this.familiya = familiya
        this.ism = ism
        this.telefon = telefon
        this.registratsiyaVaqti = registratsiyaVaqti
        this.guruh = guruh
    }

    constructor()
}