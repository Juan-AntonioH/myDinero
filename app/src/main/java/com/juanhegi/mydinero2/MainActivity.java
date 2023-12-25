package com.juanhegi.mydinero2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    private ProgressBar mBar;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hola qweqwe
        setContentView(R.layout.activity_main);
        mInput = findViewById(R.id.editDinero);
        mTextEuro = findViewById(R.id.textEuro);
        mTextDolar =  findViewById(R.id.textDolar);
        mTextLibra = findViewById(R.id.textLibra);
        Button mBotonEuro = findViewById(R.id.botonEuro);
        Button mBotonDolar = findViewById(R.id.botonDolar);
        Button mBotonLibra = findViewById(R.id.botonLibra);
        mBar = findViewById(R.id.barra);
        ImageView mImagen = findViewById(R.id.imagen);

        mBotonEuro.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarBrindis("ERROR Euro: Introduzca pts");
            }
            else{
                mTextEuro.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386) + "€");
                mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });

        mBotonDolar.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarBrindis("ERROR Dolar: Introduzca pts");
            }
            else{
                mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386 / 0.93) + "$");
                mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });
        mBotonLibra.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarBrindis("ERROR Libra: Introduzca pts");
            }
            else{
                mTextLibra.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386 / 1.14) + "£");
                mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });
        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                mBar.setVisibility(View.INVISIBLE);
                ocultarTeclado();
            }
        });
    }//Fin de onCreate
    private void mostrarBrindis(String msg) {
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        // En tu actividad o fragmento
       /* View layout = findViewById(android.R.id.content); // Encuentra la vista raíz de tu actividad
        Snackbar snackbar = Snackbar.make(layout, msg, Snackbar.LENGTH_SHORT);

        // Para centrar el Snackbar, necesitas personalizar su vista
        View snackbarView = snackbar.getView();
        FrameLayout.LayoutParams params;
        params = (FrameLayout.LayoutParams)snackbarView.getLayoutParams();
        params.gravity = Gravity.CENTER;
        snackbarView.setLayoutParams(params);

        snackbar.show();*/

    }
    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }

}