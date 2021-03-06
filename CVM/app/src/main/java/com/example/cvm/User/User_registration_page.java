package com.example.cvm.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cvm.Authentication.LoginActivity;
import com.example.cvm.Authentication.Verify_otp;
import com.example.cvm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class User_registration_page extends AppCompatActivity {

    public Button register;
    public TextView signin_button;
    EditText Name,Address,Age;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //new
    EditText name,email,phone_number,institution;
    TextView qualification;
    Button submit;
    Spinner spinner;
    String Gender;
    FirebaseDatabase database;
    DatabaseReference myRef;

    public static String Mail;
    public static String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_page);


        signin_button=findViewById(R.id.sign_in_button);
        Name=findViewById(R.id.username);
        Address=findViewById(R.id.address);
        Age=findViewById(R.id.age);
        phone_number=findViewById(R.id.Phone_number);
        spinner=findViewById(R.id.Gender_Spinner);

        final LottieAnimationView lottie_smily=findViewById(R.id.covid_animation);
        lottie_smily.setSpeed(1);
        lottie_smily.playAnimation();


        List<String> categories=new ArrayList<>();
        categories.add("Gender");
        categories.add("Male");
        categories.add("Female");

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Gender")){
                    //do nothing
                    Gender=null;
                }
                else{
                    //on selecting sppiner item
                    String item=parent.getItemAtPosition(position).toString();
                    //anything u want do here
                    Gender=spinner.getSelectedItem().toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO AUTOGENERATED METHOD DO HERE
            }
        });

        register=findViewById(R.id.Register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkConnection();
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_registration_page.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void checkConnection(){


            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

            if(Name.length()==0){
                Name.setError("Enter Name!");
            }else if(Address.length()==0){
                Address.setError("Enter Address!");
            }else if(phone_number.length()==0){
                phone_number.setError("Enter Phone Number!");
            }else if(Age.length()==0){
                Age.setError("Enter Age!");
            } else if(Gender==null){
                Toast.makeText(User_registration_page.this, " select Gender",
                        Toast.LENGTH_SHORT).show();
            }else {
                String code = "+91";
                final String Names = Name.getText().toString();
                final String User_Address = Address.getText().toString();
                final String Phone_number = phone_number.getText().toString();
                String phoneNumber = code + Phone_number;
                final String User_Age = Age.getText().toString();

                register.setVisibility(View.INVISIBLE);
                final LottieAnimationView lottiePreloader = findViewById(R.id.preloader_button);

                lottiePreloader.setVisibility(View.VISIBLE);
                lottiePreloader.setSpeed(1);
                lottiePreloader.playAnimation();

                Thread timer = new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Intent intent = new Intent(User_registration_page.this, Verify_otp.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            intent.putExtra("user_name", Names);
                            intent.putExtra("user_address", User_Address);
                            intent.putExtra("age", User_Age);
                            intent.putExtra("gender", Gender);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                timer.start();
            }
    }
}