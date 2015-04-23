package ie.dit.jerrarddunne.christopher.refereehelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * Code to handle adding new players to the football match
 */
public class AddPlayer extends Activity implements View.OnClickListener{

    private EditText playerNameText;
    private RadioGroup teamGroup;

    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate (savedInstanceState);
        setContentView(R.layout.addplayer_layout);

        findViewById(R.id.add_player_button).setOnClickListener(this);
        findViewById(R.id.return_button).setOnClickListener(this);
        findViewById(R.id.team_radio).setOnClickListener(this);

        playerNameText = (EditText) findViewById(R.id.newplayername);
        teamGroup = (RadioGroup) findViewById(R.id.team_radio);

    }



    public void onClick(View view){

        switch (view.getId()){

            case R.id.add_player_button:
                addPlayer();
                break;
            case R.id.return_button:
                goBack();
                break;
        }
    }


    //return method, returns user to main activity
    public void goBack(){
        finish();
    }

    public void goBackResult(Player player){

        if(player == null){
            finish();
        }

        Intent i = new Intent(this, AddPlayer.class);
        i.putExtra(player.PlayerName, player.Team);
        startActivity(i);
    }


    public void addPlayer(){
        RadioButton teamchoice = (RadioButton) findViewById(teamGroup.getCheckedRadioButtonId());
        Player player;

        if(teamchoice.getText().toString() == "Red Team"){
            //false == red team
            player = new Player(playerNameText.getText().toString(), 1);
            goBackResult(player);
        }
        else if (teamchoice.getText().toString() == "Blue Team"){
            //true == blue team
            player = new Player(playerNameText.getText().toString(), 2);
            goBackResult(player);
        }
        else{
            goBack();
        }



    }

}
