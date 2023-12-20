package com.groovylamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.groovylamp.databinding.FragmentProfileBinding;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences myAccount = requireContext().getSharedPreferences("myAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myAccount.edit();
        String name = myAccount.getString("login", "");
        binding.logout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SplashActivity.class);
            editor.putString("login", "");
            editor.apply();
            startActivity(intent);
        });
        binding.nameProfile.setText("Hello, " + name + ".");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}