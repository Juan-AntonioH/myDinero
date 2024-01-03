package com.juanhegi.mydinero2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText mInput;
    private TextView mTextEuro, mTextDolar, mTextLibra;
    static final String TEXT_EURO = "mTextEuro";
    static final String TEXT_DOLAR = "mTextDolar";
    static final String TEXT_LIBRA= "mTextLibra";
    private EditText mInputDolar, mInputLibra;
    private Button mBotonChDolar, mBotonChLibra;
    private String[] archivos;
    //private ProgressBar mBar;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(TEXT_EURO,mTextEuro.getText().toString());
        savedInstanceState.putString(TEXT_DOLAR,mTextDolar.getText().toString());
        savedInstanceState.putString(TEXT_LIBRA,mTextLibra.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTextLibra.setText(savedInstanceState.getString(TEXT_LIBRA, ""));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        archivos = fileList();
        setContentView(R.layout.activity_main);
        mInput = findViewById(R.id.editDinero);
        mTextEuro = findViewById(R.id.textEuro);
        mTextDolar =  findViewById(R.id.textDolar);
        mTextLibra = findViewById(R.id.textLibra);
        Button mBotonEuro = findViewById(R.id.botonEuro);
        Button mBotonDolar = findViewById(R.id.botonDolar);
        Button mBotonLibra = findViewById(R.id.botonLibra);
        Button mBotonPort = findViewById(R.id.botonPort);
        mInputDolar = findViewById(R.id.editDolar);
        mInputLibra = findViewById(R.id.editLibra);
        mBotonChDolar = findViewById(R.id.botonCambioDolar);
        mBotonChLibra = findViewById(R.id.botonCambioLibra);

        //mBar = findViewById(R.id.barra);
        ImageView mImagen = findViewById(R.id.imagen);

        recuperarValorMoneda("dolar",mInputDolar);
        recuperarValorMoneda("libra",mInputLibra);

        if (savedInstanceState != null) {
            mTextEuro.setText(savedInstanceState.getString(TEXT_EURO, ""));
            mTextDolar.setText(savedInstanceState.getString(TEXT_DOLAR, ""));
        }


        mBotonEuro.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarSnack(arg0,"ERROR Euro: Introduzca pts");
            }
            else{
                mTextEuro.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386) + "€");
               // mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });

        mBotonDolar.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarSnack(arg0,"ERROR Dolar: Introduzca pts");
            }
            else{
                mTextDolar.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386 / Double.parseDouble(String.valueOf(mInputDolar.getText()))) + "$");
               // mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });

        mBotonLibra.setOnClickListener(arg0 -> {
            if (mInput.getText().length()==0) {
                mostrarSnack(arg0,"ERROR Libra: Introduzca pts");
            }
            else{
                mTextLibra.setText(String.format("%1$,.2f",Double.parseDouble(String.valueOf(mInput.getText())) / 166.386 / Double.parseDouble(String.valueOf(mInputLibra.getText()))) + "£");
                //mBar.setVisibility(View.VISIBLE);
                ocultarTeclado();
            }
        });

        mBotonPort.setOnClickListener(arg0 ->{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });

        mBotonChDolar.setOnClickListener(vista ->{
            guardarMoneda("dolar", mInputDolar);
        });

        mBotonChLibra.setOnClickListener(vista ->{
            guardarMoneda("libra", mInputLibra);
        });

        mImagen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                mInput.setText("");
                mTextEuro.setText("");
                mTextDolar.setText("");
                mTextLibra.setText("");
                //mBar.setVisibility(View.INVISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                ocultarTeclado();
            }
        });


    }//Fin de onCreate
    public void guardarMoneda(String tipoMoneda, EditText inputMoneda) {
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(tipoMoneda + ".da", Activity.MODE_PRIVATE));
            archivo.write(inputMoneda.getText().toString());
            archivo.flush();
            archivo.close();
            Toast.makeText(this, tipoMoneda + " guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error guardando " + tipoMoneda, Toast.LENGTH_SHORT).show();
        }
    }
    private void recuperarValorMoneda(String tipoMoneda, EditText inputMoneda) {
        if (existe_archivo(archivos, tipoMoneda + ".da")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput(tipoMoneda + ".da"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todas = "";
                while (linea != null) {
                    todas = todas + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                inputMoneda.setText(todas);
            } catch (IOException e) {
                Toast.makeText(this, "Error leyendo "+ tipoMoneda, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean existe_archivo(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mimenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // Toast.makeText(getApplicationContext(), "Botón pulsado", Toast.LENGTH_SHORT).show();
        finishAffinity();
        return true;
    }

    private void mostrarSnack(View v, String msg){
        Snackbar.make(v, msg, Snackbar.LENGTH_LONG|Snackbar.LENGTH_SHORT)
                .setActionTextColor(Color.CYAN).show();
    }

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mInput.getWindowToken(), 0);
    }

}