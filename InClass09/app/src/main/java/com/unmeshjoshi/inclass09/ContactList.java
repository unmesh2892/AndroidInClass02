package com.unmeshjoshi.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    ArrayList<Contacts> contactsArrayList = new ArrayList<>();
    ImageView imageView3;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;
    DatabaseReference dbRef;
    String uid;
    private FirebaseAuth auth;
    ImageView buttonDelete;
    ContactAdapter contactAdapter;
    TextView contactName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
       final ListView listView = findViewById(R.id.listView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        contactName = findViewById(R.id.ContactName);
       // dbRef = firebaseDatabase.getReference().child("users").child(uid);

        //dbRef.addValueEventListener()

        uid =  auth.getCurrentUser().getUid();


        databaseReference = firebaseDatabase.getReference().child("users").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactName.setText(dataSnapshot.child("name").getValue().toString());

                contactsArrayList.clear();
               for(DataSnapshot child:dataSnapshot.child("contacts").getChildren()){
                   Contacts con = child.getValue(Contacts.class);
                   con.setName(con.name);
                   con.setEmail(con.email);
                   con.setPhone(con.phone);
                   con.setKey(con.key);
                   con.setImageUrl(con.imageUrl);
                   contactsArrayList.add(con);
               }
                contactAdapter = new ContactAdapter(ContactList.this,contactsArrayList);
                listView.setAdapter(contactAdapter);
          }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        TextView name = findViewById(R.id.ContactName);
        buttonDelete =  findViewById(R.id.ContactDelete);

        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactList.this,AddNewContact.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ContactList.this,MainActivity.class);
                startActivity(intent);
                finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
