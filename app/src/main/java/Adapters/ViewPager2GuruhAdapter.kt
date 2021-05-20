package Adapters

import DB.MyDbHelper
import Models.Guruh
import Models.Talaba
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.h532oquvmarkaz.databinding.ItemViewPager2GuruhBinding

class ViewPager2GuruhAdapter(val myDbHelper: MyDbHelper, val list: List<Guruh>, var rvGuruhOnClick: RvGuruhOnClick)
    :RecyclerView.Adapter<ViewPager2GuruhAdapter.Vh>(){

    inner class Vh(var itemBinding:ItemViewPager2GuruhBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun onBind(position: Int){
            itemBinding.rvViewPagerGuruh.adapter = RvGuruhAdapter(myDbHelper, list, rvGuruhOnClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPager2GuruhBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = 2
}

interface RvGuruhOnClick{
    fun itemClick(guruh: Guruh, talabaList:ArrayList<Talaba>, position:Int)
    fun edit(guruh: Guruh, position:Int)
    fun delete(guruh: Guruh, position:Int)
    fun show(guruh: Guruh, talabaList: ArrayList<Talaba>, position:Int)
}