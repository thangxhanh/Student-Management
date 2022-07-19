package com.example.studentmanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {
    final String DATABASE_NAME = "EmployeeDB.sqlite";
    int mssv = -1;

    Button btnSave, btnCancel;
    EditText edtMssv, edtName, edtEmail, edtDob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addControls();
        addEvents();
        initUI();
    }

    private void addControls() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        edtMssv = (EditText) findViewById(R.id.edtMssv);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtDob = (EditText) findViewById(R.id.edtDob);
    }

    private void addEvents(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        mssv = intent.getIntExtra("MSSV", -1);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM Student WHERE mssv = ? ",new String[]{mssv + ""});
        cursor.moveToFirst();
        String name = cursor.getString(1);
        String email = cursor.getString(2);
        String dob = cursor.getString(2);

        edtMssv.setText(mssv);
        edtName.setText(name);
        edtEmail.setText(email);
        edtDob.setText(dob);
    }

    private void update(){
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String dob = edtDob.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("dob", dob);

        SQLiteDatabase database = Database.initDatabase(this, "EmployeeDB.sqlite");
        database.update("Student", contentValues, "id = ?", new String[] {mssv + ""});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
