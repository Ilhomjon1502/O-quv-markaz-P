package com.ilhomjon.h532oquvmarkaz

import DB.MyDbHelper
import Models.Guruh
import Models.Kurs
import Models.Mentor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentYangiGuruhQoshishBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YangiGuruhQoshishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class YangiGuruhQoshishFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var kurs: Kurs
    lateinit var binding: FragmentYangiGuruhQoshishBinding
    lateinit var arrayMentorismi:ArrayList<String>
    lateinit var mentorList:ArrayList<Mentor>
    lateinit var myDbHelper: MyDbHelper
    var addOrEdit:Int = -1
    var hasMentorSpinner = true
    lateinit var guruh: Guruh

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYangiGuruhQoshishBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        myDbHelper = MyDbHelper(context)
        kurs = arguments?.getSerializable("keyKurs") as Kurs
        addOrEdit = arguments?.getInt("key", -1)!!

        mentorList = ArrayList()
        arrayMentorismi = ArrayList()
        val listM = myDbHelper.getAllMentor()
        for (mentor in listM) {
            if (mentor.kurs?.id == kurs.id){
                mentorList.add(mentor)
                arrayMentorismi.add(mentor.ism!!)
            }
        }

        if (arrayMentorismi.isEmpty()){
            arrayMentorismi.add("Mentor yo'q")
            hasMentorSpinner = false
        }

        val arrayAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, arrayMentorismi)
        binding.mentorTanlaSpinner.adapter = arrayAdapter

        if (addOrEdit == 0){
            guruh = arguments?.getSerializable("keyGuruh") as Guruh
            binding.guruhNomiEd.setText(guruh.nomi)
            binding.guruhVaqtiEd.setText(guruh.vaqt)

            if (guruh.kunlari == "Toq kunlar"){
                binding.guruhKunlariSpinner.setSelection(0)
            }else{
                binding.guruhKunlariSpinner.setSelection(1)
            }

            binding.mentorTanlaSpinner.setSelection(arrayMentorismi.indexOf(guruh.mentor?.ism))
        }

        binding.guruhSaveBtn.setOnClickListener {
            if (addOrEdit == 1) {
                if (hasMentorSpinner) {
                    val guruhNomi = binding.guruhNomiEd.text.toString()
                    val guruhVaqto = binding.guruhVaqtiEd.text.toString()
                    val mentor = mentorList[binding.mentorTanlaSpinner.selectedItemPosition]
                    val kun = binding.guruhKunlariSpinner.selectedItem.toString()
                    myDbHelper.addGuruh(
                        Guruh(guruhNomi, mentor, kun, guruhVaqto, kurs)
                    )
                    Toast.makeText(
                        context,
                        "$guruhNomi guruh ${kurs.name} kursiga qo'shildi",
                        Toast.LENGTH_SHORT
                    ).show()
                    fragmentManager?.popBackStack()
                } else {
                    Toast.makeText(context, "Ma'lumot yetarli emas", Toast.LENGTH_SHORT).show()
                }
            }
            if (addOrEdit == 0){
                guruh.nomi = binding.guruhNomiEd.text.toString()
                guruh.vaqt = binding.guruhVaqtiEd.text.toString()
                guruh.mentor = mentorList[binding.mentorTanlaSpinner.selectedItemPosition]
                guruh.kunlari = binding.guruhKunlariSpinner.selectedItem.toString()

                myDbHelper.editGuruh(guruh)
                Toast.makeText(context, "Guruh tahrirlandi", Toast.LENGTH_SHORT).show()
                fragmentManager?.popBackStack()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment YangiGuruhQoshishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YangiGuruhQoshishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}