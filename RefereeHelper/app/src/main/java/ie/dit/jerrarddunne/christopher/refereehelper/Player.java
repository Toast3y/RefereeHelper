package ie.dit.jerrarddunne.christopher.refereehelper;

/**
 * Player class manages each football player and their statistics for the match
 */
public class Player {

    public String PlayerName;
    public int Team;


    public Player (String name, int team){
        PlayerName = name;
        Team = team;
    }

    public String getPlayerName(){
        return PlayerName;
    }


    public int getTeam(){
        return Team;
    }



}
