package com.demo.folder.tata.fetcher.data;



import com.demo.folder.tata.fetcher.parser.beans.User;

import java.util.List;

public class UserData implements Data<List<User>> {

    private List<User> users;

    public UserData(List<User> translateSymbols) {
        this.users = translateSymbols;
    }

    @Override
    public List<User> getData() {
        return users;
    }
}
