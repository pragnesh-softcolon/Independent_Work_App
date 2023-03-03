package com.example.independentworkapp.Activity;

import static com.google.android.material.datepicker.MaterialDatePicker.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.independentworkapp.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class signup_Form_2 extends AppCompatActivity
{
    Button btn_next,UploadImage;
    TextInputEditText dob,PhoneNumber;
    TextInputLayout ed_DOB,ed_phone,ed_Location;
    AutoCompleteTextView Location;
    TextView error_image;
    String imageName="";
    String picturePath;
    int columnIndex;
    Uri selectedImage;
    Bitmap bitmap;
    int RESULT_LOAD_IMAGE = 1;
    String location1;
    String[] City ={"Ahmedabad","Amreli","Anand","Aravalli","Banaskantha (Palanpur)","Bharuch","Bhavnagar","Botad",
            "Chhota Udepur","Dahod","Dangs (Ahwa)","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh",
            "Kachchh","Kheda (Nadiad)","Mahisagar","Mehsana","Morbi","Narmada (Rajpipla)","Navsari","Panchmahal (Godhra)","Patan",
            "Porbandar","Rajkot","Sabarkantha (Himmatnagar)","Surat","Surendranagar","Tapi (Vyara)","Vadodara","Valsad"};
    String Phone,BirthDate,Place;
    int age;
    Boolean isImageSelected= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form2);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        views();

        Builder materialDateBuilder = Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT BIRTH DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,City);
        Location.setThreshold(1);
        Location.setAdapter(adapter);
        dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
//                        dob.setText(materialDatePicker.getHeaderText());
                        String Rdate=materialDatePicker.getHeaderText();
                        java.util.Date date1=new Date(Rdate);
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
                        dob.setText(Date);
//                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
//                        LocalDate birthdate = LocalDate.parse(materialDatePicker.getHeaderText(), inputFormatter);
//                        LocalDate today = LocalDate.now();
//                        Period age1 = Period.between(birthdate, today);
//                        Log.e("anyTextage",""+age1.getYears());
                    }
                });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location1=Location.getText().toString();
                for (int i=0;i<City.length;i++)
                {
                    if (location1.equalsIgnoreCase(City[i]))
                    {
                        Place=City[i];
                        Location.setText(Place);
                        break;
                    }
                }
                Phone=PhoneNumber.getText().toString().trim();
                BirthDate=dob.getText().toString().trim();
                if (Phone.isEmpty())
                {
                    ed_phone.setError("Required");
                    ed_phone.requestFocus();
                }
                else if(Phone.length()!=10)
                {
                    Toast.makeText(signup_Form_2.this, ""+Phone.length(), Toast.LENGTH_SHORT).show();
                    ed_phone.setError("Invalid Phone Number");
                    ed_phone.requestFocus();
                }
                else if(BirthDate.isEmpty())
                {
                    ed_Location.setError(null);
                    ed_DOB.setError("Required");
                }
                else if (!location1.equalsIgnoreCase(Place))
                {
                    ed_Location.setError(null);
                    ed_DOB.setError(null);
                    ed_Location.setError("Required");
                    ed_Location.requestFocus();
                }

                else if(!isImageSelected)
                {
                    ed_Location.setError(null);
                    ed_DOB.setError(null);
                    ed_Location.setError(null);
                    error_image.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_Location.setError(null);
                    ed_DOB.setError(null);
                    ed_Location.setError(null);
                    error_image.setVisibility(View.GONE);
                    Intent next = new Intent(signup_Form_2.this, signup_Form_3.class);
                    next.putExtra("FirstName",  getIntent().getExtras().getString("FirstName"));
                    next.putExtra("LastName",  getIntent().getExtras().getString("LastName"));
                    next.putExtra("Gender",  getIntent().getExtras().getString("Gender"));
                    next.putExtra("Phone", Phone);
                    next.putExtra("BirthDate", BirthDate);
                    next.putExtra("Place", Place);
                    next.putExtra("imagePath", picturePath);
                    startActivity(next);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
    }

    private void views() {
        ed_DOB=findViewById(R.id.ed_DOB);
        ed_Location=findViewById(R.id.ed_Location);
        ed_phone=findViewById(R.id.ed_phone);
        btn_next=findViewById(R.id.btn_next);
        dob=findViewById(R.id.dob);
        UploadImage=findViewById(R.id.UploadImage);
        PhoneNumber=findViewById(R.id.PhoneNumber);
        Location=findViewById(R.id.Location);
        error_image=findViewById(R.id.error_image);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                String extension =picturePath.substring(picturePath.lastIndexOf(".") + 1);
                Log.e("anyText","Extension is : "+extension);
                if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))
                {
                    bitmap= BitmapFactory.decodeFile(picturePath);
//                    img.setImageBitmap(bitmap);
//                    get image name
                    File f = new File(picturePath);
                    imageName = f.getName();
                    UploadImage.setText(imageName);
                    isImageSelected=true;
                }
                else
                {
                    Toast.makeText(this, "Please select .JPG,.JPEG or .PNG formate", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
//        Intent form1=new Intent(signup_Form_2.this,signup_Form_1.class);
//        startActivity(form1);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}