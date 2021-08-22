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
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.model.DashResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class TAdapter extends RecyclerView.Adapter<TAdapter.DataObjectHolder> implements Filterable {
    private static String TAG = TAdapter.class.getSimpleName();
    private static MyClickListener myClickListener;
    public List<DashResponse.Tertiary> gridList;
    public Activity activity;
    private List<DashResponse.Tertiary> gridListFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public TAdapter(Activity activity, List<DashResponse.Tertiary> gridList) {
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_g_it, parent, false);

            dataObjectHolder = new DataObjectHolder(view);
        } catch (Exception ex) {
        }
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            DashResponse.Tertiary item = gridList.get(position);

//            final int orangeColor = activity.getResources().getColor(R.color.Chocolate);
////            final int whiteColor = activity.getResources().getColor(R.color.white);
//            final int blackColor = activity.getResources().getColor(R.color.Black);
//            final int grayColor = activity.getResources().getColor(R.color.colorPrimaryDark);
//            StateListDrawable statesLayout = new StateListDrawable();
//            StateListDrawable statesImage = new StateListDrawable();

//            statesImage.addState(new int[]{android.R.attr.state_pressed}, activity.getResources().getDrawable(item.getIconSelected()));
//            statesImage.addState(new int[]{android.R.attr.state_focused}, activity.getResources().getDrawable(item.getIconSelected()));
//            statesImage.addState(new int[]{}, activity.getResources().getDrawable(item.getIcon()));
//
//            int[][] states = new int[][]{
//                    new int[]{android.R.attr.state_pressed},
//                    new int[]{android.R.attr.state_focused},
//                    new int[]{}
//            };
//            int[] listBlack = new int[]{orangeColor, orangeColor, blackColor};
//            int[] listGray = new int[]{orangeColor, grayColor, grayColor};
//            ColorStateList statesBlack = new ColorStateList(states, listBlack);
//            ColorStateList statesGray = new ColorStateList(states, listGray);
//            holder.titleTV.setTextColor(statesBlack);
//            holder.textTV.setTextColor(statesGray);
//
//            holder.iconIV.setImageDrawable(statesImage);
//            holder.mSquareView.setBackground(statesLayout);
//
            holder.S_No_TV.setText((position + 1) + ".");
            String s = "SerialNo: " + item.getSerialNo();
            s = s + "\nBilled by Id: " + item.getBilledById();
            s = s + "\nBilled by Name: " + item.getBilledByName();

            String s1 = "Billed Date: " + item.getBillingDate();
            s1 = s1 + "\nModel Name: " + item.getModuleName();
            s1 = s1 + "\nModel No: " + item.getModuleNo();
            holder.titleTV.setText(s);
            holder.title1TV.setText(s1);

            String s2 = "Primary Award: " + item.getPrimaryAward();
            s2 = s2 + "\nPrimary To: " + item.getPrimaryTo();
            s2 = s2 + "\nScheme Id: " + item.getSchemeId();

            String s3 = "Secondary Award: " + item.getSecondaryAward();
            s3 = s3 + "\nState: " + item.getState();
            s3 = s3 + "\nTo Name: " + item.getToName();
            s3 = s3 + "\nType: " + item.getCattype();
            holder.textTV.setText(s2);
            holder.text1TV.setText(s3);

        } catch (Resources.NotFoundException e) {
            printStackTrace(TAG, e);
        }
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void addItem(DashResponse.Tertiary dataObj, int index) {
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
        ImageView iconIV;

        public DataObjectHolder(final View itemView) {
            super(itemView);
//            mSquareView = (RelativeLayout)itemView.findViewById(R.id.mSquareView);
            S_No_TV = itemView.findViewById(R.id.S_No_TV);
            titleTV = itemView.findViewById(R.id.titleTV);
            textTV = itemView.findViewById(R.id.textTV);
            title1TV = itemView.findViewById(R.id.title1TV);
            text1TV = itemView.findViewById(R.id.text1TV);
            iconIV = itemView.findViewById(R.id.iconIV);
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
                List<DashResponse.Tertiary> filteredList = new ArrayList<>();
                for (DashResponse.Tertiary row : gridList) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getModuleName().toLowerCase().contains(charString.toLowerCase()) || row.getBilledById().contains(charSequence)) {
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
            gridListFiltered = (ArrayList<DashResponse.Tertiary>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
