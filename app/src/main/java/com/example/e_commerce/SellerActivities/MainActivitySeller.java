package com.example.e_commerce.SellerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.db.ViewModels.ProductViewModel;

public class MainActivitySeller extends AppCompatActivity {
    public RecyclerView rv_products;
    public Button btn_add, btn_added, btn_orders;
    public ProductViewModel productViewModel;
    public SellerProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_seller);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_add = findViewById(R.id.btn_add);
        btn_added = findViewById(R.id.btn_added);
        btn_orders = findViewById(R.id.btn_orders);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        rv_products = findViewById(R.id.rv_products);
        rv_products.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SellerProductAdapter();
        rv_products.setAdapter(adapter);

        productViewModel.getProductList().observe(this, products -> {
            if (products != null) {
                adapter.setProducts(products);
            }
        });
        productViewModel.getAllProduct();
        btn_add.setOnClickListener(v -> {
            Intent i = new Intent(this, AddProduct.class);
            startActivity(i);
            finish();
        });
        btn_added.setOnClickListener(v -> {
            Intent i = new Intent(this, AddedProducts.class);
            startActivity(i);
            finish();
        });
        btn_orders.setOnClickListener(v -> {
            Intent i = new Intent(this, Orders.class);
            startActivity(i);
            finish();
        });
    }
}