package de.jo20046.klausurvorlage_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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

        EditText editCityname = (EditText) findViewById(R.id.edit_cityname);
        TextView tvTimestamp = (TextView) findViewById(R.id.tv_timestamp);
        Button btnMessung = (Button) findViewById(R.id.btn_messung);

        editCityname.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String str = DateTimeFormatter.ISO_LOCAL_TIME.format(LocalTime.now()) + ": " +
                            editCityname.getText().toString();
                    tvTimestamp.setText(str);
                    return true;
                }
                return false;
            }
        });

        btnMessung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessungActivity.class);
                startActivity(intent);
            }
        });
    }
}