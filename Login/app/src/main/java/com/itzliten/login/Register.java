package com.itzliten.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    private Button btnSignUp;
    private EditText fullNameET,mobileET,passwordET ;
    private TextView signIn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullNameET=(EditText)findViewById(R.id.fullnameET);
        mobileET=(EditText)findViewById(R.id.mobleET);
        passwordET=(EditText)findViewById(R.id.passwrdET);
        signIn=(TextView)findViewById(R.id.signIBn);
        btnSignUp=(Button) findViewById(R.id.sigupBtn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(Register.this,Login.class);
               startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String fName=fullNameET.getText().toString();
                String mobile=mobileET.getText().toString();
                String pass=passwordET.getText().toString();

                if(fName.isEmpty()){
                 fullNameET.setError("Please enter Full Name");
                }else if (mobile.isEmpty()){
                    mobileET.setError("Please enter Number Phone");
                }else if(pass.isEmpty()){
                    passwordET.setError("Please enter Password");
                }else if(pass.equals("123456789")){

                    if (!mobileET.getText().toString().trim().isEmpty()){
                        if ((mobileET.getText().toString().trim()).length()==9)
                        {



                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+218" + mobileET.getText().toString(),
                                    60,
                                    TimeUnit.SECONDS,
                                    Register.this,
                                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                        @Override
                                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                        }

                                        @Override
                                        public void onVerificationFailed(@NonNull FirebaseException e) {


                                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {



                                            Intent intent=new Intent(getApplicationContext(),OTPverification.class);
                                            intent.putExtra("mobile",mobileET.getText().toString());
                                            intent.putExtra("backendotp",backendotp);
                                            intent.putExtra("fullName",fullNameET.getText().toString());
                                            intent.putExtra("Activity","Register");
                                            startActivity(intent);

                                        }
                                    }

                            );


                        }else {
                            Toast.makeText(Register.this, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        Toast.makeText(Register.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    passwordET.setError("Please enter Password");
                }
            }
        });
 }

}