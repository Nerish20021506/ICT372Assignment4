package com.koi.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koi.ecommerceapp.data.FakeRepository;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        EditText etFullName = findViewById(R.id.etFullName);
        EditText etAddress = findViewById(R.id.etAddress);
        EditText etPhone = findViewById(R.id.etPhone);
        EditText etEmail = findViewById(R.id.etEmail);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Prevent empty checkout
        if (FakeRepository.getCart().isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnPlaceOrder.setOnClickListener(v -> {
            if (etFullName.getText().toString().isEmpty() ||
                    etAddress.getText().toString().isEmpty() ||
                    etPhone.getText().toString().isEmpty() ||
                    etEmail.getText().toString().isEmpty()) {

                Toast.makeText(this, "Fill in all shipping info", Toast.LENGTH_SHORT).show();
                return;
            }

            FakeRepository.placeOrder();
            Toast.makeText(this, "✅ Order placed!", Toast.LENGTH_SHORT).show();

            // After checkout → go to Purchase History
            startActivity(new Intent(this, PurchaseHistoryActivity.class));
            finish();
        });
    }
}
