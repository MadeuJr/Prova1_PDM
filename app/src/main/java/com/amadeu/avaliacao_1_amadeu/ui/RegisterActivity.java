package com.amadeu.avaliacao_1_amadeu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amadeu.avaliacao_1_amadeu.R;


public class RegisterActivity extends AppCompatActivity {

    private ImageView iv_imageClose;
    private EditText et_inputText;
    private RadioGroup rg_corSelecionada;
    private Button bt_Register;
    private Button bt_clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setupViews();
        setupListeners();
    }
    private void setupViews(){
        iv_imageClose = findViewById(R.id.iv_CloseButton);
        et_inputText = findViewById(R.id.et_InputText);
        rg_corSelecionada = findViewById(R.id.rg_Color);
        bt_Register = findViewById(R.id.bt_Register);
        bt_clear = findViewById(R.id.bt_Clear);
    }

    private void setupListeners(){
        iv_imageClose.setOnClickListener(view -> {
            Intent resultadoIntent = new Intent();
            setResult(RESULT_CANCELED, resultadoIntent);
            finish();
        });

        bt_clear.setOnClickListener(view -> {
            et_inputText.setText("");
            rg_corSelecionada.clearCheck();
        });

        bt_Register.setOnClickListener(view -> {
            Intent dadosObtidos = new Intent(RegisterActivity.this, MainActivity.class);
            String textoinserido = et_inputText.getText().toString();
            int corSelecionada = -1;

            int idRadioSelected = rg_corSelecionada.getCheckedRadioButtonId();
            // Foi usado o logcat par conseguir obter o valor do id de cada button para poder fazer as operações
            // Porque não estava conseguindo fazer;
            switch (idRadioSelected){
                case 2131231224:
                    corSelecionada = R.color.red;
                    break;
                case 2131231223:
                    corSelecionada = R.color.green;
                    break;
                case 2131231222:
                    corSelecionada = R.color.blue;
                    break;
            }

            if (textoinserido.equals("")){
                Toast.makeText(getApplicationContext(), "Insira um texto a ser inserido.", Toast.LENGTH_SHORT).show();
            }

            if (corSelecionada == -1){
                Toast.makeText(getApplicationContext(), "Selecione uma cor", Toast.LENGTH_SHORT).show();
            }

            if (!textoinserido.equals("") && !(corSelecionada == -1)){
                dadosObtidos.putExtra("texto", textoinserido);
                dadosObtidos.putExtra("cor", corSelecionada);
                setResult(RESULT_OK, dadosObtidos);
                finish();
            }
        });
    }
}




