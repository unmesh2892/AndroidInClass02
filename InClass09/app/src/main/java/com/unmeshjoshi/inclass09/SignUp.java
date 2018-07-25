package com.unmeshjoshi.inclass09;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button signUp,cancel;
    private EditText firstName,lastName,username,password;
    private int GALLERY = 1, CAMERA = 2;
    ImageView imageView;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        auth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.buttonSignUp);
        cancel = findViewById(R.id.buttonCancel);

        firstName = findViewById(R.id.FrstNameCon);
        lastName = findViewById(R.id.PhoneCon);
        username = findViewById(R.id.UserNameCon);
        password = findViewById(R.id.editTextPassword);
        imageView = findViewById(R.id.imageViewCon);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pass = password.getText().toString();
                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                final String fullName = firstName1+" " +lastName1;


                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(SignUp.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            mReference = FirebaseDatabase.getInstance().getReference();

                           String uid =   auth.getUid();
                            DatabaseReference mRef = mReference.child("users").child(uid).child("name");
                                    mRef.setValue(fullName);

                            startActivity(new Intent(SignUp.this, ContactList.class));
                            finish();
                        }
                    }
                });


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
             /*   Uri contentURI = data.getData();
                //  File filepath = data.getData();
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                String path = "images/" + UUID.randomUUID().toString();
                final StorageReference ref = storageReference.child(path);
                ref.putFile(contentURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("image", "uploaded from gallery");
                        imageURL =  taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        // storageReference.child(ref.toString()).getDownloadUrl().getResult();

                    }
                });

*/
            }

        } else if (requestCode == CAMERA && resultCode == RESULT_OK) {

            if (data != null) {
/*

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(AddExpense.this.getContentResolver(), thumbnail, "Title", null);
                Uri contentURI =   Uri.parse(path);

        //  File filepath = data.getData();
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
                String camPath = "images/" + UUID.randomUUID().toString();
                final StorageReference ref = storageReference.child("images/").child(contentURI.getLastPathSegment());
                ref.putFile(contentURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("image", "uploaded from camera");
                        Log.d("geturl",""+taskSnapshot.getMetadata().getReference().getDownloadUrl());
                        imageURL=   taskSnapshot.getMetadata().getReference().getDownloadUrl();




                    }
                });

*/
            }
        }
    }

}
