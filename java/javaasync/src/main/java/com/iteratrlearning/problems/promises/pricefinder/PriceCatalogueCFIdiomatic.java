package com.iteratrlearning.problems.promises.pricefinder;

import com.iteratrlearning.examples.promises.pricefinder.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.iteratrlearning.examples.promises.pricefinder.Currency.USD;

public class PriceCatalogueCFIdiomatic
{
    private final Catalogue catalogue = new Catalogue();
    private final PriceFinder priceFinder = new PriceFinder();
    private final ExchangeService exchangeService = new ExchangeService();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new PriceCatalogueCFIdiomatic().findLocalDiscountedPrice(Currency.CHF, "Nexus7");
    }

    private void findLocalDiscountedPrice(final Currency localCurrency, final String productName) throws ExecutionException, InterruptedException {
        long time = System.currentTimeMillis();

       // Product product = catalogue.productByName(productName);
        CompletableFuture<Price> pric=
                CompletableFuture.supplyAsync(()-> catalogue.productByName(productName)).
                        thenComposeAsync(pr->CompletableFuture.supplyAsync(()-> priceFinder.findBestPrice(pr)));

       // Price price = priceFinder.findBestPrice(product);

        CompletableFuture<Double> localPrice=
        CompletableFuture.supplyAsync(()-> exchangeService.lookupExchangeRate(USD, localCurrency)).
                thenCombine(pric, (ex,pr)-> exchange(pr,ex));

    //    double exchangeRate = exchangeService.lookupExchangeRate(USD, localCurrency);

     //   double localPrice = exchange(price, exchangeRate);

        System.out.printf("A %s will cost us %f %s\n", productName, localPrice.get(), localCurrency);
        System.out.printf("It took us %d ms to calculate this\n", System.currentTimeMillis() - time);
    }

    private double exchange(Price price, double exchangeRate)
    {
        return Utils.round(price.getAmount() * exchangeRate);
    }

}

