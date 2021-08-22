/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.StatResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class STAdapter extends RecyclerView.Adapter<STAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = STAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<StatResponse.StateItem> gridList;
    public Activity activity;
    private static List<StatResponse.StateItem> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public STAdapter(Activity activity, List<StatResponse.StateItem> gridList) {
        this.activity = activity;
        this.gridList = gridList;
        this.gridListFiltered = gridList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataObjectHolder dataObjectHolder = null;
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_sp_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            StatResponse.StateItem item = gridListFiltered.get(position);
            holder.CompanyNameTV.setText(item.getCityName());
        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(StatResponse.StateItem dataObj, int index) {
        gridList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        gridList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return gridListFiltered.size();
    }

    public interface MyClickListener {
        void onItemClick(String id, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        RelativeLayout mSquareView;
        TextView CompanyNameTV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            CompanyNameTV = itemView.findViewById(R.id.CompanyNameTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(gridListFiltered.get(getAdapterPosition()).getCityId(), v);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                gridListFiltered = gridList;
            } else {
                List<StatResponse.StateItem> filteredList = new ArrayList<>();
                for (StatResponse.StateItem row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getCityName().toLowerCase().contains(charString.toLowerCase()) ) {
                        filteredList.add(row);
                    }
                }
                gridListFiltered = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = gridListFiltered;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            gridListFiltered = (ArrayList<StatResponse.StateItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
