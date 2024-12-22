package com.example.simplesqlite;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MahasiswaDbHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    ImageView imgTambah;
    Dialog dialogTambah;
    Button btnTambah;
    EditText etNama;

    ArrayList<Integer> listNim;
    ArrayList<String> listNama;
    RecyclerView rvUtama;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MahasiswaDbHelper(this);
        showData();

        imgTambah = findViewById(R.id.iv_tambah);
        imgTambah.setOnClickListener(v -> {
            dialogTambah = new Dialog(MainActivity.this);
            dialogTambah.setContentView(R.layout.dialog_tambah);
            dialogTambah.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialogTambah.show();

            etNama = dialogTambah.findViewById(R.id.et_nama);
            btnTambah = dialogTambah.findViewById(R.id.btn_tambah);

            btnTambah.setOnClickListener(v1 -> {
                if(etNama.getText().toString().isBlank()){
                    Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    MahasiswaDbHelper helper = new MahasiswaDbHelper(this);
                    helper.tambahMahasiswa(etNama.getText().toString());
                    dialogTambah.dismiss();
                    showData();
                }
            });
        });
    }

    public void showData() {
        listNim = new ArrayList<>();
        listNama = new ArrayList<>();

        layout = new LinearLayoutManager(this);
        adapter = new MahasiswaAdapter(this, listNim, listNama);
        rvUtama = findViewById(R.id.rv_utama);

        rvUtama.setLayoutManager(layout);
        rvUtama.setHasFixedSize(true);
        rvUtama.setAdapter(adapter);

        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM tb_mahasiswa", null);
        cursor.moveToFirst();

        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count);
            listNim.add(cursor.getInt(0));
            listNama.add(cursor.getString(1));
        }
    }
}