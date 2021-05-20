package DB

import Models.Guruh
import Models.Kurs
import Models.Mentor
import Models.Talaba

interface DbServiceInterface {

    fun addMentor(mentor: Mentor)
    fun editMentor(mentor: Mentor):Int
    fun deleteMentor(mentor: Mentor)
    fun getAllMentor():List<Mentor>

    fun addKurs(kurs: Kurs)
    fun getAllKurs():List<Kurs>
    fun getKursById(id: Int):Kurs

    fun addGuruh(guruh: Guruh)
    fun editGuruh(guruh: Guruh):Int
    fun deleteGuruh(guruh: Guruh)
    fun getAllGuruh():List<Guruh>
    fun getMentorById(id:Int):Mentor

    fun addTalaba(talaba: Talaba)
    fun editTalaba(talaba: Talaba):Int
    fun deleteTalaba(talaba: Talaba)
    fun getAllTalaba():List<Talaba>
    fun getGuruhById(id:Int):Guruh
}