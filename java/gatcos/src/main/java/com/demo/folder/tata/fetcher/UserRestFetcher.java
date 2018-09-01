package com.demo.folder.tata.fetcher;

import com.demo.folder.tata.fetcher.data.Data;
import com.demo.folder.tata.fetcher.data.StringData;
import com.demo.folder.tata.fetcher.parser.beans.User;
import com.demo.folder.tata.fetcher.data.UserData;
import com.demo.folder.tata.fetcher.params.RestParam;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER;

public class UserRestFetcher extends RestFetcher {

    public UserRestFetcher(RestParam params) {
        super(params);
    }

    @Override
    public Data fetch() {
        StringData data = (StringData) super.fetch();
        List<User> users = new ArrayList<>();
        try {
            users = JSONPARSER.getMapper().readValue(data.getData(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, User.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new UserData(users);
    }

}
