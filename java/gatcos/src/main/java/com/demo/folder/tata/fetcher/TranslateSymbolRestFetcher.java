package com.demo.folder.tata.fetcher;

import com.demo.folder.tata.fetcher.data.Data;
import com.demo.folder.tata.fetcher.data.StringData;
import com.demo.folder.tata.fetcher.data.TranslateSymbolData;
import com.demo.folder.tata.fetcher.params.RestParam;
import com.demo.folder.tata.fetcher.parser.mfiseriesname.TranslateSymbol;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.demo.folder.tata.fetcher.parser.JsonParser.JSONPARSER;

public class TranslateSymbolRestFetcher extends RestFetcher {

    public TranslateSymbolRestFetcher(RestParam params) {
        super(params);
    }

    @Override
    public Data fetch() {
        StringData data = (StringData) super.fetch();
        JSONObject jobj = (JSONObject) new JSONArray(data.getData()).get(0);
        List<TranslateSymbol> symbols = new ArrayList<>();
        try {
            symbols = JSONPARSER.getMapper().readValue(jobj.getJSONArray("output").toString(),
                    JSONPARSER.getMapper().getTypeFactory().constructCollectionType(List.class, TranslateSymbol.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TranslateSymbolData(symbols);
    }

}
