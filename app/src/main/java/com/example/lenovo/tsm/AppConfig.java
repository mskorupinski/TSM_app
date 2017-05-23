package com.example.lenovo.tsm;

/**
 * Created by Lenovo on 12.05.2017.
 */

public class AppConfig {

    private static AppConfig appconfig = new AppConfig();

    private String API_String;

    private String Login;

    private String URL_Login = "http://auto.techdra.pl/API/Login.php";

    private String URL_SmsState = "http://auto.techdra.pl/API/SmsState.php";

    private String URL_StartEmail = "http://auto.techdra.pl/API/StartEmail.php";

    private String URL_StartSms = "http://auto.techdra.pl/API/StartSMS.php";

    private String URL_StopEmail = "http://auto.techdra.pl/API/StopEmail.php";

    private String URL_StopSms = "http://auto.techdra.pl/API/StopSMS.php";

    private String URL_UsrData = "http://auto.techdra.pl/API/UsrData.php";

    public static  AppConfig getInstance(){
        return appconfig;
    }

    public String getAPI_String() {
        return API_String;
    }

    public void setAPI_String(String API_String) {
        this.API_String = API_String;
    }

    public String getURL_Login() {
        return URL_Login;
    }

    public String getURL_SmsState() {
        return URL_SmsState;
    }

    public String getURL_StartEmail() {
        return URL_StartEmail;
    }

    public String getURL_StartSms() {
        return URL_StartSms;
    }

    public String getURL_StopEmail() {
        return URL_StopEmail;
    }

    public String getURL_StopSms() {
        return URL_StopSms;
    }

    public String getURL_UsrData() {
        return URL_UsrData;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }
}
