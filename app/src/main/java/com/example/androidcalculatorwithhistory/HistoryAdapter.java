package com.example.androidcalculatorwithhistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidcalculatorwithhistory.dummy.HistoryContent;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends
        SectionedRecyclerViewAdapter<HistoryAdapter.HeaderViewHolder,
                        HistoryAdapter.ViewHolder,
                        HistoryAdapter.FooterViewHolder> {

    private final HistoryFragment.OnListFragmentInteractionListener mListener;
    private final HashMap<String, List<HistoryContent.HistoryItem>> dayValues;
    private final List<String> sectionHeaders;

    public HistoryAdapter(List<HistoryContent.HistoryItem> items, HistoryFragment.OnListFragmentInteractionListener listener) {
        //mValues = items;
        this.dayValues = new HashMap<String,List<HistoryContent.HistoryItem>>();
        this.sectionHeaders = new ArrayList<String>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        for (HistoryContent.HistoryItem hi : items) {
            DateTime t = DateTime.parse(hi.getTimestamp());
            String key = "Entries for " + fmt.print(t);
            List<HistoryContent.HistoryItem> list = this.dayValues.get(key);
            if (list == null) {
                list = new ArrayList<>();
                this.dayValues.put(key, list);
                this.sectionHeaders.add(key);
            }
            list.add(hi);
        }
        mListener = listener;
    }

    @Override
    protected int getSectionCount() {
        return this.sectionHeaders.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return this.dayValues.get(this.sectionHeaders.get(section)).size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_section_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(HeaderViewHolder holder, int section) {
        holder.header.setText(this.sectionHeaders.get(section));
    }

    @Override
    protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(final ViewHolder holder, int section, int position) {
        holder.mItem = this.dayValues.get(this.sectionHeaders.get(section)).get(position);
        holder.mP1.setText(holder.mItem.toString());
        holder.mDateTime.setText(holder.mItem.getTimestamp().toString());
        if (holder.mItem.getMode().equals("Length")) {
            // length icon          holder.mImage.setImageDrawable(holder.mImage.getResources().getDrawable(R.drawable.length_icon));
        } else {
            // volume icon
            holder.mImage.setImageDrawable(holder.mImage.getResources().getDrawable(R.drawable.volume));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mP1;
        public final TextView mDateTime;
        public final ImageView mImage;
        public HistoryContent.HistoryItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mP1 = (TextView) view.findViewById(R.id.p1);
            mDateTime = (TextView) view.findViewById(R.id.timestamp);
            mImage = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDateTime.getText() + "'";
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public HeaderViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.header);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

}
