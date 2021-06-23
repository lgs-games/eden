package com.lgs.eden.api.nexus;

import com.lgs.eden.api.APIException;
import com.lgs.eden.api.games.*;
import io.socket.client.Socket;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Nexus imp of Game
 */
public class GameImp extends ImpSocket implements GameAPI {

    // constructor
    public GameImp(Socket socket) { super(socket); }

    // ------------------------------ METHODS ----------------------------- \\

    @Override
    public EdenVersionData getEdenVersion() throws APIException {
        return null;
    }

    @Override
    public ArrayList<MarketplaceGameData> getMarketPlaceGames(int begin, int count, String code, int userID) {
        return null;
    }

    @Override
    public GameViewData getGameData(int userID, int gameID) {
        return null;
    }

    @Override
    public ObservableList<BasicGameData> getUserGames(int userID) {
        return null;
    }

    @Override
    public boolean addToLibrary(int userID, BasicGameData game) {
        return false;
    }

    @Override
    public boolean removeFromLibrary(int userID, BasicGameData game) {
        return false;
    }

    @Override
    public ShortGameViewData getGameDateUpdate(int userID, int gameID) {
        return null;
    }
}
