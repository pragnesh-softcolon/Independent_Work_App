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
import java.time.format.DateTimeFormatter;

public class signup_Form_2 extends AppCompatActivity
{
    Button btn_next,UploadImage;
    TextInputEditText dob,PhoneNumber;
    AutoCompleteTextView Location;
    String imageName="";
    String picturePath;
    int columnIndex;
    Uri selectedImage;
    Bitmap bitmap;
    int RESULT_LOAD_IMAGE = 1;
    String location1,location2;
    String[] City ={"Ahmedabad","Amreli","Anand","Aravalli","Banaskantha (Palanpur)","Bharuch","Bhavnagar","Botad",
            "Chhota Udepur","Dahod","Dangs (Ahwa)","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh",
            "Kachchh","Kheda (Nadiad)","Mahisagar","Mehsana","Morbi","Narmada (Rajpipla)","Navsari","Panchmahal (Godhra)","Patan",
            "Porbandar","Rajkot","Sabarkantha (Himmatnagar)","Surat","Surendranagar","Tapi (Vyara)","Vadodara","Valsad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form2);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        btn_next=findViewById(R.id.btn_next);
        dob=findViewById(R.id.dob);
        UploadImage=findViewById(R.id.UploadImage);
        PhoneNumber=findViewById(R.id.PhoneNumber);
        Location=findViewById(R.id.Location);
        Builder materialDateBuilder = Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT BIRTH DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
//        dob.setShowSoftInputOnFocus(false);just another virtual accelator
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,City);
        //Getting the instance of AutoCompleteTextView
        Location.setThreshold(1);//will start working from first character
        Location.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
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

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        dob.setText(materialDatePicker.getHeaderText());
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
                        LocalDate birthdate = LocalDate.parse(materialDatePicker.getHeaderText(), inputFormatter);


                        // Calculate age based on birthdate
                        LocalDate today = LocalDate.now();
                        Period age = Period.between(birthdate, today);
                        Log.e("anyTextage",""+age.getYears());
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
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
                        location2=City[i];
                        break;
                    }
                }
//                if (location1.equalsIgnoreCase(location2))
//                {
                    Intent next = new Intent(signup_Form_2.this, signup_Form_3.class);
                    startActivity(next);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                }
//                else
//                {
//                    Toast.makeText(signup_Form_2.this, "Please Select Valid Location", Toast.LENGTH_SHORT).show();
//                }
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
        Intent form1=new Intent(signup_Form_2.this,signup_Form_1.class);
        startActivity(form1);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}