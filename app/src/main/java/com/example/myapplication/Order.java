package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Order extends AppCompatActivity {

    private static final String TAG = "Order";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

     private Spinner spinner;

    //Insert
    EditText desc,qty;
    TextView date;
    Button add;
    DatabaseReference reff;
    OrderSchema orderSchema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        spinner = findViewById(R.id.spinner);
        mDisplayDate = (TextView) findViewById(R.id.date);
        desc = findViewById(R.id.desc);
        qty = findViewById(R.id.qty);
        date = findViewById(R.id.date);
        add = (Button) findViewById(R.id.add);
        orderSchema = new OrderSchema();
        reff = FirebaseDatabase.getInstance().getReference().child("Order");

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Order.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });




        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };





        List<String> categories = new ArrayList<>();
        categories.add(0,"Choose Type");
        categories.add("Pipes");
        categories.add("Cements");
        categories.add("Rods");
        categories.add("Woods");



        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(parent.getItemAtPosition(position).equals("Choose District")){

                }
                else{
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), "Selected: "+item, Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                orderSchema.setDesc(desc.getText().toString().trim());
                Float hit= Float.parseFloat((qty.getText().toString().trim()));
                orderSchema.setQty(hit);
                orderSchema.setDate(date.getText().toString().trim());

                reff.child("Items").setValue(orderSchema);
                Toast.makeText(Order.this,"data inserted successfully", Toast.LENGTH_LONG).show();
            }
        });


    }
}
