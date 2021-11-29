package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView PlayerOneScore,PlayerTwoScore,PlayerStatus;
    private Button[] buttons=new Button[9];
    private Button resetGame;
    private int playerOneScoreCount,playerTwoScoreCount,RountCount;
    boolean activePlayer;
    // p1=0, p2=2, empty=2
    int [] gameState={2,2,2,2,2,2,2,2,2};
    int [][] winningPositions={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PlayerOneScore =(TextView)findViewById(R.id.PlayerOneScore);
        PlayerTwoScore=(TextView)findViewById(R.id.PlayerTwoScore);
        PlayerStatus =(TextView)findViewById(R.id.playerStatus);

        resetGame=(Button) findViewById(R.id.resetGame);

        for(int i=0;i<buttons.length;i++){
            String buttonID ="btn_"+i;
            int resourceID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=(Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        RountCount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;
        activePlayer=true;


    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID=v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer= Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));
        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#89a8f0"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#ff4a5f"));
            gameState[gameStatePointer]=1;
        }
        RountCount++;
        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                UpdatePlayerSore();
                Toast.makeText(this,"Player One Won!",Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                UpdatePlayerSore();
                Toast.makeText(this,"Player Two Won!",Toast.LENGTH_SHORT).show();
                playAgain();

            }
        }else if(RountCount==9){
            playAgain();
            Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        }else {
            activePlayer = !activePlayer;
        }
        if (playerOneScoreCount>playerTwoScoreCount) {
            PlayerStatus.setText("Player One is winning!");
        } else   if (playerOneScoreCount<playerTwoScoreCount) {
            PlayerStatus.setText("Player Two is winning!");
        }else {
            PlayerStatus.setText("");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                PlayerStatus.setText("");
                UpdatePlayerSore();
            }
        });




    }
    public boolean checkWinner(){
        boolean winnerResult=false;
        for(int [] winningPos:winningPositions){
            if(gameState[winningPos[0]]==gameState[winningPos[1]]&&
                    gameState[winningPos[1]]==gameState[winningPos[2]]&&
                    gameState[winningPos[0]]!=2){
                winnerResult=true;

            }
        }
        return winnerResult;
    }
    public void UpdatePlayerSore(){
        PlayerOneScore.setText(Integer.toString(playerOneScoreCount));
        PlayerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain(){
        RountCount=0;
        activePlayer=true;
        for (int i=0;i< buttons.length;i++){
            gameState[i]=2;
            buttons[i].setText("");
        }
    }

}