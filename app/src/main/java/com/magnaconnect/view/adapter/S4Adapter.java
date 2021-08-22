/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.ProdResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class S4Adapter extends RecyclerView.Adapter<S4Adapter.DataObjectHolder> implements Filterable {
    private static String TAG = S4Adapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<ProdResponse> gridList;
    public Activity activity;
    private List<ProdResponse> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public S4Adapter(Activity activity, List<ProdResponse> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_s4_it, parent, false);

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
            holder.titleTV.setText(item.getProductName());
            if(item.getQuantity() == 0){
                holder.inputET.setText("");
            } else {
                holder.inputET.setText(String.valueOf(item.getQuantity()));
            }
            holder.inputET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().length()>0){
                        item.setQuantity(Integer.parseInt(charSequence.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
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
        TextView S_No_TV, titleTV, textTV, text1TV;
        EditText inputET;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV =  itemView.findViewById(R.id.textTV);
            inputET= itemView.findViewById(R.id.inputET);
            text1TV =  itemView.findViewById(R.id.text1TV);

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
