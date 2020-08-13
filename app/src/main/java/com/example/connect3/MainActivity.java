package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 = yellow, 1 = red
    int activePlayer = 0;
    //boolean for if game is over or not
    boolean gameIsActive = true;
    //2 means un-played. The slot is empty
    int[] gameState= {2,2,2,2,2,2,2,2,2};
    //array to determine the winning positions. Every three consecutive same colored tokens in any horizontal, vertical, or diagonal direction
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{2,4,6},{0,4,8}};

    //This method is responsible for the token dropping in
    public void dropIn (View view){

        //ImageView called counter
        ImageView counter = (ImageView)view;

        //this integer is used for the tag counter
        //get the tag of the element. This is so we can decide which element are filled. This is important to developing the logic of the app
        //we use Integer.parseInt to convert the string of the tag to an integer
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        //now we develop the logic for the gameState. This will let us allow the use to enter only one token in a spot and cannot be overridden
        //if the element of the grid is equal to 2 then the item can be placed if not, the item cannot be placed there (no need for the else statement)
        if (gameState[tappedCounter] == 2 && gameIsActive){

            //we set gameState index to the activePlayer state so that they element is no longer 2 and cannot be overridden with another token
            gameState[tappedCounter] = activePlayer;

            //set so the image is off of the screen
            counter.setTranslationY(-1500f);

            //check if player is yellow
            if (activePlayer == 0) {
                //image is set to yellow token
                counter.setImageResource(R.drawable.yellow);
                //set activePlayer to 1 because yellow is done playing
                activePlayer = 1;
            } else { //check if player is red
                //image is set to red token
                counter.setImageResource(R.drawable.red);
                //set activePlayer to 0 because red is done playing
                activePlayer = 0;
            }

            //when clicked the image will translate onto the screen and into the spot needed
            counter.animate().translationYBy(1500f).setDuration(300);

            //check if one player has won. Loop through the gameState and check if the winning positions all have the same activePlayer number
            for(int[] winningPosition: winningPositions ){
                //check if any player has won
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){

                    //someone has one the game. set the gameIsActive to false
                    gameIsActive = false;

                    //String used for winner. By default lets assign red to win
                    String winner = "Red";
                    //check who has won the game
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }

                    //Someone has won the game. Show the message of who has one and play again button
                    TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                    winnerMessage.setText(winner + " has won!");

                    //show the message. Since the linear layout was invisible, now the layout is visible
                    LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
                }else { //check for draw or tie. One way of doing this is if everything is not 2 in gameState. This means a player a placed a token on all spots

                    //boolean used for if the game is over
                    boolean gameIsOver = true;
                    //loop through gameState
                    for(int counterState: gameState ){
                       if(counterState == 2){
                           gameIsOver = false;
                       }
                    }
                    if (gameIsOver){
                        //Someone has won the game. Show the message of who has one and play again button
                        TextView winnerMessage = (TextView)findViewById(R.id.winnerMessage);
                        winnerMessage.setText("Game Drawn!");
                        //show the message. Since the linear layout was invisible, now the layout is visible
                        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }
                }//end of draw check

            }//end of for each loop check
        }
    }//end of drop in method

    //playAgain method
    public void playAgain(View view){

        //game is active again because of reset
        gameIsActive = true;

        //We need to make the pop up linear layout invisible again
        LinearLayout layout = (LinearLayout)findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);

        //need to update the player and game state again back to default
        activePlayer = 0;
        //gameState = {2,2,2,2,2,2,2,2,2}; we need a for loop to update the array contents back to default. Doing this alone basically means we're setting the array equal to another array
        //we can use a for loop to reset the contents of the gameState
        for(int i =0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        //now we need to loop through and remove the elements in the grid so there is no element there. Essentially resetting the board
        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        //we can se getChildCount to find out how many views are used in the grid
        for(int i =0; i<gridLayout.getChildCount(); i++){
            //reset the image
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0); //setting the image resource to zero essentially takes away the image that was there before
        }
    }//end of playAgain method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}//end of the class