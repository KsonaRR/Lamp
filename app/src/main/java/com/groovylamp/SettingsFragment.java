package com.groovylamp;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.groovylamp.databinding.FragmentSettingsBinding;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Objects;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SettingsFragment";
    private FragmentSettingsBinding binding;
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //UserName = UserName.findViewById(R.id.editTextTextPersonName);
    }

    public void initScreen(SharedPreferences myAccount) {
        boolean power = myAccount.getBoolean("power", false);
        int progressBrightness = myAccount.getInt("progressBrightness", 100);
        int progressSpeed = myAccount.getInt("progressSpeed", 20);
        int pickedColor = myAccount.getInt("pickedColor", 0);
        int effectNumber = myAccount.getInt("effect", 0);
        System.out.println("effectNumber");
        System.out.println(effectNumber);
        binding.powerButton.setText(power? "TURN OFF" : "TURN ON");
        binding.powerButton.setBackgroundColor(power? Color.GREEN : Color.RED);
        binding.seekBarBrightness.setProgress(progressBrightness);
        binding.progressBrightness.setText(String.valueOf(progressBrightness));
        binding.seekBarSpeed.setProgress(progressSpeed);
        binding.progressSpeed.setText(String.valueOf(progressSpeed));
        binding.pickedColor.setBackgroundColor(pickedColor);
//        binding.spinner.setSelection(effectNumber, true);
    }

//    public void initSpinner() {
//        String[] arraySpinner = new String[] {
//                "Смерч", "Смена", "Градиент", "Частицы", "Костёр", "Огонь", "Конфетти"
//        };
////        Spinner s = binding.spinner;
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),
//                android.R.layout.simple_spinner_item, arraySpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        s.setAdapter(adapter);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences myAccount = requireContext().getSharedPreferences("myAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myAccount.edit();
//        initSpinner();
        initScreen(myAccount);
//        binding.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println(position);
//                editor.putInt("effect", position);
//                //saveSettings(true);
//                editor.apply();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                binding.powerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean power = !myAccount.getBoolean("power", false);
                        editor.putBoolean("power", power);
                        editor.apply();
                        Button button = (Button) v;
                        button.setText(power ? "TURN OFF" : "TURN ON");
                        button.setBackgroundColor(power ? Color.GREEN : Color.RED);

                    }
                });
            }
        };

        binding.powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean power = !myAccount.getBoolean("power", false);
                editor.putBoolean("power", power);
                editor.apply();
                Button button = (Button) v;
                button.setText(power ? "TURN OFF" : "TURN ON");
                button.setBackgroundColor(power ? Color.GREEN : Color.RED);

            }
        });


        binding.seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putInt("progressBrightness", progress);
                editor.apply();
                binding.progressBrightness.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        binding.seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putInt("progressSpeed", progress);
                editor.apply();
                binding.progressSpeed.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonPickupColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog.Builder(getContext())
                        .setTitle("Color")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton("Choose color",
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        binding.pickedColor.setBackgroundColor(envelope.getColor());
                                        editor.putInt("pickedColor", envelope.getColor());
                                        editor.apply();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .attachAlphaSlideBar(true) // the default value is true.
                        .attachBrightnessSlideBar(true)  // the default value is true.
                        .setBottomSpace(12) // set a bottom space between the last slidebar and buttons.
                        .show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    BroadcastReceiver timerFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            binding.powerButton.performClick();
        }
    };

}