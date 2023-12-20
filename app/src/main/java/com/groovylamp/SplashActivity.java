package com.groovylamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.groovylamp.data.Users;
import com.groovylamp.models.User;

public class SplashActivity extends AppCompatActivity {
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Users.getInstance().loadUsers(getCacheDir().getPath());
        LottieAnimationView animationView = findViewById(R.id.lottie_anim);
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.setRepeatMode(LottieDrawable.REVERSE);
        animationView.playAnimation();
        new Handler().postDelayed(() -> {
            SharedPreferences myAccount = getSharedPreferences("myAccount", Context.MODE_PRIVATE);
            String login = myAccount.getString("login", "");
            System.out.println(login);

            User user = Users.getInstance().findUser(login);
            if (user == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", gson.toJson(user));
                startActivity(intent);
            }
            finish();
        }, 2000);
    }
}