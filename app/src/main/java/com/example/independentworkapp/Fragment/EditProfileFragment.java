package com.example.independentworkapp.Fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.independentworkapp.Network.Apis;
import com.example.independentworkapp.Network.SharedPrefs;
import com.example.independentworkapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class EditProfileFragment extends Fragment
{
    View view;
    int RESULT_LOAD_IMAGE = 1;
    String imageName="";
    String picturePath;
    int columnIndex;
    Uri selectedImage;
    Bitmap bitmap;
    Boolean image=false;
    Dialog dialog;

    public static final int PERMISSION_CODE=1234;
    public static final int CAP_CODE=1001;
    Uri image_uri;
    ImageView imageView,boy,girl;
    TextInputLayout ed_Fname,ed_Lname,ed_gendar,ed_DOB,ed_Phone,ed_Location;
    TextInputEditText Fname,Lname,DOB,Phone;
    AutoCompleteTextView Location;
    String gendar;
//    String[] array_gendar ={"MALE","FEMALE"};
    String[] array_Location ={"Ahmedabad","Amreli","Anand","Aravalli","Banaskantha (Palanpur)","Bharuch","Bhavnagar","Botad",
            "Chhota Udepur","Dahod","Dangs (Ahwa)","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh",
            "Kachchh","Kheda (Nadiad)","Mahisagar","Mehsana","Morbi","Narmada (Rajpipla)","Navsari","Panchmahal (Godhra)","Patan",
            "Porbandar","Rajkot","Sabarkantha (Himmatnagar)","Surat","Surendranagar","Tapi (Vyara)","Vadodara","Valsad"};

    Button select,select2,btn_update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        view=inflater.inflate(R.layout.fragment_edit_profile, container, false);
        views();
        setHasOptionsMenu(true);
//        ArrayAdapter<String> adapter_gendar = new ArrayAdapter<String>
//                (getContext(),android.R.layout.select_dialog_item,array_gendar);
//        gendar.setThreshold(1);
//        gendar.setAdapter(adapter_gendar);
        ArrayAdapter<String> adapter_location = new ArrayAdapter<String>
                (getContext(),android.R.layout.select_dialog_item,array_Location);
        Location.setThreshold(1);
        Location.setAdapter(adapter_location);
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT BIRTH DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        DOB.setOnClickListener(view -> materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    String Rdate=materialDatePicker.getHeaderText();
                    Date date1=new Date(Rdate);
                    ZoneId z = ZoneId.of("Asia/Kolkata");
                    String fdate=""+date1.toInstant().atZone(z);
                    String reach_date = fdate.substring(0,10);
                    String st="";
                    ArrayList list =new ArrayList();
                    String[] strSplit = reach_date.split("-");
                    for (int j=0;j<strSplit.length;j++)
                    {
                        st=strSplit[j];
                        list.add(st);
                    }
                    String Date=list.get(2)+"/"+list.get(1)+"/"+list.get(0);
                    DOB.setText(Date);
                });
        boy.setOnClickListener(view -> {
            gendar="MALE";
            boy.setBackgroundResource(R.drawable.gendar_select);
            girl.setBackgroundResource(R.drawable.gendar_deselect);
        });
        girl.setOnClickListener(view -> {
            gendar="FEMALE";
            boy.setBackgroundResource(R.drawable.gendar_deselect);
            girl.setBackgroundResource(R.drawable.gendar_select);
        });
