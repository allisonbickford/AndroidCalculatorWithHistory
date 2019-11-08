//package com.example.androidcalculatorwithhistory
//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.androidcalculatorwithhistory.HistoryFragment.OnListFragmentInteractionListener
//import com.example.androidcalculatorwithhistory.dummy.HistoryContent.HistoryItem
//import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter
//import org.joda.time.format.DateTimeFormat
//import org.joda.time.format.DateTimeFormatter
//
//
///**
// * [RecyclerView.Adapter] that can display a [HistoryItem] and makes a call to the
// * specified [OnListFragmentInteractionListener].
// * TODO: Replace the implementation with code for your data type.
// */
//class HistoryAdapter(
//    items: List<HistoryItem>,
//    private val mListener: OnListFragmentInteractionListener?
//) : SectionedRecyclerViewAdapter<
//        HistoryAdapter.HeaderViewHolder,
//        HistoryAdapter.ViewHolder,
//        HistoryAdapter.FooterViewHolder>() {
//    var dayValues: HashMap<String, MutableList<HistoryItem>> = HashMap()
//    var sectionHeaders: ArrayList<String> = ArrayList()
//    var fmt: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
//
//    init {
//        for (hi in items) {
//            val key = "Entries for " + fmt.print(hi.timestamp)
//            var list: MutableList<HistoryItem>? = dayValues[key]
//            if (list == null) {
//                list = ArrayList()
//                this.dayValues[key] = list
//                this.sectionHeaders.add(key)
//            }
//            list.add(hi)
//        }
//    }
//
//
//    override fun getSectionCount(): Int {
//        return sectionHeaders.size
//    }
//
//    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent!!.context)
//            .inflate(R.layout.fragment_history, parent, false);
//        return ViewHolder(view)
//    }
//
//    override fun hasFooterInSection(section: Int): Boolean {
//        return false
//    }
//
//    override fun onCreateSectionFooterViewHolder(
//        parent: ViewGroup?,
//        viewType: Int
//    ): FooterViewHolder? {
//        return null
//    }
//
//    override fun getItemCountForSection(section: Int): Int {
//        return dayValues[sectionHeaders[section]]!!.size
//    }
//
//    override fun onCreateSectionHeaderViewHolder(
//        parent: ViewGroup?,
//        viewType: Int
//    ): HeaderViewHolder {
//        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_section_header, parent, false)
//        return HeaderViewHolder(view)
//    }
//
//    override fun onBindSectionFooterViewHolder(p0: FooterViewHolder?, p1: Int) {
//
//    }
//
//    override fun onBindItemViewHolder(holder: ViewHolder?, section: Int, position: Int) {
//        holder!!.mItem = dayValues[sectionHeaders[section]]!![position]
//        holder.mP1.text = holder.mItem.toString();
//        holder.mDateTime.text = holder.mItem?.timestamp.toString()
//        if (holder.mItem?.mode.equals("Length")) {
//            // length icon
//            holder.mImage.setImageDrawable(holder.mImage.resources.getDrawable(R.drawable.length))
//        } else {
//            // volume icon
//            holder.mImage.setImageDrawable(holder.mImage.resources.getDrawable(R.drawable.volume))
//        }
//        holder.mView.setOnClickListener { mListener?.onListFragmentInteraction(holder.mItem!!) }
//    }
//
//    override fun onBindSectionHeaderViewHolder(holder: HeaderViewHolder, section: Int) {
//        holder.header.setText(sectionHeaders[section])
//    }
//
//    private val mOnClickListener: View.OnClickListener
//
//    init {
//        mOnClickListener = View.OnClickListener { v ->
//            val item = v.tag as HistoryItem
//            // Notify the active callbacks interface (the activity, if the fragment is attached to
//            // one) that an item has been selected.
//            mListener?.onListFragmentInteraction(item)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_history, parent, false)
//        return ViewHolder(view)
//    }
//
//    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        val mP1: TextView
//        val mDateTime: TextView
//        val mImage: ImageView
//        var mItem: HistoryItem? = null
//
//        init {
//            mP1 = mView.findViewById(R.id.p1) as TextView
//            mDateTime = mView.findViewById(R.id.timestamp) as TextView
//            mImage = mView.findViewById(R.id.imageView) as ImageView
//        }
//
//        override fun toString(): String {
//            return super.toString() + " '" + mDateTime.text + "'"
//        }
//    }
//
//    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
//        val header: TextView = view.findViewById(R.id.header) as TextView
//    }
//
//    inner class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)
//}
