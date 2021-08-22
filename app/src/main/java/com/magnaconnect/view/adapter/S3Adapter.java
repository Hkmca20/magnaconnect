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
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.ProdResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.enableVisible;
import static com.magnaconnect.utils.Utility.printStackTrace;

public class S3Adapter extends RecyclerView.Adapter<S3Adapter.DataObjectHolder> implements Filterable {
    private static String TAG = S3Adapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<ProdResponse> gridList;
    public Activity activity;
    private List<ProdResponse> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public S3Adapter(Activity activity, List<ProdResponse> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_e_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            ProdResponse item = gridList.get(position);
            holder.S_No_TV.setText((position + 1) + ".");
            String s = "Product Name: " + item.getProductName();
            s = s + "\nModel Name : " + item.getModuleName();
            s = s + "\nModel Number : " + item.getModuleNo();
            holder.titleTV.setText(s);
            holder.title1TV.setText(position + "");

            String s1 = "Unit Price: " + item.getUnit_price();
            s1 = s1 + "\nId : " + item.getId();
            holder.textTV.setText(s1);

//            String s2 = "Capacity: " + item.getCapacity();
            String s2 = "Warranty: " + item.getWarranty();
            holder.text1TV.setText(s2);
            holder.quantityTV.setText(String.valueOf(item.getQuantity()));
            enableVisible(holder.textTV, holder.text1TV, holder.plusMinusContainer);
        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(ProdResponse dataObj, int index) {
        gridList.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        gridList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return gridList.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        RelativeLayout mSquareView;
        TextView S_No_TV, titleTV, textTV, title1TV, text1TV, quantityTV;
        RelativeLayout plusMinusContainer;
        Button removeButton, plusButton, minusButton;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV = itemView.findViewById(R.id.textTV);
            title1TV = itemView.findViewById(R.id.title1TV);
            text1TV = itemView.findViewById(R.id.text1TV);
            removeButton = itemView.findViewById(R.id.removeButton);
            plusMinusContainer = itemView.findViewById(R.id.plusMinusContainer);
            plusButton = itemView.findViewById(R.id.plusButton);
            quantityTV = itemView.findViewById(R.id.quantityTV);
            minusButton = itemView.findViewById(R.id.minusButton);
            removeButton.setOnClickListener(this);
            plusButton.setOnClickListener(this);
            minusButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
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
                List<ProdResponse> filteredList = new ArrayList<>();
                for (ProdResponse row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getUserName().toLowerCase().contains(charString.toLowerCase()) || row.getMobileNumber().contains(charSequence)) {
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
            gridListFiltered = (ArrayList<ProdResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
