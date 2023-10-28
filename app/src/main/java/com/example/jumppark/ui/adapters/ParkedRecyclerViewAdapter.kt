package com.example.jumppark.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.databinding.VehicleItemBinding
import com.example.jumppark.ui.uiUtils.formatMinutes

class ParkedRecyclerViewAdapter() :
    RecyclerView.Adapter<ParkedRecyclerViewAdapter.ParkedViewHolder>() {

    private val callback = getCallback()
    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkedViewHolder {
        val binding = VehicleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParkedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ParkedViewHolder, position: Int) {
        val voucher = differ.currentList[position]
        holder.binding(voucher)
    }

    private fun getCallback() = object : DiffUtil.ItemCallback<Voucher>() {
        override fun areItemsTheSame(oldItem: Voucher, newItem: Voucher): Boolean {
            return oldItem.plate == newItem.plate
        }

        override fun areContentsTheSame(oldItem: Voucher, newItem: Voucher): Boolean {
            return oldItem == newItem
        }

    }

    inner class ParkedViewHolder(val binding: VehicleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(voucher: Voucher) {
            binding.plate.text = voucher.plate.toString()
            binding.predictedTime.text = formatMinutes(voucher.predictedMin)

            binding.root.setOnClickListener {
                clickListener?.let { clickListener -> clickListener(voucher) }
            }
        }
    }

    private var clickListener: ((Voucher) -> Unit)? = null
    fun setOnClickListener(listener: (Voucher) -> Unit) {
        clickListener = listener
    }
}