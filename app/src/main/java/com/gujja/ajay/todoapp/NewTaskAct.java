package com.gujja.ajay.todoapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskAct extends AppCompatActivity {

    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes;
    Button btnSaveTask, btnCancel;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titledoes = findViewById(R.id.titleDoes);
        titlepage = findViewById(R.id.titlepage);
        addtitle = findViewById(R.id.addtitle);

        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);
        descdoes = findViewById(R.id.descDoes);
        datedoes = findViewById(R.id.dateDoes);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert data to database
                reference = FirebaseDatabase.getInstance().getReference().child("BoxDoes").child("Does" + doesNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                        startActivity(a);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        finish();
                        

                    }
                });

            }
        });


        //import fonts
        Typeface Mlight = Typeface.createFromAsset(getAssets(), "fonts/ml.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/mm.ttf");

        //customize fonts
        titlepage.setTypeface(MMedium);

        addtitle.setTypeface(Mlight);
        titledoes.setTypeface(MMedium);

        adddesc.setTypeface(Mlight);
        descdoes.setTypeface(MMedium);

        adddate.setTypeface(Mlight);
        datedoes.setTypeface(MMedium);

        btnSaveTask.setTypeface(MMedium);
        btnCancel.setTypeface(Mlight);


    }
}
