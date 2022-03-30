package com.example.retrofitimageviewuploadinternet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.retrofitimageviewuploadinternet.API.Api;
import com.example.retrofitimageviewuploadinternet.API.Const;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final int MY_REQUEST_CODE = 10;
//    private EditText edt_username, edt_password;
//    private TextView tv_username, tv_password;
    private Button btn_select_image, btn_upload_image;
    private ImageView img_from_gallery, img_from_api;
    private Uri mUri;
    private ProgressDialog progressDialog;
    Context context = MainActivity.this;
    Bitmap bitmap;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        Log.d("uri", String.valueOf(uri));
                        Log.e("uri", String.valueOf(uri));
                        mUri = uri;
                        Log.d("mUri", String.valueOf(mUri));

                        btn_upload_image = findViewById(R.id.btn_upload_image);
                        btn_upload_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mUri != null) {
                                    Log.d("button", String.valueOf(mUri));
                                    callApiRegisterAccount();
//                                    uploadImage();
                                }
                            }
                        });
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img_from_gallery.setImageBitmap(bitmap);
                            Log.d("bitmap", String.valueOf(bitmap));
                            String imgCode = getBitMap(bitmap);
                            Log.d("imgCode", String.valueOf(imgCode));
                            SharedPreferences pref = getSharedPreferences("PREF", MODE_PRIVATE);
                            pref.edit().putString("data", imgCode).apply();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Pls wait...");

//        Log.d("button", String.valueOf(mUri));

        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRequestPermission();
            }
        });


    }

    private void initView() {
//        edt_username = findViewById(R.id.edt_username);
//        edt_password = findViewById(R.id.edt_password);
//        tv_username = findViewById(R.id.tv_username);
//        tv_password = findViewById(R.id.tv_password);
        btn_select_image = findViewById(R.id.btn_select_image);
        btn_upload_image = findViewById(R.id.btn_upload_image);
        img_from_gallery = findViewById(R.id.img_from_gallery);
        img_from_api = findViewById(R.id.img_from_api);

    }

    private void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() { //clickListeners
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    public String getBitMap(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

//    private void uploadImage() {
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,75, byteArrayOutputStream);
//        byte[] imageInByte = byteArrayOutputStream.toByteArray();
//        String encodedImage =  Base64.encodeToString(imageInByte,Base64.DEFAULT);
//
//        Call<User> call = RetroClient.getInstance().getApi().uploadImage(encodedImage);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Toast.makeText(MainActivity.this, response.body().getRemarks(), Toast.LENGTH_SHORT).show();
//
//                if(response.body().isStatus()){
//
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void callApiRegisterAccount() {

        if (mUri == null) {
            Toast.makeText(MainActivity.this, "Call Api Failed", Toast.LENGTH_SHORT).show();
        } else {
            String imgCode = getBitMap(bitmap);
            progressDialog.show();

//            String strUsername = edt_username.getText().toString().trim();
//            String strPassword = edt_password.getText().toString().trim();
//            RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), strUsername);
//            RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);


//            String strRealPath = RealPathUtil.getRealPathFromURI_API11to18(context, mUri);
//
//            //truyen duong dan thuc de tao ra file call api
//            File file = new File(strRealPath);
//
//            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData(Const.KEY_AVT, file.getName(), requestBodyAvt);


            Api.api.uploadImage("Client-ID fffeea2f30929b1", imgCode).enqueue(new Callback<Example>() {
                //requestBodyUsername, requestBodyPassword, multipartBodyAvt
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    progressDialog.dismiss();
                    Example example = response.body();
                    if (example != null) {
//                        tv_username.setText(user.get(0).getUsername());
//                        tv_password.setText(user.get(0).getPassword());
                        Glide.with(MainActivity.this).load(example.getData().getLink()).into(img_from_api);
                        Toast.makeText(MainActivity.this, "Call Api Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Call Api Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Call Api Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
