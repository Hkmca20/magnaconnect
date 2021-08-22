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
import com.magnaconnect.view.model.AttResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.disableVisible;
import static com.magnaconnect.utils.Utility.printStackTrace;

public class ATHAdapter extends RecyclerView.Adapter<ATHAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = ATHAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<AttResponse> gridList;
    public Activity activity;
    private List<AttResponse> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public ATHAdapter(Activity activity, List<AttResponse> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_s2_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            AttResponse item = gridList.get(position);
//            holder.S_No_TV.setText((position + 1) + ".");
            holder.titleTV.setTextSize(20);
            holder.titleTV.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            holder.titleTV.setText("Current Status-->");
            holder.title1TV.setText(item.getStatus());

//            holder.textTV.setText("AttendanceOut:"+item.attendanceOut);
//            holder.text1TV.setText("Email id: " + item.getUserEmail());

        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(AttResponse dataObj, int index) {
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
        TextView S_No_TV, titleTV, textTV, title1TV, text1TV;
        ImageView callIV, locationIV;
        LinearLayout callLayout, locationLayout, buttonContainer;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV = itemView.findViewById(R.id.textTV);
            title1TV = itemView.findViewById(R.id.title1TV);
            title1TV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            text1TV = itemView.findViewById(R.id.text1TV);
            locationLayout = itemView.findViewById(R.id.locationLayout);
            buttonContainer = itemView.findViewById(R.id.buttonContainer);
            locationIV = itemView.findViewById(R.id.locationIV);
            callLayout = itemView.findViewById(R.id.callLayout);
            callIV = itemView.findViewById(R.id.callIV);
            disableVisible(buttonContainer);

//            itemView.setOnClickListener(this);
//            locationLayout.setOnClickListener(this);
//            callLayout.setOnClickListener(this);
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
                List<AttResponse> filteredList = new ArrayList<>();
                for (AttResponse row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getStatus().toLowerCase().contains(charString.toLowerCase()) || row.getAttendanceIn().contains(charSequence)) {
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
            gridListFiltered = (ArrayList<AttResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
