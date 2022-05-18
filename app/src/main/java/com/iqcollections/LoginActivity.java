package com.iqcollections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TabLayout tabLayout;
    private ViewPager viewPager;
   private FloatingActionButton google;
   private float v=0;
   private Button btnRegister,btnLogin;
   private EditText edtLoginUser,edtLoginPassword,edtRegisterEmail,edtRegisterUser,edtRegisterPass1,edtRegisterPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        edtLoginUser = findViewById(R.id.email);
        edtLoginPassword =findViewById(R.id.pass);
        edtRegisterUser = findViewById(R.id.username);
        edtRegisterEmail = findViewById(R.id.email2);
        edtRegisterPass1 = findViewById(R.id.pass2);
        edtRegisterPass2 = findViewById(R.id.confirm_pass2);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
    viewPager.setAdapter(adapter);

    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    google.setTranslationY(300);
    tabLayout .setTranslationY(300);


    google.setAlpha(v);
    tabLayout.setAlpha(v);

    google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
    google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String userEmail = edtLoginUser.getText().toString();
        String userPass = edtLoginPassword.getText().toString();
        if(userEmail.isEmpty() || userPass.isEmpty()){
            Toast.makeText(LoginActivity.this, "Email or password is empty", Toast.LENGTH_SHORT).show();

        }else{
            mAuth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else
                    {
                        Toast.makeText(LoginActivity.this, "Login Failure "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
});
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = edtRegisterEmail.getText().toString();
                String userPass = edtRegisterPass1.getText().toString();
                if(userEmail.isEmpty() || userPass.isEmpty() ||(userPass.equals(edtRegisterPass2.getText().toString()))){//checking if the username or password is not empty and password check matches
                    Toast.makeText(LoginActivity.this, "Email or password is empty or passwords do not match", Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Succesfully Registered, You may now login", Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(LoginActivity.this, "An Error has occured "+ task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}