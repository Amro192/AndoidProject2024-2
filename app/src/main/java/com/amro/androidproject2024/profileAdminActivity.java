package com.amro.androidproject2024;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profileAdminActivity extends AppCompatActivity {

    private TextView name_admin_textView;

    private TextView email_admin_textView;

    private Button btn_edit_profile_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileAdminLayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
    }

    public void setupViews() {
        name_admin_textView = findViewById(R.id.name_admin_textView);
        email_admin_textView = findViewById(R.id.email_admin_textView);
        btn_edit_profile_admin = findViewById(R.id.btn_edit_profile_admin);
    }
}