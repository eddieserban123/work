package com.demo.folder.tata.fetcher;

import com.demo.folder.tata.fetcher.data.Data;
import com.demo.folder.tata.fetcher.params.GeneralParams;
import com.demo.folder.tata.transform.Transformer;
import com.demo.folder.tata.validation.Validator;


public abstract class GeneralFetcher implements Validator, Transformer{

    private GeneralParams params;

    public GeneralFetcher(GeneralParams params) {
        this.params = params;
    }

    public abstract Data fetch();

    public GeneralParams getParams() {
        return params;
    }

    @Override
    public boolean validate(Data data){
        //System.out.println("General Validation");
        return true;
    }

    @Override
    public Data transform(Data data){
        //System.out.println("General Transformation");
        return data;
    }

    public Data getData(){
        System.out.println("Start retrieving data flow");
        long startTime = System.currentTimeMillis();
        Data dataRet = fetch();
        long duration = System.currentTimeMillis()-startTime;
        dataRet = transform(dataRet);
        if(!validate(dataRet)) {
            //! log ERROR
            //maybe throw RunTimeExcpetion
            System.out.println("Validation failed");
        }
        System.out.println("fetch duration took "  + duration + " ms for " + params.getIdentifier());
        return dataRet;
    }


}
