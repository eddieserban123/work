/*
 * Copyright 2007-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.integration.springintegration.shoppingCart;

import org.integration.springintegration.book.BookOrder;
import org.integration.springintegration.book.Delivery;
import org.integration.springintegration.book.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;

import java.util.List;

/**
 * Splits a shopping cart order into separate orders.
 * 
 * @author David Winterfeldt
 */
@MessageEndpoint
public class OrderSplitter {

    final Logger logger = LoggerFactory.getLogger(OrderSplitter.class);
    
    /**
     * Splits a shopping cart order into separate book orders.
     */
    @Splitter(inputChannel="deliveryPickupOrder", outputChannel="processOrder")
    public List<BookOrder> split(ShoppingCart shoppingCart) {
        logger.debug("In OrderSplitter for {}.  bookOrderCount={}",
                     (shoppingCart instanceof Delivery ? "delivery" : "pickup"),
                     shoppingCart.getBookOrderList().size());
	    
		return shoppingCart.getBookOrderList();
	}

}
