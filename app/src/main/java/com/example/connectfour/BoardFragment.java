package com.example.connectfour;

import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;

public class BoardFragment extends Fragment {
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate fragment_board layout
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        // Instantiate the member variable mGrid to GridLayout unique id in fragment_board
        mGrid = parentView.findViewById(R.id.board_grid);

        // Add click handler to all grid buttons
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        mGame = new ConnectFourGame();

        if (savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setDisc();
        }
        return parentView;
    }

    private void startGame() {
        mGame.newGame();
        setDisc();
    }
    private void onButtonClick(View view) {
        // Find the button's row and col
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.ROW;
        int col = buttonIndex % ConnectFourGame.COL;

        mGame.selectDisc(row, col);
        setDisc();

        // Congratulate the user if the game is over
        if (mGame.isGameOver()) {
            Toast.makeText(this.requireActivity(), R.string.congrats, Toast.LENGTH_SHORT).show();
            mGame.newGame();
            setDisc();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    private void setDisc() {
        // Iterate through the buttons
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++) {

            // Instance of class Button for the current member of the collection
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Find the button's row and col
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            // Instance of class Drawable for each disc
            Drawable drawable;
            int discValue = mGame.getDisc(row, col);

            // Determine the appropriate color of each disc
            if (discValue == ConnectFourGame.BLUE) {
                drawable = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue));
            } else if (discValue == ConnectFourGame.RED) {
                drawable = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_red));
            } else {
                drawable = DrawableCompat.wrap(ContextCompat.getDrawable(getActivity(), R.drawable.circle_white));
            }

            // Set background depending on the color
            gridButton.setBackground(drawable);
        }
    }
}
