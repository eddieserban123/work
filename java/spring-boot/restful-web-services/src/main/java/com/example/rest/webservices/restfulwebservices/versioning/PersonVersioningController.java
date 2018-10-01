package com.example.rest.webservices.restfulwebservices.versioning;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {


    @GetMapping(path = "/v1/person")
    public PersonV1 getPersonV1() {
        return new PersonV1("Peter Pan");
    }

    @GetMapping(path = "/v2/person")
    public PersonV2 getPersonV2() {
        return new PersonV2(new Name("Peter", "Pan"));
    }

    //QueryParams

    @GetMapping(path = "/person/param", params = "version=1")
    public PersonV1 getParamV1() {
        return new PersonV1("Peter Pan");
    }

    @GetMapping(path = "/person/param", params = "version=2")
    public PersonV2 getParamV2() {
        return new PersonV2(new Name("Peter", "Pan"));
    }


    //application headers

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getHeaderV1() {
        return new PersonV1("Peter Pan");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getHeaderV2() {
        return new PersonV2(new Name("Peter", "Pan"));
    }


    //Accept header

    @GetMapping(path = "/person/produces",
            produces = "application/vnd.company.app-v1+json")
    public PersonV1 getProdcesV1() {
        return new PersonV1("Peter Pan");
    }

    @GetMapping(path = "/person/produces",
            produces = "application/vnd.company.app-v2+json")
    public PersonV2 getproducesV2() {
        return new PersonV2(new Name("Peter", "Pan"));
    }




}
