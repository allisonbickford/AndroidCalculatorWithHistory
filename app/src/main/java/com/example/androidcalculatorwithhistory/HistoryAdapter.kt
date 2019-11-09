package com.example.androidcalculatorwithhistory

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import com.example.androidcalculatorwithhistory.HistoryFragment.OnListFragmentInteractionListener
import com.example.androidcalculatorwithhistory.dummy.HistoryContent.HistoryItem

/**
 * [RecyclerView.Adapter] that can display a [HistoryItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class HistoryAdapter(
    private val mValues: List<HistoryItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mP1?.text = (holder.mItem.toString())
        holder.mDateTime?.text = (holder.mItem?.timestamp.toString())
        if(holder.mItem!!.mode.equals("Length")){
            holder.mImage?.setImageDrawable(
                holder.mImage.resources?.
                getDrawable(R.drawable.length))
        }else{
            holder.mImage?.setImageDrawable(
                holder.mImage.resources?.
                getDrawable(R.drawable.volume))
        }

        holder.mView?.setOnClickListener(View.OnClickListener {
            mListener?.onListFragmentInteraction(
                holder.mItem
            )
        })
    }

    override fun getItemCount(): Int = mValues.size


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val mView: View?
        val mP1: TextView?
        val mDateTime: TextView?
        var mItem: HistoryItem? = null
        val mImage: ImageView?


        init {
            mView = view
            mP1 = view.findViewById(R.id.p1) as TextView
            mDateTime = view.findViewById(R.id.timestamp) as TextView
            mImage = view.findViewById(R.id.imageView)
        }

        override fun toString(): String {
            return super.toString() + " '" + mDateTime?.text + "'"
        }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

}

