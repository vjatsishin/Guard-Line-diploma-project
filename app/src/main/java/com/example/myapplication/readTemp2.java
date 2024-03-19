package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class readTemp2 extends AppCompatActivity {


    private ListView listView1;
    private ArrayAdapter <String> adapter1;
    private List<String> listData1;
    public DatabaseReference db;
    private String USER_KEY1 = "Users";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read2_layout);
        init();
        getDataFromDB();

    }
    private void init(){
        listView1 = findViewById(R.id.listView1);
        listData1 = new ArrayList<>();
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData1);
        listView1.setAdapter(adapter1);
        db = FirebaseDatabase.getInstance().getReference(USER_KEY1);
    }

    private void getDataFromDB(){

        ValueEventListener vListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    assert user != null;
                    listData1.add(user.getTemperature());
                }
                adapter1.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        db.addValueEventListener(vListener1);

    }

}