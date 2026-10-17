package com.example.e_commerce.SellerActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_commerce.R;
import com.example.e_commerce.db.ViewModels.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AddProduct extends AppCompatActivity {
    public TextInputEditText et_name, et_desc, et_price, et_quan, et_cgy;
    public ImageView iv_image;
    public ProductViewModel productViewModel;
    public String imgPath;
    public Button btn_add, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_name = findViewById(R.id.et_name);
        et_desc = findViewById(R.id.et_desc);
        et_price = findViewById(R.id.et_price);
        et_quan = findViewById(R.id.et_quan);
        et_cgy = findViewById(R.id.et_cgy);
        iv_image = findViewById(R.id.iv_image);
        btn_add = findViewById(R.id.btn_add);
        btn_back = findViewById(R.id.btn_back);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getStatusMsg().observe(this, msg -> {
            if (msg!=null) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        });
        SharedPreferences pref = getSharedPreferences("session", MODE_PRIVATE);
        int sellerId = pref.getInt("sellerId", -1);

        iv_image.setOnClickListener(v -> {
            imgPickerLauncher.launch("image/*");
        });

        btn_add.setOnClickListener(v -> {
            String name = et_name.getText().toString().trim();
            String desc = et_desc.getText().toString().trim();
            String price = et_price.getText().toString().trim();
            String quan = et_quan.getText().toString().trim();
            String cgy = et_cgy.getText().toString().trim();
            productViewModel.addProduct(name,desc,price,quan,cgy,imgPath, sellerId);
        });
        btn_back.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivitySeller.class);
            startActivity(i);
            finish();
        });
    }
    private final ActivityResultLauncher<String> imgPickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        if (uri != null){
            iv_image.setImageURI(uri);
            imgPath = saveImgToInternal(uri);
        }
    });

    private String saveImgToInternal(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), "img_" + System.currentTimeMillis() + ".png");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while((length = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}