package de.jo20046.klausurvorlage_1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MessungActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messung);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 0, "Start Messung");
        menu.add(Menu.NONE, 2, 1, "Richtungsumkehr");
        menu.add(Menu.NONE, 3, 2, "Stopp Messung");
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(MessungActivity.this, "Start Messung", Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(MessungActivity.this, "Richtungsumkehr", Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                Toast.makeText(MessungActivity.this, "Stopp Messung", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}