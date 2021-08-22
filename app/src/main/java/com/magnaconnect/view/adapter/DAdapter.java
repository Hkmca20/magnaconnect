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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.view.model.StatResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class DAdapter extends RecyclerView.Adapter<DAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = DAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    private static List<StatResponse.StateItem> gridListFiltered;
    public List<StatResponse.StateItem> gridList;
    public Activity activity;
    private ItemFilter mFilter = new ItemFilter();

    public DAdapter(Activity activity, List<StatResponse.StateItem> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_d_it, parent, false);
            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            StatResponse.StateItem item = gridListFiltered.get(position);
            holder.buttonText.setText(String.valueOf(item.getDisplayName()));
            if (Utility.notNullParams(item.getCityName())) {
                Picasso.get().load(item.getCityName()).into(holder.bigImageView);
            }
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
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        RelativeLayout mSquareView;
        ImageView bigImageView;
        TextView buttonText;
        LinearLayout pdf_download_lnr;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            bigImageView = itemView.findViewById(R.id.bigIV);
            buttonText = itemView.findViewById(R.id.buttonText);
            pdf_download_lnr = itemView.findViewById(R.id.pdf_download_lnr);
            pdf_download_lnr.setOnClickListener(this);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
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
                    if (row.getCityName().toLowerCase().contains(charString.toLowerCase())) {
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
