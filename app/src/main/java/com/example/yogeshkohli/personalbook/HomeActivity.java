package com.example.yogeshkohli.personalbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {

    GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupGridView();
    }

    public void setupGridView(){
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        //Set Click Event
        setClickEvent(mainGrid);
    }
    //Handle click on grid cells
    private void setClickEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch(finalI){

                        case 0:

                            break;

                        case 1:

                            break;

                        case 2:

                            break;

                        case 3:
                            fireIntent(new AboutAppActivity());
                            break;

                        case 4:
                            fireIntent(new ContactDeveloperActivity());
                            break;

                        case 5:
                            fireIntent(new HelpActivity());
                            break;
                    }
                }
            });
        }
    }

    //Fire Intent to different Activities according to requirement
    public void fireIntent(Activity page){
        Intent intent = new Intent(HomeActivity.this,page.getClass());
        startActivity(intent);
    }
}
