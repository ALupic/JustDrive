package com.example.justdrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    private FirebaseAuth mAuth;
    public int currentRole;

    DatabaseReference databaseEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        databaseEntries = FirebaseDatabase.getInstance().getReference();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        if(mAuth.getCurrentUser() != null){
            logIn();
        }
    }

    public void goClicked(View v){
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            onStart();


                            /*if(mAuth.getCurrentUser()){
                                currentRole = 1;
                            }else{
                                currentRole = 0;
                            }*/
                            logIn();
                        } else {
                            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                // Add to database
                                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("email").setValue(emailEditText.getText().toString());
                                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).child("role").setValue(1);
                                                currentRole = 1;
                                                logIn();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                                String exc = task.getException().toString();
                                                Log.i(">>>>>>>", exc);
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void logIn(){
        if(currentRole == 1){
            Intent riderIntent = new Intent(this, RiderActivity.class);
            String currentUser = emailEditText.getText().toString();
            riderIntent.putExtra("currentUser", currentUser);
            startActivity(riderIntent);
        }else{
            Intent driverIntent = new Intent(this, DriverActivity.class);
            String currentUser = emailEditText.getText().toString();
            driverIntent.putExtra("currentUser", currentUser);
            startActivity(driverIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEntries.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot entrySnapshot: snapshot.getChildren()){
                    User user = entrySnapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
