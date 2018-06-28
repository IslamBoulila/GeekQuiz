package com.boulila.islam.geekquiz;

/**
 * Created by Islam BOULILA on 11/01/2018.
 */

public class User {
    private String vUserName;

    public String getUserName() {
        return vUserName;
    }

    public void setUserName(String userName) {
        vUserName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "vUserName='" + vUserName + '\'' +
                '}';
    }
}
