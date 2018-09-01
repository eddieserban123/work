package com.demo.fetcher.data;

import com.demo.fetcher.parser.mfiseriesname.TranslateSymbol;


import java.util.List;

public class TranslateSymbolData implements Data<List<TranslateSymbol>> {

    private List<TranslateSymbol> translateSymbols;

    public TranslateSymbolData(List<TranslateSymbol> translateSymbols) {
        this.translateSymbols = translateSymbols;
    }


    @Override
    public List<TranslateSymbol> getData() {
        return translateSymbols;
    }
}
