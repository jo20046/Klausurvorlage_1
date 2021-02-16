package de.jo20046.klausurvorlage_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editCityname = findViewById(R.id.edit_cityname);
        TextView tvTimestamp = findViewById(R.id.tv_timestamp);
        Button btnMessung = findViewById(R.id.btn_messung);

        // Eigenschaft 9: Anzeigen des aktuellen Messwerts, falls die MainActivity durch den Back-
        // Button der MessungActivity gestartet wurde
        if (getIntent().getExtras() != null) {
            tvTimestamp.setText(getIntent().getExtras().getString("messwert"));
        }

        // Eigenschaft 2: Anzeigen des Timestamps nachdem Enter gedrückt wurde
        editCityname.setOnKeyListener((view, keyCode, keyevent) -> {
            if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String str = DateTimeFormatter.ISO_LOCAL_TIME.format(LocalTime.now()) + ": " +
                        editCityname.getText().toString();
                tvTimestamp.setText(str);
                return true;
            }
            return false;
        });

        // Eigenschaft 4: Start der Activity für die Messung per Button
        btnMessung.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MessungActivity.class);
            startActivity(intent);
        });
    }
}