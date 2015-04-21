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
import android.widget.Button;
import android.widget.Chronometer;


public class MainActivity extends ActionBarActivity implements OnClickListener {

    private Chronometer chronometer;
    private boolean timerStarted;
    private long timeElapsed;
    private long timeStopped;
    private boolean matchStarted;

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


    public void AddPlayer(View view){
        Intent i = new Intent(this, AddPlayer.class);
        startActivity(i);
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

}
