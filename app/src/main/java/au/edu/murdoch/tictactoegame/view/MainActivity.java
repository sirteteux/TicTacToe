package au.edu.murdoch.tictactoegame.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import au.edu.murdoch.tictactoegame.R;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, tictactoe.class);
                intent1.putExtra("computer", "friend");
                startActivity(intent1);
            }
        };

        Button button1 = (Button) findViewById(R.id.playFriend);
        button1.setOnClickListener(listener1);

        View.OnClickListener listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, tictactoe.class);
                intent2.putExtra("computer", "computer");
                startActivity(intent2);
            }
        };

        Button button2 = (Button) findViewById(R.id.playComputer);
        button2.setOnClickListener(listener2);
    }
}