package com.example.myapplication2220202501tsize;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button b1;
    TextView rozmiarT;
    TextView tBottom;
    SeekBar seekBar1;

    int listIndex = 0;
    String NAZWWA_PREFERENCJI = "UstawieniaAplikacji";
    String KLUCZ_INDEKSU_NAPISU = "KLUCZ_INDEKSU_NAPISU";
    String KLUCZ_ROZMIARU_CZCIONKI = "rozmiarCzcionki";
    int DOMUSLNY_ROZMIAR_CZCIONKI = 20;
    int aktRozmiarCzci;
    int aktNrIndeksu;
    private SharedPreferences preferencjeWspoldzielone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        rozmiarT = findViewById(R.id.sizeT);
        tBottom = findViewById(R.id.Btext);
        seekBar1 = findViewById(R.id.seekbarinit);

        preferencjeWspoldzielone = getSharedPreferences(NAZWWA_PREFERENCJI, Context.MODE_PRIVATE);

        String[] array1 = {"Dzień dobry", "Good morning", "Buenos dias"};

        aktRozmiarCzci = preferencjeWspoldzielone.getInt(KLUCZ_ROZMIARU_CZCIONKI, DOMUSLNY_ROZMIAR_CZCIONKI);

        tBottom.setTextSize(aktRozmiarCzci);
        rozmiarT.setText("" + aktRozmiarCzci);
        seekBar1.setProgress(aktRozmiarCzci);

        aktNrIndeksu = preferencjeWspoldzielone.getInt(KLUCZ_INDEKSU_NAPISU, 0);

        tBottom.setText("" + array1[aktNrIndeksu]);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rozmiarT.setText("" + i);

                tBottom.setTextSize(i);

                SharedPreferences.Editor edytor = preferencjeWspoldzielone.edit();
                edytor.putInt(KLUCZ_ROZMIARU_CZCIONKI, i);

                edytor.apply();

                Toast.makeText(MainActivity.this, "Zmieniono Preferecje", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b1.setOnClickListener(v -> {
            listIndex += 1;

            if(listIndex == 3){
                listIndex = 0;
            }

            SharedPreferences.Editor edytor = preferencjeWspoldzielone.edit();
            edytor.putInt(KLUCZ_INDEKSU_NAPISU, listIndex);

            edytor.apply();

            tBottom.setText("" + array1[listIndex]);
            Toast.makeText(MainActivity.this, "Zmieniono Preferecje", Toast.LENGTH_SHORT).show();
        });
    }
}