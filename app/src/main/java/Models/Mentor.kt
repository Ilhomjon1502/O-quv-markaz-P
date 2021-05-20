package Models

class Mentor {
    var id:Int? = null
    var familiya:String? = null
    var ism:String? = null
    var sharifi:String? = null
    var kurs:Kurs? = null

    constructor()




    constructor(id: Int?, familiya: String?, ism: String?, sharifi: String?, kurs: Kurs?) {
        this.id = id
        this.familiya = familiya
        this.ism = ism
        this.sharifi = sharifi
        this.kurs = kurs
    }

    constructor(familiya: String?, ism: String?, sharifi: String?, kurs: Kurs?) {
        this.familiya = familiya
        this.ism = ism
        this.sharifi = sharifi
        this.kurs = kurs
    }

}