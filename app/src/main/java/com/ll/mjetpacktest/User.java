package com.ll.mjetpacktest;

/**
 * @Auther kylin
 * @Data 2020/8/21 - 23:13
 * @Package com.ll.mjetpacktest
 * @Description
 */
public class User {
    private String userName;
    private int userAge;

    public User(String userName, int userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
}
