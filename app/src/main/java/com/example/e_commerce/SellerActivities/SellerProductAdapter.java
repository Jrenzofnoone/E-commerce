package com.example.e_commerce.SellerActivities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SellerProductAdapter extends RecyclerView.Adapter<SellerProductAdapter.ViewHolder> {
    List<Products> products = new ArrayList<>();
    public void setProducts(List<Products> products){
        this.products = products;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SellerProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerProductAdapter.ViewHolder holder, int position) {
        Products currentProduct = products.get(position);
        String imgPath = currentProduct.getImg();
        if (imgPath!=null && !imgPath.isEmpty()){
            File imgFile = new File(imgPath);
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.iv_image.setImageBitmap(bitmap);
        } else {
            holder.iv_image.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.tv_name.setText(currentProduct.getName());
        holder.tv_desc.setText(currentProduct.getDesc());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_desc, tv_price;
        ImageView iv_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
