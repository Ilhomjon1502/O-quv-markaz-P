package com.ilhomjon.h532oquvmarkaz

import Adapters.RvGuruhOnClick
import Adapters.ViewPager2GuruhAdapter
import DB.MyDbHelper
import Models.Guruh
import Models.Kurs
import Models.Talaba
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ilhomjon.h532oquvmarkaz.databinding.FragmentGuruhRoyhatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuruhRoyhatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuruhRoyhatFragment : Fragment() {
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

    lateinit var binding: FragmentGuruhRoyhatBinding
    lateinit var viewPager2GuruhAdapter: ViewPager2GuruhAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var guruhList: ArrayList<Guruh>
    lateinit var talabaList:ArrayList<Talaba>
    lateinit var kurs: Kurs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuruhRoyhatBinding.inflate(LayoutInflater.from(context))


        myDbHelper = MyDbHelper(context)


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        guruhList = ArrayList()
        talabaList = ArrayList()
        kurs = arguments?.getSerializable("keyKurs") as Kurs
        val list = myDbHelper.getAllGuruh()
        for (guruh in list) {
            if (guruh.kurs?.name == kurs.name) {
                guruhList.add(guruh)
            }
        }
        viewPager2GuruhAdapter = ViewPager2GuruhAdapter(myDbHelper, guruhList, object : RvGuruhOnClick {
            override fun itemClick(guruh: Guruh, talabaList: ArrayList<Talaba>, position: Int) {
                findNavController().navigate(R.id.guruhShowFragment, bundleOf("keyGuruh" to guruh))
            }

            override fun edit(guruh: Guruh, position: Int) {
                val bundle = bundleOf("key" to 0, "keyKurs" to kurs, "keyGuruh" to guruh)
                findNavController().navigate(R.id.yangiGuruhQoshishFragment, bundle)
            }

            override fun delete(guruh: Guruh, position: Int) {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setMessage(
                    "${guruh.nomi} guruh o'chirilsinmi?\n" +
                            "Agar ushbu guruh o'chirilsa undagi barcha talabalar ma'lumotlari ham o'chib ketadi!!!"
                )
                alertDialog.setPositiveButton(
                    "Ha roziman"
                ) { dialog, which ->
                    myDbHelper.deleteGuruh(guruh)
                    onStart()
                    Toast.makeText(
                        context,
                        "${guruh.nomi} ning barcha ma'lumotlari o'chirildi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                alertDialog.setNegativeButton("Bekor qilish")
                {dialog, which ->

                }
                alertDialog.show()
            }

            override fun show(guruh: Guruh, talabaList: ArrayList<Talaba>, position: Int) {
                findNavController().navigate(R.id.guruhShowFragment, bundleOf("keyGuruh" to guruh, "talabaList" to talabaList))
            }


        })

        val list1 = arrayOf("Ochilgan guruhlar", "Ochilayotgan guruhlar")
        binding.viewPager2Guruh.adapter = viewPager2GuruhAdapter

        binding.viewPager2Guruh.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1) {
                    setHasOptionsMenu(true)
                } else {
                    setHasOptionsMenu(false)
                }
            }
        })

        TabLayoutMediator(binding.tabGuruh, binding.viewPager2Guruh) { tab, position ->
            tab.text = list1[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val bundle = bundleOf("key" to 1, "keyKurs" to kurs)
        findNavController().navigate(R.id.yangiGuruhQoshishFragment, bundle)
        return super.onOptionsItemSelected(item)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuruhRoyhatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuruhRoyhatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}