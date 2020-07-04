package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private File tempFile;
    private Boolean isPermission = true;
    private StorageReference mStorageRef;
    private FirebaseStorage storage;
    public String get_category = "";
    private static final int PICK_FROM_ALBUM = 1;
    Bitmap bitmap;
    private static final String TAG = "PostgActivity";
    public String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
    String Title;
    String Url;
    ImageView imageView;
    int Number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button post_apply = (Button) findViewById(R.id.post_apply);
        final EditText add_title = (EditText) findViewById(R.id.add_title);
        final EditText add_content = (EditText) findViewById(R.id.add_content);
        final EditText add_price = (EditText) findViewById(R.id.add_price);
        final Spinner category_spinner = (Spinner) findViewById(R.id.category_spinner);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.itemImage);


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                get_category = category_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final DatabaseReference pnRef = FirebaseDatabase.getInstance().getReference("PostNum");
        pnRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PostNum n = new PostNum();
                        n = dataSnapshot.getValue(PostNum.class);
                        GlobalData.setPostNum(n.postnum++);
                        pnRef.setValue(n);
                        Number = n.postnum;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        findViewById(R.id.itemImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
            }
        });


        post_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String get_id = getEmail().replace('.',',');
                String get_title = add_title.getText().toString();
                String get_content = add_content.getText().toString();
                String get_price =  add_price.getText().toString() + "원";
                SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd hh:mm");
                String get_time = mFormat.format(System.currentTimeMillis());
                String get_Url = Url;

                HashMap result = new HashMap<>();
                result.put("email", GlobalData.Myemail);
                result.put("nickname", GlobalData.MyName);
                result.put("title", get_title);
                result.put("content", get_content);
                result.put("price", get_price);
                result.put("time", get_time);
                result.put("category", get_category);
                result.put("postnum", String.valueOf(GlobalData.getPostNum()));
                result.put("imageUrl", get_Url);



                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("/게시판/물품목록/"+GlobalData.getPostNum()).setValue(result);
                mDatabase.child("/USER/"+ GlobalData.Myemail.replace('.',',')+"/내게시물목록/"+GlobalData.getPostNum()).setValue(result);

                Intent intent = new Intent(PostActivity.this, BoardActivity.class);
                startActivity(intent);

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            imageView.setImageURI(photoUri);
            uploadImage();


        }
    }

    private void setImage() {

        ImageView imageView = findViewById(R.id.itemImage);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);

    }

    public void uploadImage(){

        StorageReference mountainsRef = mStorageRef.child("Postimage").child(getEmail()+ Number + ".jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri imageUrl)
                    {

                        Url = imageUrl.toString();
                    }
                });;
                String photoUri =  Url;
                // Log.d("url", photoUri);

                DatabaseReference myRef = firebaseDatabase.getReference("email");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // String s = dataSnapshot.getValue().toString();
                        //Log.d("Profile", s);
                        if (dataSnapshot != null) {
                            Toast.makeText(PostActivity.this, "사진 업로드가 잘 됐습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(getApplicationContext(),BoardActivity.class);
                    startActivity(intent1);

                    return true;

                case R.id.navigation_category:
                    Intent intent2 = new Intent(getApplicationContext(),CategoryActivity.class);
                    startActivity(intent2);
                    return true;

                case R.id.navigation_boardwrite:
                    Intent intent3 = new Intent(getApplicationContext(),PostActivity.class);
                    startActivity(intent3);
                    return true;

                case R.id.navigation_chatlist:


                    Intent intent4 = new Intent(getApplicationContext(),ListActivity.class);
                    startActivity(intent4);
                    return true;

                case R.id.navigation_myprofile:
                    Intent intent5 = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent5);
                    return true;




            }
            return false;
        }
    };
}
