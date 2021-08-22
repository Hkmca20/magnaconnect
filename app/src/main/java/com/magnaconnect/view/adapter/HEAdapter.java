/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.ProdResponse;

import java.util.ArrayList;
import java.util.List;

import static com.magnaconnect.utils.Utility.enableVisible;
import static com.magnaconnect.utils.Utility.printStackTrace;

public class HEAdapter extends RecyclerView.Adapter<HEAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = HEAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<ProdResponse.Dashboard> productList;
    public Activity activity;
    private List<ProdResponse.Dashboard> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public HEAdapter(Activity activity, List<ProdResponse.Dashboard> productList) {
        this.activity = activity;
        this.productList = productList;
        this.gridListFiltered = productList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataObjectHolder dataObjectHolder = null;
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_e_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            ProdResponse.Dashboard item = productList.get(position);
            holder.titleTV.setText(item.getItem());
            enableVisible(holder.title1TV);
            holder.title1TV.setText(item.getCount());
        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(ProdResponse.Dashboard dataObj, int index) {
        productList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        productList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
                RelativeLayout mSquareView;
        TextView S_No_TV, titleTV, textTV, title1TV, text1TV;
        ImageView iconIV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            mSquareView = itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV = itemView.findViewById(R.id.textTV);
            title1TV = itemView.findViewById(R.id.title1TV);
            text1TV = itemView.findViewById(R.id.text1TV);
            iconIV = itemView.findViewById(R.id.iconIV);
            itemView.setOnClickListener(this);
            mSquareView.setOnClickListener(this);
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
                gridListFiltered = productList;
            } else {
                List<ProdResponse.Dashboard> filteredList = new ArrayList<>();
                for (ProdResponse.Dashboard row : productList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getItem().toLowerCase().contains(charString.toLowerCase()) || row.getCount().contains(charSequence)) {
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
            gridListFiltered = (ArrayList<ProdResponse.Dashboard>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
