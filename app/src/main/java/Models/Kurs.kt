package Models

import java.io.Serializable

class Kurs :Serializable{
    var id:Int? = null
    var name:String? = null
    var about:String? = null

    constructor(id: Int?, name: String?, about: String?) {
        this.id = id
        this.name = name
        this.about = about
    }

    constructor(name: String?, about: String?) {
        this.name = name
        this.about = about
    }

    constructor()


}