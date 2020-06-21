package com.example.fit.ui.registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.fit.R;
import com.example.fit.databinding.ActivitySignUpBinding;
import com.example.fit.ui.setting.SettingFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private ActivitySignUpBinding mActivitySignUpBinding ;
    private FirebaseAuth mAuth;
    private FirebaseFirestore dbStore ;
    private String UserID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.signup));
        mActivitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar() ;
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        startSettingFragment();
        initFirebaseDatabase();
        SignButtonClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void SignButtonClickListener() {
        mActivitySignUpBinding.SignUpButton.setOnClickListener(v -> createUser());
    }
    private void createUser() {
        if (Validation()) {
            mAuth.createUserWithEmailAndPassword(mActivitySignUpBinding.EmailInput.getText().toString(), mActivitySignUpBinding.PassWordInput.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()){
                            UserID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = dbStore.collection("users").document(UserID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("UserName" , mActivitySignUpBinding.UserNameInput.getText().toString());
                            user.put("Email" , mActivitySignUpBinding.EmailInput.getText().toString());
                            user.put("PassWord" , mActivitySignUpBinding.PassWordInput.getText().toString());
                            documentReference.set(user).addOnSuccessListener(aVoid -> {
                                Log.e(TAG, "onSuccess: "+UserID);
                                finish();
                            }).addOnFailureListener(e -> Log.e(TAG, "onFailure: "+ e));
                        }
                    });
        }
    }
    private boolean Validation(){
        boolean valid = true;
        if (TextUtils.isEmpty(mActivitySignUpBinding.UserNameInput.getText())){
            mActivitySignUpBinding.UserNameInput.setError(getString(R.string.EnterUsername));
            valid = false;
        }else if (TextUtils.isEmpty(mActivitySignUpBinding.EmailInput.getText())){
            mActivitySignUpBinding.EmailInput.setError(getString(R.string.EnterEmail));
            valid = false;
        }else if (TextUtils.isEmpty(mActivitySignUpBinding.PassWordInput.getText())){
            mActivitySignUpBinding.PassWordInput.setError(getString(R.string.EnterPassword));
            valid = false;
        }
        return valid;
    }
    private void initFirebaseDatabase(){
        mAuth = FirebaseAuth.getInstance();
        dbStore = FirebaseFirestore.getInstance();
    }
    private void startSettingFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingFragment fragmentVideosList = new SettingFragment();
        fragmentManager.beginTransaction()
                .add(R.id.prefContainer, fragmentVideosList)
                .commit();
    }
}
