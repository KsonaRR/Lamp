package com.groovylamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groovylamp.data.Users;
import com.groovylamp.models.User;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static final String KEY_USERNAME = "Username";

    private EditText UserName;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = findViewById(R.id.editTextTextPersonName);
        EditText editText = findViewById(R.id.editTextTextPersonName);
        AppCompatButton appCompatButton = findViewById(R.id.button);
        appCompatButton.setOnClickListener(view -> {
            if(editText.getText().toString().trim().equals("")){
                Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = Users.getInstance().findUser(editText.getText().toString());
            if(user == null){
                user = new User(editText.getText().toString());
                Users.getInstance().addUser(user);
                Users.getInstance().saveUsers( getCacheDir().getPath() );
                SharedPreferences myAccount = getSharedPreferences("myAccount", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myAccount.edit();
                editor.putString("login", editText.getText().toString());
                editor.apply();
                saveUser();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                intent.putExtra("user", gson.toJson(user));
            }else{
                Intent intent = new Intent(this, MainActivity.class);
                SharedPreferences myAccount = getSharedPreferences("myAccount", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = myAccount.edit();
                editor.putString("login", editText.getText().toString());
                editor.apply();
                saveUser();
                intent.putExtra("user", gson.toJson(user));
                startActivity(intent);
            }
        });
    }

    public void saveUser() {
        String userName = UserName.getText().toString();

        HashMap<String, Object> UserData = new HashMap<>();
        UserData.put(KEY_USERNAME, userName);

        //db.document("StandartGlowRepo/Usernames");
        db.collection("StandartGlowRepo").document("Usernames").set(UserData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(LoginActivity.this, "Success login", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}