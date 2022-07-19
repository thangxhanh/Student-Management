package com.example.studentmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterStudent extends BaseAdapter {
    Activity context;
    ArrayList<Student> list;

    public AdapterStudent(Activity context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        TextView txtMssv = (TextView) row.findViewById(R.id.txtMssv);
        TextView txtName = (TextView) row.findViewById(R.id.txtName);
        TextView txtEmail = (TextView) row.findViewById(R.id.txtEmail);
        TextView txtDob = (TextView) row.findViewById(R.id.txtDob);
        Button btnDel = (Button) row.findViewById(R.id.btnDel);
        Button btnEdit = (Button) row.findViewById(R.id.btnEdit);

        final Student student = list.get(position);
        txtMssv.setText(student.mssv + "");
        txtName.setText(student.name + "");
        txtEmail.setText(student.email + "");
        txtDob.setText(student.dob + "");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("MSSV", student.mssv);
                context.startActivity(intent);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(student.mssv);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return row;
    }

    private void delete(int idNhanVien) {
        SQLiteDatabase database = Database.initDatabase(context,"EmployeeDB.sqlite");
        database.delete("Student", "mssv = ?", new String[]{idNhanVien + ""});
        list.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM Student", null);
        while (cursor.moveToNext()){
            int mssv = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String dob = cursor.getString(2);

            list.add(new Student(mssv, name, email, dob));
        }
        notifyDataSetChanged();
    }
}