//        btn_update.setOnClickListener(view -> {
//
//            ed_Fname.setError(null);
//            ed_Lname.setError(null);
//            ed_DOB.setError(null);
//            ed_Location.setError(null);
//           if (Fname.getText().toString().trim().isEmpty()){
//               Fname.requestFocus();
//                ed_Fname.setError("Enter First Name");
//            }
//            else if(Lname.getText().toString().trim().isEmpty()){
//                Lname.requestFocus();
//                ed_Lname.setError("Enter Last Name");
//            }
//            else if (DOB.getText().toString().trim().isEmpty()){
//                ed_DOB.setError("Enter Date Of Birth");
//            }
//            else if (gendar.isEmpty()){
//               Toast.makeText(getContext(),"Select Gender",Toast.LENGTH_SHORT).show();
//            }
//            else if (Location.getText().toString().trim().isEmpty()){
//                Location.requestFocus();
//                ed_Location.setError("Select Location");
//            }
//           else if (image){
//                updateProfileWithImage();
//            }
//            else {
//                updateProfileWithoutImage();
//            }
//        });
        select.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i,RESULT_LOAD_IMAGE);
        });
        select2.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)  == PackageManager.PERMISSION_DENIED
                        ||
                        getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_DENIED
                )
                {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission,PERMISSION_CODE);
                }
                else
                {
                    openCamera();
                }
            }
            else
            {
                openCamera();
            }
        });
        setData();
        // Inflate the layout for this fragment
        return view;
    }

    private void updateProfileWithoutImage() {
        dialog.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT,
                Apis.UPDATE_PROFILE+
                        "?firstName="+Fname.getText().toString().trim()+
                        "&lastName="+Lname.getText().toString().trim()+
                        "&gender="+gendar.trim()+
                        "&dateOfBirth="+DOB.getText().toString().trim()+
                        "&Location="+Location.getText().toString().trim(),
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            new SharedPrefs(getContext()).setUserId(jsonObject.getString("_id"));
                            new SharedPrefs(getContext()).setFirstName(jsonObject.getString("firstName"));
                            new SharedPrefs(getContext()).setLastName(jsonObject.getString("lastName"));
                            new SharedPrefs(getContext()).setGender(jsonObject.getString("gender"));
                            new SharedPrefs(getContext()).setDateOfBirth(jsonObject.getString("dateOfBirth"));
                            new SharedPrefs(getContext()).setImage(jsonObject.getString("image"));
                            new SharedPrefs(getContext()).setPhone(jsonObject.getString("phone"));
                            new SharedPrefs(getContext()).setLocation(jsonObject.getString("Location"));
                            Log.e("anyText","obj is "+jsonObject);
                            setData();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            Log.e("anyText","catch : "+e);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_LONG).show();
                        Log.e("anyText",""+error);
                        dialog.dismiss();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new SharedPrefs(getContext()).getUserToken());
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);

    }
    private void updateProfileWithImage() {
        dialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT,
                Apis.UPDATE_PROFILE+
                        "?firstName="+Fname.getText().toString().trim()+
                        "&lastName="+Lname.getText().toString().trim()+
                        "&gender="+gendar.trim()+
                        "&dateOfBirth="+DOB.getText().toString().trim()+
                        "&Location="+Location.getText().toString().trim(),
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            new SharedPrefs(getContext()).setUserId(jsonObject.getString("_id"));
                            new SharedPrefs(getContext()).setFirstName(jsonObject.getString("firstName"));
                            new SharedPrefs(getContext()).setLastName(jsonObject.getString("lastName"));
                            new SharedPrefs(getContext()).setGender(jsonObject.getString("gender"));
                            new SharedPrefs(getContext()).setDateOfBirth(jsonObject.getString("dateOfBirth"));
                            new SharedPrefs(getContext()).setImage(jsonObject.getString("image"));
                            new SharedPrefs(getContext()).setPhone(jsonObject.getString("phone"));
                            new SharedPrefs(getContext()).setLocation(jsonObject.getString("Location"));
                            Log.e("anyText","obj is "+jsonObject);
                            setData();
                            dialog.dismiss();
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                            Log.e("anyText","catch : "+e);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), ""+error, Toast.LENGTH_LONG).show();
                        Log.e("anyText",""+error);
                        dialog.dismiss();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new SharedPrefs(getContext()).getUserToken());
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(imageName, imageBytes));
                Log.e("anyText",""+params);
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if (grantResults.length > 0    && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(getContext(), "Permission not here", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor =getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                String extension =picturePath.substring(picturePath.lastIndexOf(".") + 1);
                Log.e("anyText","Extension is : "+extension);
                if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                    {
                        bitmap=getResizedBitmap( ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), selectedImage)),1600,2400);
                    }
                    else
                    {
                        bitmap=  getResizedBitmap( MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage),1600,2400);
                    }
