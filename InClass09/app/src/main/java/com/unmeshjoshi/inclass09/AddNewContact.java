package com.unmeshjoshi.inclass09;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class AddNewContact extends AppCompatActivity {

    EditText name,phone,email;
    Button save;
    ArrayList<Contacts> contactsLS = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private int GALLERY = 1, CAMERA = 2;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String uid,urlId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        auth = FirebaseAuth.getInstance();

        name = findViewById(R.id.FrstNameCon);
        phone=findViewById(R.id.PhoneCon);
        email = findViewById(R.id.UserNameCon);
        save = findViewById(R.id.buttonSave);

      uid=   auth.getCurrentUser().getUid();



      findViewById(R.id.imageViewCon).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showPictureDialog();
          }
      });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference =  FirebaseDatabase.getInstance().getReference();;
                Contacts contacts = new Contacts();

                contacts.name = name.getText().toString();
                contacts.email = email.getText().toString();
                contacts.phone = email.getText().toString();
                contacts.imageUrl = urlId;
                    //contactsLS.add(contacts);

                    DatabaseReference mReference= databaseReference.child("users").child(uid).child("contacts");
                    String key = mReference.push().getKey();
                    contacts.key=key;
                    mReference.child(key).setValue(contacts);
                        //mReference.setValue(contacts);
                Intent intent = new Intent(AddNewContact.this,ContactList.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                firebaseStorage = FirebaseStorage.getInstance();
                storageReference = firebaseStorage.getReference();
                String path = "images/" + UUID.randomUUID().toString();
                final StorageReference ref = storageReference.child(path);
                ref.putFile(contentURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("image", "uploaded from gallery");
                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                urlId = task.getResult().toString();
                            }
                        });
                       // urlId =  taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    }
                });


            }

        } else if (requestCode == CAMERA && resultCode == RESULT_OK) {

            if (data != null) {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(AddNewContact.this.getContentResolver(), thumbnail, "Title", null);
                Uri contentURI =   Uri.parse(path);






                //  File filepath = data.getData();
                firebaseStorage = FirebaseStorage.getInstance();
                storageReference = firebaseStorage.getReference();
                String camPath = "images/" + UUID.randomUUID().toString();
                final StorageReference ref = storageReference.child("images/").child(contentURI.getLastPathSegment());
                ref.putFile(contentURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("image", "uploaded from camera");
                        Log.d("geturl",""+taskSnapshot.getMetadata().getReference().getDownloadUrl());

                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                urlId = task.getResult().toString();
                            }
                        });
                       // urlId=   taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();


                    }
                });



            }
        }
    }
}
