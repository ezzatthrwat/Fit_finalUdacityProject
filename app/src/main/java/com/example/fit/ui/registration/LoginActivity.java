package com.example.fit.ui.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.fit.R;
import com.example.fit.databinding.ActivityLoginBinding;
import com.example.fit.ui.video_list.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mActivityLoginBinding ;
    private FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.login));
        mActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginButtonClickListener();
        createAccountClickListener();

    }

    private void createAccountClickListener() {
        mActivityLoginBinding.CreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginButtonClickListener() {
        mActivityLoginBinding.LoginButton.setOnClickListener(v -> {
            if (Validation()){
                mAuth.signInWithEmailAndPassword(mActivityLoginBinding.LoginUserNameInput.getText().toString(), mActivityLoginBinding.LoginPassWordInput.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private boolean Validation(){
        boolean Valid = true ;

        if (TextUtils.isEmpty(mActivityLoginBinding.LoginUserNameInput.getText())){
            Valid = false ;
            mActivityLoginBinding.LoginUserNameInput.setError(getString(R.string.invalid_username));
        }else if (TextUtils.isEmpty(mActivityLoginBinding.LoginPassWordInput.getText())){
            mActivityLoginBinding.LoginPassWordInput.setError(getString(R.string.invalid_password));
            Valid = false ;
        }
        return Valid ;
    }
}
