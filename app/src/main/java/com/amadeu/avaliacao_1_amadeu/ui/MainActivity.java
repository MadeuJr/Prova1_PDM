package com.amadeu.avaliacao_1_amadeu.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amadeu.avaliacao_1_amadeu.R;
import com.amadeu.avaliacao_1_amadeu.model.Texto;
import com.amadeu.avaliacao_1_amadeu.ui.adapters.AdapterTexto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MeuDBHelper dbHelper;
    private Button btnChangeView;
    private ListView lvTextos;
    private List<Texto> listaDeTextos = new ArrayList<>();
    private AdapterTexto adapterTexto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupListeners();

        adapterTexto = new AdapterTexto(this, listaDeTextos);
        lvTextos.setAdapter(adapterTexto);

        dbHelper = new MeuDBHelper(this);

        findAllTextos();

    }

    private void setupViews() {
        btnChangeView = findViewById(R.id.bt_ChangeView);
        lvTextos = findViewById(R.id.lv_listaTextos);
    }

    private void setupListeners() {
        btnChangeView.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        });
        lvTextos.setOnItemClickListener(new EscutadorCliqueComum());
        lvTextos.setLongClickable(true);
        lvTextos.setOnItemLongClickListener( new EscutadorCliqueLongo() );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            String textoEnviado = data.getStringExtra("texto");
            int corEnviada = data.getIntExtra("cor", -1);

            insertTexto(textoEnviado, corEnviada);
            adapterTexto.notifyDataSetChanged();
        }
    }

    private class EscutadorCliqueComum implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(MainActivity.this, listaDeTextos.get(i).getTexto_exibir(), Toast.LENGTH_SHORT).show();
        }
    }

    private class EscutadorCliqueLongo implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            deleteTexto(listaDeTextos.get(i).getId());
            return true;
        }
    }

    // Database methods

    private void insertTexto(String texto, int cor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MeuDBHelper.COLUNA_TEXTO, texto);
        values.put(MeuDBHelper.COLUNA_COR, cor);

        long newRowId = db.insert(MeuDBHelper.TABELA_TEXTOS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Texto adicionado com sucesso", Toast.LENGTH_SHORT).show();
            listaDeTextos.clear();
            findAllTextos();
        } else {
            Toast.makeText(this, "Erro ao adicionar texto", Toast.LENGTH_SHORT).show();
        }

    }

    private void findAllTextos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                MeuDBHelper._ID,
                MeuDBHelper.COLUNA_TEXTO,
                MeuDBHelper.COLUNA_COR
        };

        Cursor cursor = db.query(
                MeuDBHelper.TABELA_TEXTOS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MeuDBHelper._ID));
            String texto = cursor.getString(cursor.getColumnIndexOrThrow(MeuDBHelper.COLUNA_TEXTO));
            int cor = cursor.getInt(cursor.getColumnIndexOrThrow(MeuDBHelper.COLUNA_COR));

            listaDeTextos.add(new Texto(id, texto, cor));

        }
        adapterTexto.notifyDataSetChanged();
        cursor.close();
    }

    private void deleteTexto(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = MeuDBHelper._ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowCount = db.delete(MeuDBHelper.TABELA_TEXTOS, whereClause, whereArgs);

        if (rowCount > 0) {
            Toast.makeText(this, "Texto excluído com sucesso", Toast.LENGTH_SHORT).show();
            listaDeTextos.clear();
            findAllTextos();
            adapterTexto.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Erro ao excluir texto", Toast.LENGTH_SHORT).show();
        }
    }
        private static class MeuDBHelper extends SQLiteOpenHelper {
        private static final String NOME_BANCO = "databaseProva_Amadeu.db"; // Nome do banco de dados
        private static final int VERSAO_BANCO = 1;

        private static final String TABELA_TEXTOS = "textos";
        private static final String _ID = "id";
        private static final String COLUNA_TEXTO = "texto";
        private static final String COLUNA_COR = "cor";

        private static final String SQL_CRIAR_TABELA =
                "CREATE TABLE " + TABELA_TEXTOS + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUNA_TEXTO + " TEXT," +
                        COLUNA_COR + " INTEGER)";

        public MeuDBHelper(Context context) {
            super(context, NOME_BANCO, null, VERSAO_BANCO);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CRIAR_TABELA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}
