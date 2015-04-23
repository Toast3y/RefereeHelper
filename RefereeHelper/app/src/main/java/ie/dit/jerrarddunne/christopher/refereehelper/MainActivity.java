package ie.dit.jerrarddunne.christopher.refereehelper;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnClickListener {

    private Chronometer chronometer;
    private boolean timerStarted;
    private long timeElapsed;
    private long timeStopped;
    private boolean matchStarted;

    private String newplayername;
    private int newplayerteam;

    List<Player> redTeam = new ArrayList<Player>();
    List<Player> blueTeam = new ArrayList<Player>();

    ListView redTeamView;
    ListView blueTeamView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        timeElapsed = 0;
        timeStopped = 0;
        timerStarted = false;
        matchStarted = false;

        findViewById(R.id.timer_button).setOnClickListener(this);
        findViewById(R.id.new_match_button).setOnClickListener(this);
        findViewById(R.id.add_player_button).setOnClickListener(this);
        redTeamView = (ListView) findViewById(R.id.team1);
        blueTeamView = (ListView) findViewById(R.id.team2);

        //Values passed to program from AddPlayer class
        newplayername = getIntent().getStringExtra("player.PlayerName");
        newplayerteam = getIntent().getIntExtra("player.Team", 0);

        if (newplayerteam == 1){
            addRedPlayers(newplayername, newplayerteam);
            populateRedTeam();
        }
        else if (newplayerteam == 2){
            addBluePlayers(newplayername, newplayerteam);
            populateBlueTeam();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.timer_button:
                   StartStopTimer();
                break;
            case R.id.new_match_button:
                    NewMatch();
                break;
            case R.id.add_player_button:
                    AddPlayer();
                break;
        }
    }



    //Starts and stops the chronometer.
    //Examples taken from http://www.techrepublic.com/blog/software-engineer/use-androids-chronometer-timer-widget-for-your-apps/
    public void StartStopTimer(){

        if (timerStarted == false){

            if (matchStarted == false) {
                //Sets the chronometer to run from when the timer is first started. Only sets it once in case it needs to be stopped.
                //If a referee starts a new match, they simply press the new match button.
                chronometer.setBase(SystemClock.elapsedRealtime());
                matchStarted = true;
            }
            else if (matchStarted == true){
                //Sets the base time to when the clock was paused.
                //Actually the program calculates how long passed while it was started, then sets the clock to assume that much time passed first, but same thing aesthetically
                chronometer.setBase(SystemClock.elapsedRealtime() - timeElapsed);
            }
            chronometer.start();
            timerStarted = true;
        }
        else if (timerStarted == true){
            timeStopped = SystemClock.elapsedRealtime();
            timeElapsed = timeStopped - chronometer.getBase();
            chronometer.stop();
            timerStarted = false;
        }

    }

    //Adding extra time lets the referee keep the game going as needed
    //This is largely a
    public void AddExtraTime(View view){


    }


    public void AddPlayer(){
        Intent i = new Intent(this, AddPlayer.class);
        startActivity(i);

        //Values passed to program from AddPlayer class
        newplayername = getIntent().getStringExtra("player.PlayerName");
        newplayerteam = getIntent().getIntExtra("player.Team", 0);

        if (newplayerteam == 1){
            addRedPlayers(newplayername, newplayerteam);
            populateRedTeam();
        }
        else if (newplayerteam == 2){
            addBluePlayers(newplayername, newplayerteam);
            populateBlueTeam();
        }
    }


    public void NewMatch(){
        //Asks the user if they're sure they want to start a new match.


        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to start a new match?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Reset the program settings for a new match.
                        chronometer.stop();
                        matchStarted = false;
                        timerStarted = false;
                        chronometer.setText("00:00");
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }


    //Adapters for both list views to display both teams
    //Code referenced from "Android Studio App Development | Adding the Contact List" Tutorial
    //https://www.youtube.com/watch?v=h7w3bveUfFA
    private class RedTeamListAdapter extends ArrayAdapter<Player>{

        public RedTeamListAdapter(){
            super(MainActivity.this, R.layout.redteam_layout);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.redteam_layout, parent, false);
            }

            //Get all stats for each player in the list to display and return them as a view
            Player currentRedPlayer = redTeam.get(position);

            TextView name = (TextView) view.findViewById(R.id.redplayerName);
            name.setText(currentRedPlayer.getPlayerName());

            //TextView number = (TextView) view.findViewById(R.id.redplayerNumber);
            //number.setText(redTeam.get(position).toString());

            return view;
        }

    }

    private class BlueTeamListAdapter extends ArrayAdapter<Player>{

        public BlueTeamListAdapter(){
            super(MainActivity.this, R.layout.blueteam_layout);
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.blueteam_layout, parent, false);
            }


            Player currentBluePlayer = blueTeam.get(position);

            TextView name = (TextView) view.findViewById(R.id.blueplayerName);
            name.setText(currentBluePlayer.getPlayerName());

            //TextView number = (TextView) view.findViewById(R.id.blueplayerNumber);
            //number.setText(blueTeam.get(position).toString());


            return view;

        }
    }




    private void addRedPlayers(String PlayerName, int Team){
        redTeam.add(new Player(PlayerName, Team));
    }

    private void addBluePlayers(String PlayerName, int Team){
        blueTeam.add(new Player(PlayerName, Team));
    }


    private void populateRedTeam(){
        ArrayAdapter<Player> adapter = new RedTeamListAdapter();
        redTeamView.setAdapter(adapter);
    }

    private void populateBlueTeam(){
        ArrayAdapter<Player> adapter = new BlueTeamListAdapter();
        blueTeamView.setAdapter(adapter);
    }
}
