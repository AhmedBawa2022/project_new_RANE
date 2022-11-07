package com.itzliten.login;

import static com.itzliten.login.R.*;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
private EditText mobileET ;
private TextView signUp;
private Button  btnLogin;
private FirebaseAuth firebaseAuth;
@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
    mobileET=(EditText)findViewById(id.mobileET);
        signUp=(TextView)findViewById(id.txtSignUp);
        btnLogin=(Button) findViewById(id.btnLogin);
        firebaseAuth=FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
      btnLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String mobilee=mobileET.getText().toString();

              if(mobilee.isEmpty()){
                  mobileET.setError("Please enter Full Name");
              }else{
                  if (!mobileET.getText().toString().trim().isEmpty()){
                      if ((mobileET.getText().toString().trim()).length()==9)
                      {



                          PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                  "+218" + mobileET.getText().toString(),
                                  60,
                                  TimeUnit.SECONDS,
                                  Login.this,
                                  new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                      @Override
                                      public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                      }

                                      @Override
                                      public void onVerificationFailed(@NonNull FirebaseException e) {


                                          Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                      }

                                      @Override
                                      public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {



                                          Intent intent=new Intent(getApplicationContext(),OTPverification.class);
                                          intent.putExtra("mobile",mobileET.getText().toString());
                                          intent.putExtra("backendotp",backendotp);
                                          intent.putExtra("Activity","Login");
                                          startActivity(intent);

                                      }
                                  }

                          );


                      }else {
                          Toast.makeText(Login.this, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                      }

                  }else
                  {
                      Toast.makeText(Login.this, "Enter Mobile number", Toast.LENGTH_SHORT).show();
                  }

              }



              }

      });

    }

}
