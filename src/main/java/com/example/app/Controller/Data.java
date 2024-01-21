package com.example.app.Controller;

import com.example.app.Models.User.User;

import java.util.List;

public class Data {



    private User currentUser;

    public Data() {

        currentUser = new User(null, null, null, null, null, null, null);
    }


    public class getDataGLobal {
        public static Data dataGlobal = new Data();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
