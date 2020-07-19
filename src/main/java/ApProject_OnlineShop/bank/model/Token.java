package model;

import java.time.LocalTime;

public class Token {
    LocalTime startTime;
    LocalTime endTime;
    String tokenString;
    String userName;

    public Token(String tokenString, String userName) {
        this.tokenString = tokenString;
        this.userName = userName;
        this.startTime = LocalTime.now();
        this.endTime = startTime.plusHours(1);
    }

    public String getUserName(){
        return userName;
    }

    public String getTokenString(){
        return tokenString;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isExpired(){
        if (getEndTime().isAfter(LocalTime.now()))
            return false;
        return true;
    }

    public static String getRandomToken(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder randomCode = new StringBuilder(12);
        for (int i = 0; i < 20; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomCode.append(AlphaNumericString.charAt(index));
        }
        return randomCode.toString();
    }
}
