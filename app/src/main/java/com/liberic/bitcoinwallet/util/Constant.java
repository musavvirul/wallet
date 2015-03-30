package com.liberic.bitcoinwallet.util;

public class Constant {
    /************ PREFERENCES *********************/
    public static final String PREF_GENERAL = "general";

    public static final String USER = "USERNAME";
    public static final String PASS = "PASSWORD";
    public static final String NAME = "nameOfContact";
    public static final String PHONE = "phoneOfContact";
    public static final String IMAGE = "imageOfContact";
    public static final String MODE = "mode";
    public static final String ADDRESS = "address";
    public static final String LABEL = "label";
    public static final String MESSAGE = "message";
    public static final String AMOUNT = "amount";

    /************ MYSQL **************************/
    public static final String IP_MYSQL = "172.17.0.17";
    public static final String PORT_MYSQL = "3306";
    public static final String TABLE_USER_MYSQL = "user";
    public static final String USER_MYSQL = "root";
    public static final String PASS_MYSQL = "root";
    public static final String USER_TABLE_MYSQL = "username";
    public static final String PASS_TABLE_MYSQL = "password";

    public enum Mode {
        SEND, RECEIVE
    }
}
