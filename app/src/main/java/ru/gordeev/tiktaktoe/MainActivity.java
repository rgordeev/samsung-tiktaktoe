package ru.gordeev.tiktaktoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] tiktaktoe = new Button[3][3];

    private int activePlayer = 0;

    private int round = 0;

    private int p1Score = 0;

    private int p2Score = 0;

    private TextView p1ScoreText;
    private TextView p2ScoreText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p1ScoreText = findViewById(R.id.text_view_p1);
        p2ScoreText = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                String id = String.format("tik-tak-toe_%d%d", i, j);
                int resId = getResources().getIdentifier(id, "id", getPackageName());
                tiktaktoe[i][j] = findViewById(resId);
                tiktaktoe[i][j].setOnClickListener(this);

            }

        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        if (!b.getText().toString().equals(""))
            return;

        if (activePlayer == 0) {
            b.setText("X");
        }

        if (activePlayer == 1) {
            b.setText("O");
        }

        round = round + 1;

        if (win()) {
            if (activePlayer == 0) {
                p1wins();
            } else {
                p2wins();
            }
        } else if (round == 9) {
            draw();
        } else {
            activePlayer = (activePlayer + 1) % 2;
        }
    }


    private void p1wins() {
        p1Score++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updateScores();
        resetBoard();
    }

    private void p2wins() {
        p2Score++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updateScores();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updateScores() {
        p1ScoreText.setText("Player 1: " + p1Score);
        p2ScoreText.setText("Player 2: " + p2Score);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tiktaktoe[i][j].setText("");
            }
        }
        round = 0;
        activePlayer = 0;
    }

    private boolean win() {
        String[][] values = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                values[i][j] = tiktaktoe[i][j].getText().toString();
            }
        }

        if (sum(row(0, values)) % 3 == 0 || sum(row(1, values)) % 3 == 0 || sum(row(2, values)) % 3 == 0)
            return true;

        if (sum(column(0, values)) % 3 == 0 || sum(column(1, values)) % 3 == 0 || sum(column(2, values)) % 3 == 0)
            return true;

        if (sum(mainDiag(values, 3)) % 3 == 0)
            return true;

        if (sum(slaveDiag(values, 3)) % 3 == 0)
            return true;

        return false;
    }

    private int sum(String... a) throws NumberFormatException {
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals("X"))
                result = result + 1;
            else if (a[i].equals("O"))
                result = result + 0;
            else
                return 1;
        }
        return result;
    }

    private String[] row(int i, String[][] a) {
        return a[i];
    }

    private String[] column(int j, String[][] a) {
        String[] result = new String[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i][j];
        }
        return result;
    }

    private String[] mainDiag(String[][] a, int n) {
        String[] result = new String[n];
        for (int i = 0; i < n; i++)
            result[i] = a[i][i];
        return result;
    }

    private String[] slaveDiag(String[][] a, int n) {
        String[] result = new String[n];
        for (int i = 0; i < n; i++)
            result[i] = a[i][n - i - 1];
        return result;
    }

}
