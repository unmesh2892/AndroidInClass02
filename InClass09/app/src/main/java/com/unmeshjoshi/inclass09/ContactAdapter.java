package com.unmeshjoshi.inclass09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contacts> {

    FirebaseDatabase firebaseDatabase ;
    DatabaseReference databaseReference;
    String uid;
    private FirebaseAuth auth;



    public ContactAdapter(@NonNull Context context, ArrayList<Contacts> resources) {
        super(context, 0,resources);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Contacts contacts = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.contactlistadapter, parent, false);

        TextView textViewName = convertView.findViewById(R.id.ContactName);
        TextView textViewPhone = convertView.findViewById(R.id.ContactPhone);
        TextView textViewEmail = convertView.findViewById(R.id.ContactEmail);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        ImageView deleteButton = convertView.findViewById(R.id.ContactDelete);

        textViewName.setText(contacts.name);
        textViewPhone.setText(contacts.phone);
        textViewEmail.setText(contacts.email);

        if (contacts.imageUrl != null && !contacts.imageUrl.isEmpty()) {
            Picasso.get().load(contacts.imageUrl).into(imageView);

        }else{
            Picasso.get().load("https://www.gettyimages.ie/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg").into(imageView);

        }

        auth = FirebaseAuth.getInstance();
        uid =  auth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(uid).child("contacts");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(contacts.key).removeValue();
            }
        });
     return convertView;
    }
}
