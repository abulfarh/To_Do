package com.example.todo;

import static com.example.todo.MainActivity.whichActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    EditText title_input, data_input;
    TextView   dateField;
    Button add_button;
    String priorityChoice = "ordinary";
    String taskName,description,date;

    Button []priorityBts;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

         taskName=getIntent().getStringExtra("taskName");
         description = getIntent().getStringExtra("description");
         date = getIntent().getStringExtra("date");

        title_input= findViewById(R.id.taskName);
        data_input = findViewById(R.id.taskData);
        dateField= (TextView) findViewById(R.id.DatePicker);
        add_button = findViewById(R.id.addingTaskBtn);
        if(taskName != null && description != null && date !=null)
        {
            title_input.setText(taskName);
            data_input.setText(description);
            dateField.setText(date);
            add_button.setText("Save changes");
        }


        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dateField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }



    public void handleAddTaskBt(View view)
    {
        MyDatabaseHelper myDB = new MyDatabaseHelper(this);
        //edit Code
        if(add_button.getText().toString().equals("Save changes"))
        {
           myDB.updateTask(taskName,title_input.getText().toString(),data_input.getText().toString(),
                  1,whichActivity,priorityChoice,"on going");
        }

        //Add code
        else {

            myDB.addTask(title_input.getText().toString().trim(), data_input.getText().toString().trim(),
                    dateField.getText().toString().trim(), whichActivity, priorityChoice, "going");

        }

    }
   public void getPriority(View view)
    {


       Button selectedBt = (Button) view;

        priorityChoice =  selectedBt.getText().toString();

        Toast.makeText(this,"You Chosed bt "+priorityChoice,Toast.LENGTH_SHORT).show();
    }
}