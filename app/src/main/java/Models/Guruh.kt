package Models

import java.io.Serializable

class Guruh :Serializable{
    var id:Int? = null
    var nomi:String? = null
    var mentor:Mentor? = null
    var kunlari:String? = null
    var vaqt:String? = null
    var kurs:Kurs? = null


    constructor()
    constructor(
        id: Int?,
        nomi: String?,
        mentor: Mentor?,
        kunlari: String?,
        vaqt: String?,
        kurs: Kurs?
    ) {
        this.id = id
        this.nomi = nomi
        this.mentor = mentor
        this.kunlari = kunlari
        this.vaqt = vaqt
        this.kurs = kurs
    }

    constructor(nomi: String?, mentor: Mentor?, kunlari: String?, vaqt: String?, kurs: Kurs?) {
        this.nomi = nomi
        this.mentor = mentor
        this.kunlari = kunlari
        this.vaqt = vaqt
        this.kurs = kurs
    }

}