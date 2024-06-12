package com.amro.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class profile_admin_activity extends AppCompatActivity {

    private TextView name_admin_textView;

    private TextView email_admin_textView;

    private Button btn_edit_profile_admin;

    private  int userId;
    private String userName;

    private  String email_user;

    private int userRole;

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
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getIntExtra("user_id", -1);
            userName = intent.getStringExtra("user_name");
            email_user = intent.getStringExtra("user_email");
            userRole = intent.getIntExtra("admin_id", -1);
            setupViews();
            name_admin_textView.setText(userName);
            email_admin_textView.setText(email_user);
        }

        btn_edit_profile_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void setupViews() {
        name_admin_textView = findViewById(R.id.name_admin_textView);
        email_admin_textView = findViewById(R.id.email_admin_textView);
        btn_edit_profile_admin = findViewById(R.id.btn_edit_profile_admin);
    }
}