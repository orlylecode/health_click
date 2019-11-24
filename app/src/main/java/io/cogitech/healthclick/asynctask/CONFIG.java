package io.cogitech.healthclick.asynctask;

public class CONFIG {

    public final static String URL = "https://sandbox-healthservice.priaid.ch/";
    public final static String USERNAME = "orlydevignyngahe@gmail.com";
    public final static String PASSWORD = "Tn32HiQo68Yrg5L7P";
    public final static String TOKEN = "";
    public final static String URL_LOGIN = "https://sandbox-authservice.priaid.ch/login";
    public final String LANGUE = "fr-fr";

/*    public final String LANGUE = "fr-fr";
    public final static String URL = "https://healthservice.priaid.ch/";
    public final static String USERNAME = "Rj5g7_GMAIL_COM_AUT";
    public final static String PASSWORD = "d3H2DyGz65LkKn97M";
    public final static String TOKEN = "";
    public final static String URL_LOGIN = "https://authservice.priaid.ch/login";*/

    public static String getURL() {
        return URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getTOKEN() {
        return TOKEN;
    }
}
