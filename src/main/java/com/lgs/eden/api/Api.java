package com.lgs.eden.api;

public class Api {

    public static String getApiVersion() { return "1.0"; }

    public static String passwordForgotPage(String code) {
        // website only supports en or fr
        code = Api.formatCode(code);
        return "https://lgs-games.com/"+code+"/password_forgot";
    }

    private static String formatCode(String code) {
        if (!code.equals("en") && !code.equals("fr"))
            code = "en";
        return code;
    }

    /**
     * todo
     * Return code, see ApiCodes
     */
    public static int login(String username, String pwd) {
        if (!username.equals("Raphik") || !pwd.equals("tester")) {
            return -1;
        }
        return 0;
    }

    /**
     * todo
     * Return code, see ApiCodes
     */
    public static int register(String username, String pwd, String email) {
        if (!username.equals("root") || !pwd.equals("aza") || !email.equals("a@b.c")) {
            return -1;
        }
        return 0;
    }
}
