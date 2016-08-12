package tom.chinesesuperleague.data;


public class PlayerRoster {

    private String playerName;
    private int playerId;

    public PlayerRoster(String playerName,int playerId){

        this.playerId = playerId;
        this.playerName = playerName;
    }

    public String getPlayerName (int playerId){

        return this.playerName;
    }

    public void setPlayerName (String playerName){

        this.playerName = playerName;
    }

    public void setPlayerId (int playerId){

        this.playerId = playerId;
    }
}
