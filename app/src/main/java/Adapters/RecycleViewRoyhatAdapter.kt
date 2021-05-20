package Adapters

import Models.Kurs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.h532oquvmarkaz.databinding.ItemRvBarchaRoyhatBinding

class RecycleViewRoyhatAdapter(var list: List<Kurs>, var onCLickRecycleView: OnCLickRecycleView) :
    RecyclerView.Adapter<RecycleViewRoyhatAdapter.Vh>() {

    inner class Vh(var itemRv: ItemRvBarchaRoyhatBinding) :
        RecyclerView.ViewHolder(itemRv.root) {

            fun onBind(kurs: Kurs, position: Int){
                itemRv.txtName.text = kurs.name
                itemRv.root.setOnClickListener {
                    onCLickRecycleView.onItemClick(kurs, position)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBarchaRoyhatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}
interface OnCLickRecycleView{
    fun onItemClick(kurs: Kurs, position: Int)
}