package tom.chinesesuperleague;

//This class is used to store the data for each row in ListView.

public class ListItem {
    private int playerId;
    private String playerForm;

    public ListItem(int playerId, String playerForm){

        this.playerForm = playerForm;
        this.playerId = playerId;
    }

    public int getPlayerId(){
        return playerId;
    }

    public void setPlayerId(int id){
        this.playerId = playerId;
    }

    public String getPlayerForm(){
        return playerForm;
    }

    public void setPlayerForm(String id){
        this.playerForm = playerForm;
    }


}
