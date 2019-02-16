package com.gujja.ajay.todoapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView titlepage, subtitlepage, endgame;
    Button btnAddNew;

    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        endgame = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);


        //import font
        Typeface Mlight = Typeface.createFromAsset(getAssets(), "fonts/ml.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/mm.ttf");

        // customize font
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(Mlight);
        endgame.setTypeface(Mlight);
        btnAddNew.setTypeface(Mlight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this,NewTaskAct.class);
                startActivity(a);
                finish();
            }
        });





        //working with data
        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDoes>();

        //get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("BoxDoes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //set code to retrive data and replace layout
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
                    list.add(p);

                }
                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //set code to show error
                Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
                finish();

            }
        });



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
