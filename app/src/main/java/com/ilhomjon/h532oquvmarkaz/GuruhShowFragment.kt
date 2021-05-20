package com.ilhomjon.h532oquvmarkaz

import Adapters.RvTalabaAdapter
import Adapters.RvTalabaClick
import DB.MyDbHelper
import Models.Guruh
import Models.Talaba
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentGuruhShowBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuruhShowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuruhShowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentGuruhShowBinding
    lateinit var guruh: Guruh
    lateinit var talabaList:ArrayList<Talaba>
    lateinit var myDbHelper: MyDbHelper
    lateinit var rvTalabaAdapter: RvTalabaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuruhShowBinding.inflate(LayoutInflater.from(context))

        myDbHelper = MyDbHelper(context)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        guruh = arguments?.getSerializable("keyGuruh") as Guruh

        talabaList = ArrayList()
        val listT = myDbHelper.getAllTalaba()
        for (talaba in listT) {
            if (talaba.guruh?.id == guruh.id){
                talabaList.add(talaba)
            }
        }

        binding.guruhNomiTxt.text = "Guruh nomi: ${guruh.nomi}"

        binding.oquvchilarSoniTxt.text = "O'quvchilar soni: ${talabaList.size}"

        binding.btnDarsBoshlash.setOnClickListener {
            Toast.makeText(context, "Dars boshlandi", Toast.LENGTH_SHORT).show()
            fragmentManager?.popBackStack()
        }

        rvTalabaAdapter = RvTalabaAdapter(talabaList, object : RvTalabaClick{
            override fun edit(talaba: Talaba) {
                findNavController().navigate(R.id.talabaQoshishFragment, bundleOf("keyTalaba" to talaba, "keyKurs1" to guruh.kurs, "keyHas" to 1))
            }

            override fun delete(talaba: Talaba) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setMessage(
                    "${talaba.ism} talaba o'chirilsinmi?\n"
                )
                alertDialog.setPositiveButton(
                    "Ha roziman"
                ) { dialog, which ->
                    myDbHelper.deleteTalaba(talaba)
                    onStart()
                    Toast.makeText(
                        context,
                        "${talaba.ism} ning barcha ma'lumotlari o'chirildi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                alertDialog.setNegativeButton("Bekor qilish")
                {dialog, which ->

                }
                alertDialog.show()
            }
        })
        binding.rvGuruhShow.adapter = rvTalabaAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.talabaQoshishFragment, bundleOf("keyKurs1" to guruh.kurs, "keyHas" to 0))
        return super.onOptionsItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuruhShowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuruhShowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}