import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.auth.LoginResponseData;
import com.lgs.eden.api.games.MarketplaceGameData;

import java.util.ArrayList;

/**
 * Test IO methods
 */
public class TestIO {
    public static void main(String[] args) throws InterruptedException {
        try {
            API.imp.init();
            Thread.sleep(1000);

            // login
            LoginResponseData login = API.imp.login("Raphik", "tester");
            System.out.println(login);

            ArrayList<MarketplaceGameData> games = API.imp.getMarketPlaceGames(0, 4, "en", login.userID);
            System.out.println(games);

            Thread.sleep(5000);

            // logout
            try {
                API.imp.logout(login.userID);
            } catch (APIException e) {
                System.out.println("logout failed");
                e.printStackTrace();
            }
        } catch (APIException e) {
            System.out.println("login failed");
            e.printStackTrace();
        } finally {
            // close
            API.imp.close();
            System.exit(0);
        }
    }

}
