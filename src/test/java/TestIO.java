import com.lgs.eden.api.API;
import com.lgs.eden.api.APIException;
import com.lgs.eden.api.auth.LoginResponseData;

/**
 * Test IO methods
 */
public class TestIO {
    public static void main(String[] args) {
        try {
            LoginResponseData login = API.imp.login("john", "doe");
            System.out.println(login);

            try {
                API.imp.logout(login.userID);
            } catch (APIException e) {
                System.out.println("logout failed");
                e.printStackTrace();
            }

            API.imp.close();
            System.exit(0);
        } catch (APIException e) {
            System.out.println("login failed");
            e.printStackTrace();
        }
    }

}
