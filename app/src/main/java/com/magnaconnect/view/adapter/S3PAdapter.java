/*
 * Created by Hariom.Gupta.Gurugram on 01/02/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.magnaconnect.R;
import com.magnaconnect.view.contract.S3Contract;
import com.magnaconnect.view.model.ProdResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.magnaconnect.utils.Utility.printStackTrace;

public class S3PAdapter extends ArrayAdapter<ProdResponse> {
    S3Contract.View view;
    private String TAG = S3PAdapter.class.getSimpleName();
    private Context context;
    private int resourceId;
    private List<ProdResponse> items, tempItems, suggestions;
    private Filter ProductResponseFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            ProdResponse ProdResponse = (ProdResponse) resultValue;
            return ProdResponse.getProductName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null && charSequence.length() > 2) {
                suggestions.clear();
                for (ProdResponse ProdResponse : tempItems) {
                    if (ProdResponse.getProductName().toLowerCase().startsWith(charSequence.toString().toLowerCase())
                            || ProdResponse.getModuleName().toLowerCase().startsWith(charSequence.toString().toLowerCase())
                            || ProdResponse.getModuleNo().toLowerCase().startsWith(charSequence.toString().toLowerCase())
                            || ProdResponse.getUnit_price().toLowerCase().startsWith(charSequence.toString().toLowerCase())
                    ) {
                        suggestions.add(ProdResponse);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<ProdResponse> tempValues = (ArrayList<ProdResponse>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (ProdResponse prodResponseObj : tempValues) {
                    add(prodResponseObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

    public S3PAdapter(S3Contract.View view, @NonNull Context context, int resourceId, List<ProdResponse> items) {
        super(context, resourceId, items);
        this.items = items;
        this.view = view;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(resourceId, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ProdResponse item = getItem(position);
            holder.addButton.setOnClickListener(v -> view.onAdapterClick(position, 0));
//            if (item.getQuantity() == 0) {
//            enableVisible(addButton);
//            }
            holder.S_No_TV.setText((position + 1) + ".");
            String s = "Product Name : " + item.getProductName();
            s = s + "\nModel Name : " + item.getModuleName();
            s = s + "\nModel Number : " + item.getModuleNo();
            holder.titleTV.setText(s);
            String s1 = "Unit Price: " + item.getUnit_price();
            s1 = s1 + "\nId : " + item.getId();
            holder.textTV.setText(s1);
//            String s2 = "Capacity: " + item.getCapacity();
            String s2 = "Warranty: " + item.getWarranty();
            holder.text1TV.setText(s2);
        } catch (Exception e) {
            printStackTrace(TAG, e);
        }
        return convertView;
    }

    @Override
    public void add(@Nullable ProdResponse object) {
        super.add(object);
    }

    @Override
    public void addAll(ProdResponse... items) {
        super.addAll(items);
    }

    @Nullable
    @Override
    public ProdResponse getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return ProductResponseFilter;
    }

    private static class ViewHolder {
        TextView titleTV, textTV, text1TV, S_No_TV;
        Button addButton;

        ViewHolder(final View itemView) {
            this.titleTV = itemView.findViewById(R.id.titleTV);
            this.textTV = itemView.findViewById(R.id.textTV);
            this.text1TV = itemView.findViewById(R.id.text1TV);
            this.S_No_TV = itemView.findViewById(R.id.S_No_TV);
            this.addButton = itemView.findViewById(R.id.addButton);
        }
    }
}