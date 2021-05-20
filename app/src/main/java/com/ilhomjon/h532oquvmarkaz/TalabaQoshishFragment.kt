package com.ilhomjon.h532oquvmarkaz

import DB.MyDbHelper
import Models.Guruh
import Models.Kurs
import Models.Mentor
import Models.Talaba
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentTalabaQoshishBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TalabaQoshishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TalabaQoshishFragment : Fragment() {
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

    lateinit var binding: FragmentTalabaQoshishBinding
    lateinit var kurs: Kurs
    lateinit var talaba: Talaba
    lateinit var guruhList: ArrayList<Guruh>
    lateinit var mentorList: ArrayList<Mentor>
    lateinit var myDbHelper: MyDbHelper
    var hasMentor = false
    var hasAddOrEdit = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTalabaQoshishBinding.inflate(LayoutInflater.from(context))

        myDbHelper = MyDbHelper(context)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        guruhList = ArrayList()
        mentorList = ArrayList()
        kurs = arguments?.getSerializable("keyKurs1") as Kurs
        hasAddOrEdit = arguments?.getInt("keyHas", -1)!!
        val listG = myDbHelper.getAllGuruh()
        for (guruh in listG) {
            if (guruh.kurs?.id == kurs.id) {
                guruhList.add(guruh)
            }
        }
        val listM = myDbHelper.getAllMentor()
        for (mentor in listM) {
            if (mentor.kurs?.id == kurs.id) {
                mentorList.add(mentor)
            }
        }
        var guruhLS = ArrayList<String>()

        if (guruhList.isNotEmpty()) {
            for (guruh in guruhList) {
                guruhLS.add(guruh.nomi!!)
            }
        } else {
            guruhLS.add("Guruh yo'q...")
        }

        binding.guruhSpnner.adapter = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_list_item_1,
            guruhLS
        )
        binding.mentorSpinner.adapter = ArrayAdapter(
            binding.root.context, android.R.layout.simple_list_item_1, arrayOf(
                "Guruh tanlang..."
            )
        )

        var mentorL = ArrayList<String>()

        binding.guruhSpnner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                if(guruhList.isNotEmpty()){
                var guruh = guruhList[selectedItemPosition]

                for (mentor in mentorList) {
                    if (mentor.id == guruh.mentor?.id) {
                        mentorL.add(mentor.ism!!)
                        hasMentor = true
                    }
                }
                }

                binding.mentorSpinner.adapter =
                    ArrayAdapter(binding.root.context, android.R.layout.simple_list_item_1, mentorL)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        if (hasAddOrEdit == 1){
            talaba = arguments?.getSerializable("keyTalaba") as Talaba
            binding.sanaTxt.text = talaba.registratsiyaVaqti
            binding.raqamTalaba.setText(talaba.telefon)
            binding.nameTalabaEd.setText(talaba.ism)
            binding.familiyaEd.setText(talaba.familiya)

            binding.mentorSpinner.setSelection(mentorL.indexOf(talaba.guruh?.mentor?.ism))
            binding.guruhSpnner.setSelection(guruhLS.indexOf(talaba.guruh?.nomi))
        }else {

            val date = Date()
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
            binding.sanaTxt.text = simpleDateFormat.format(date)

        }
        binding.talabaAdd.setOnClickListener {
            if (hasAddOrEdit == 0) {
                if (hasMentor) {
                    myDbHelper.addTalaba(
                        Talaba(
                            binding.familiyaEd.text.toString(),
                            binding.nameTalabaEd.text.toString(),
                            binding.raqamTalaba.text.toString(),
                            binding.sanaTxt.text.toString(),
                            guruhList[binding.guruhSpnner.selectedItemPosition]
                        )
                    )
                    Toast.makeText(context, "Talaba qo'shildi", Toast.LENGTH_SHORT).show()
                    fragmentManager?.popBackStack()
                } else {
                    Toast.makeText(context, "Ma'lumot yetarli emas...", Toast.LENGTH_SHORT).show()
                }
            }
            if (hasAddOrEdit == 1){
                talaba.familiya = binding.familiyaEd.text.toString()
                talaba.ism = binding.nameTalabaEd.text.toString()
                talaba.telefon = binding.raqamTalaba.text.toString()
                talaba.guruh = guruhList[binding.guruhSpnner.selectedItemPosition]
                myDbHelper.editTalaba(talaba)
                Toast.makeText(context, "${talaba.id} idlik talaba tahrirlandi", Toast.LENGTH_SHORT).show()
                childFragmentManager?.popBackStack()
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
         * @return A new instance of fragment TalabaQoshishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TalabaQoshishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}