//                    bitmap= BitmapFactory.decodeFile(picturePath);
                    imageView.setImageBitmap(bitmap);
//                    get image name
                    File f = new File(picturePath);
                    imageName = f.getName();
                    image=true;
//                    tv.setText(imageName);
                }
                else
                {
                    Toast.makeText(getContext(), "Please select .JPG,.JPEG or .PNG formate", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (resultCode == RESULT_OK && requestCode==1001)
        {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try
                {
//                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    bitmap= getResizedBitmap( ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), image_uri)),1600,2400);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
//                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                    bitmap=  getResizedBitmap( MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), image_uri),1600,2400);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            imageView.setImageBitmap(bitmap);
            imageName = String.valueOf(System.currentTimeMillis())+".jpg";
            image=true;
        }
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private void setData() {
        Fname.setText(new SharedPrefs(getContext()).getFirstName());
        Lname.setText(new SharedPrefs(getContext()).getLasttName());
        gendar=(new SharedPrefs(getContext()).getGender());
        if (new SharedPrefs(getContext()).getGender().equalsIgnoreCase("male")){
            boy.setBackgroundResource(R.drawable.gendar_select);
            girl.setBackgroundResource(R.drawable.gendar_deselect);
        }
        if (new SharedPrefs(getContext()).getGender().equalsIgnoreCase("female")){
            boy.setBackgroundResource(R.drawable.gendar_deselect);
            girl.setBackgroundResource(R.drawable.gendar_select);
        }
        DOB.setText(new SharedPrefs(getContext()).getDateOfBirth());
        Phone.setText(new SharedPrefs(getContext()).getPhone());
        Location.setText(new SharedPrefs(getContext()).getLocation());
        Log.e("anyText",Apis.IMAGE+new SharedPrefs(getContext()).getImage());
        try {
            String url = Apis.IMAGE+new SharedPrefs(getContext()).getImage();
            String fixedUrl = url.replaceAll(" ", "%20");
            Glide.with(getContext()).load(fixedUrl).into(imageView);
            Log.e("anyText",fixedUrl.toString());
        }
        catch (Exception e){
            Log.e("anyText",""+e);
        }
    }
    private void openCamera()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"New Image from camera");
        image_uri=getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(i,CAP_CODE);
    }
    private void views() {
        select = view.findViewById(R.id.select);
        select2 = view.findViewById(R.id.select2);
        imageView = view.findViewById(R.id.image);
        ed_Fname=view.findViewById(R.id.ed_Fname);
        ed_Lname=view.findViewById(R.id.ed_Lname);
        ed_DOB=view.findViewById(R.id.ed_DOB);
        ed_Phone=view.findViewById(R.id.ed_Phone);
        ed_Location=view.findViewById(R.id.ed_Location);
        Fname=view.findViewById(R.id.first_name);
        Lname=view.findViewById(R.id.last_name);
        DOB=view.findViewById(R.id.DOB);
        Phone=view.findViewById(R.id.phone);
        Location=view.findViewById(R.id.Location);
        btn_update=view.findViewById(R.id.btn_update_profile);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        boy=view.findViewById(R.id.boy);
        girl=view.findViewById(R.id.girl);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.update, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.update)
        {

            ed_Fname.setError(null);
            ed_Lname.setError(null);
            ed_DOB.setError(null);
            ed_Location.setError(null);
            if (Fname.getText().toString().trim().isEmpty()){
                Fname.requestFocus();
                ed_Fname.setError("Enter First Name");
            }
            else if(Lname.getText().toString().trim().isEmpty()){
                Lname.requestFocus();
                ed_Lname.setError("Enter Last Name");
            }
            else if (DOB.getText().toString().trim().isEmpty()){
                ed_DOB.setError("Enter Date Of Birth");
            }
            else if (gendar.isEmpty()){
                Toast.makeText(getContext(),"Select Gender",Toast.LENGTH_SHORT).show();
            }
            else if (Location.getText().toString().trim().isEmpty()){
                Location.requestFocus();
                ed_Location.setError("Select Location");
            }
            else if (image){
                updateProfileWithImage();
            }
            else {
                updateProfileWithoutImage();
            }
            return true;
        }
        return true;
    }
}