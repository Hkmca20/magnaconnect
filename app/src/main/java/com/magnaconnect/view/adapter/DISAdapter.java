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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.utils.Utility;
import com.magnaconnect.view.model.VerifyResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class DISAdapter extends RecyclerView.Adapter<DISAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = DISAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<VerifyResponse> gridList;
    public Activity activity;
    private static List<VerifyResponse> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public DISAdapter(Activity activity, List<VerifyResponse> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_dis_dia, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            VerifyResponse item = gridListFiltered.get(position);

            holder.S_No_TV.setText((position + 1) + ".");

            holder.titleTV.setText(item.getShopName());
            holder.title1TV.setText(item.getUserStatus());

            holder.textTV.setText("Mobile no: " + item.getMobileNumber()
            +"\nEmail id: " + item.getUserEmail());
//            holder.text1TV.setText("Email id: " + item.getUserEmail());

        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(VerifyResponse dataObj, int index) {
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
        void onItemClick(String id, int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        RelativeLayout mSquareView;
        TextView S_No_TV, titleTV, textTV, title1TV, text1TV;
        ImageView callIV, locationIV;
        LinearLayout callLayout, locationLayout, buttonContainer;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV =  itemView.findViewById(R.id.textTV);
            title1TV =  itemView.findViewById(R.id.title1TV);
            text1TV =  itemView.findViewById(R.id.text1TV);
            locationLayout=  itemView.findViewById(R.id.locationLayout);
            locationIV =  itemView.findViewById(R.id.locationIV);
            callLayout=  itemView.findViewById(R.id.callLayout);
            callIV =  itemView.findViewById(R.id.callIV);
            buttonContainer = itemView.findViewById(R.id.buttonContainer);
            Utility.disableVisible(title1TV,buttonContainer);

            itemView.setOnClickListener(this);
            locationLayout.setOnClickListener(this);
            callLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(gridListFiltered.get(getAdapterPosition()).getDistributorId(),getAdapterPosition(), v);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                gridListFiltered = gridList;
            } else {
                List<VerifyResponse> filteredList = new ArrayList<>();
                for (VerifyResponse row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if ((row.getShopName()!= null && row.getShopName().toLowerCase().contains(charString.toLowerCase())) || (row.getMobileNumber()!= null && row.getMobileNumber().contains(charSequence))) {
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
            gridListFiltered = (ArrayList<VerifyResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
