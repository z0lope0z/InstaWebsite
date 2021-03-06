package com.lopefied.instawebsite.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lopefied.instawebsite.R;
import com.lopefied.instawebsite.model.BarcodeProduct;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lope on 4/30/15.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<BarcodeProduct> barcodeProducts;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        @InjectView(R.id.txt_product_name)
        public TextView txtProductName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductListAdapter(List<BarcodeProduct> barcodeProducts) {
        this.barcodeProducts = barcodeProducts;
    }

    public void setItems(List<BarcodeProduct> barcodeProducts) {
        this.barcodeProducts = barcodeProducts;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtProductName.setText(barcodeProducts.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return barcodeProducts.size();
    }
}