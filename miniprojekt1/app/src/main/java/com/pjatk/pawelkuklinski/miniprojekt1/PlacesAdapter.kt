package com.pjatk.pawelkuklinski.miniprojekt1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ListElementBinding
import com.pjatk.pawelkuklinski.miniprojekt1.databinding.ListElementPlaceBinding

class PlacesAdapter(
    val viewModel: PlaceViewModel,
    private val context: PlacesActivity,
    private val uid: String?
    ) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    private var placeList = emptyList<Place>()
    lateinit var inflater: LayoutInflater
    lateinit var parent: ViewGroup
    class PlaceViewHolder(val binding: ListElementPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int ): PlaceViewHolder {
        inflater = LayoutInflater.from(parent.context)
        this.parent = parent
        val binding = ListElementPlaceBinding.inflate(inflater, parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.binding.tvName.text = placeList[position].name.toString().take(10)
        holder.binding.tvDescription.text = placeList[position].description.toString()
        holder.binding.tvRadius.text = placeList[position].radius.toString()
        holder.binding.cbIsFavorite.isChecked = placeList[position].isFav
        holder.binding.tvLatitude.text = String.format("%.1f", placeList[position].latitude)
        holder.binding.tvLongitude.text = String.format("%.1f", placeList[position].longitude)
//        holder.binding.root.setOnClickListener {
//            val editPlaceIntent = Intent(context, EditPlacesActivity::class.java)
//            editPlaceIntent.putExtra("placeId", placeList[position].id)
//            editPlaceIntent.putExtra("userUid", uid)
//            context.startActivity(editPlaceIntent)
//
//        }

        holder.binding.root.setOnLongClickListener {
            viewModel.remove(placeList[position])
            notifyDataSetChanged()
            true
        }

        holder.binding.cbIsFavorite.setOnClickListener{
            placeList[position].isFav = holder.binding.cbIsFavorite.isChecked
            viewModel.edit(placeList[position])
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    fun setListPlace(list: List<Place>) {
        placeList = list
        notifyDataSetChanged()
    }
}