package com.example.simplesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MahasiswaDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "database_mi.db";
    private static final int DB_VERSION = 1;

    public MahasiswaDbHelper(@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_mahasiswa (nim INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_mahasiswa");
        onCreate(db);
    }

    public void tambahMahasiswa(String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nama", nama);
        long newData = db.insert("tb_mahasiswa", null, cv);

        if (newData == -1) Toast.makeText(context, "Gagal ditambahkan", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
    }

    public void updateMahasiswa(int nim, String newNama) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE tb_mahasiswa SET nama = '" + newNama + "' WHERE nim = '" + nim + "'");
        Toast.makeText(context, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
    }

    public void deleteMahasiswa(int nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tb_mahasiswa WHERE nim = '" + nim + "'");
        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}
