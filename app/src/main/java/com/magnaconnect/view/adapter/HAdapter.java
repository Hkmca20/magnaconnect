/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.SpinItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class HAdapter extends RecyclerView.Adapter<HAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = HAdapter.class.getSimpleName();
    public List<SpinItem> gridList;
    private List<SpinItem> gridListFiltered;
    public Activity activity;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout mSquareView;
        TextView S_No_TV, titleTV, textTV;
        ImageView iconIV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
            mSquareView = (RelativeLayout)itemView.findViewById(R.id.mSquareView);
            S_No_TV = (TextView) itemView.findViewById(R.id.S_No_TV);
            titleTV = (TextView) itemView.findViewById(R.id.titleTV);
            textTV = (TextView) itemView.findViewById(R.id.textTV);
            iconIV = (ImageView) itemView.findViewById(R.id.iconIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public HAdapter(Activity activity, List<SpinItem> gridList) {
        this.activity = activity;
        this.gridList = gridList;
        this.gridListFiltered = gridList;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataObjectHolder dataObjectHolder = null;
        try {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_g_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            SpinItem item = gridList.get(position);

            final int orangeColor = activity.getResources().getColor(R.color.Chocolate);
//            final int whiteColor = activity.getResources().getColor(R.color.white);
            final int blackColor = activity.getResources().getColor(R.color.Black);
            final int grayColor = activity.getResources().getColor(R.color.colorPrimaryDark);
            StateListDrawable statesLayout = new StateListDrawable();
            StateListDrawable statesImage = new StateListDrawable();

            statesImage.addState(new int[]{android.R.attr.state_pressed}, activity.getResources().getDrawable(item.getIconSelected()));
            statesImage.addState(new int[]{android.R.attr.state_focused}, activity.getResources().getDrawable(item.getIconSelected()));
            statesImage.addState(new int[]{}, activity.getResources().getDrawable(item.getIcon()));

            int[][] states = new int[][]{
                    new int[]{android.R.attr.state_pressed},
                    new int[]{android.R.attr.state_focused},
                    new int[]{}
            };
            int[] listBlack = new int[]{orangeColor, orangeColor, blackColor};
            int[] listGray = new int[]{orangeColor, grayColor, grayColor};
            ColorStateList statesBlack = new ColorStateList(states, listBlack);
            ColorStateList statesGray = new ColorStateList(states, listGray);
            holder.titleTV.setTextColor(statesBlack);
            holder.textTV.setTextColor(statesGray);

            holder.iconIV.setImageDrawable(statesImage);
            holder.mSquareView.setBackground(statesLayout);

            holder.S_No_TV.setText(String.valueOf(position + 1) + ".");
            holder.titleTV.setText("Barcode: "+item.getTitle());
            holder.textTV.setText("Time: "+item.getText());

        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    private ItemFilter mFilter = new ItemFilter();

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                gridListFiltered = gridList;
            } else {
                List<SpinItem> filteredList = new ArrayList<>();
                for (SpinItem row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getText().contains(charSequence)) {
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
            gridListFiltered = (ArrayList<SpinItem>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void addItem(SpinItem dataObj, int index) {
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

}
