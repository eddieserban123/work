package org.integration.springintegration;

import org.integration.springintegration.book.BookOrder;
import org.integration.springintegration.book.Order;
import org.integration.springintegration.book.ShoppingCart;
import org.integration.springintegration.book.ShoppingCartOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;

import java.util.ArrayList;
import java.util.List;


@ImportResource(value =
        {
        "classpath*:/spring/OrderTest-context.xml"
        //"classpath*:/spring/ShoppingCartOrderTest-context.xml"
        })

@ComponentScan("org.integration.springintegration.book.annotation")
@EnableIntegration
@SpringBootApplication
public class SpringIntegrationApplication  implements CommandLineRunner {

    private static int count = 1;
    final Logger logger = LoggerFactory.getLogger(SpringIntegrationApplication.class);


    @Autowired
    Order order;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationApplication.class, args);
    }


//    @Autowired
//    private ShoppingCartOrder shoppingCartOrder;
//
//
//    public void testShoppingCartOrder() {
//        long start = System.nanoTime();
//
//        List<BookOrder> lBookOrders = new ArrayList<BookOrder>();
//
//        BookOrder message01 = new BookOrder("Stranger in a Strange Land", 2, BookOrder.OrderType.PICKUP);
//        BookOrder message02 = new BookOrder("Tender is the Night", 1, BookOrder.OrderType.PICKUP);
//        BookOrder message03 = new BookOrder("Stranger in a Strange Land", 1, BookOrder.OrderType.DELIVERY);
//
//        lBookOrders.add(message01);
//        lBookOrders.add(message02);
//        lBookOrders.add(message03);
//
//        ShoppingCart message = new ShoppingCart(lBookOrders);
//
//        for (int i = 1; i <= count; i++) {
//            shoppingCartOrder.process(message);
//        }
//
//        long end = System.nanoTime();
//
//        logger.info("Shopping cart order pickup test took {}ns", (end - start)/count);
//    }

    public void testOrder() {
        long start = System.nanoTime();
        BookOrder message = new BookOrder("Stranger in a Strange Land", 2, BookOrder.OrderType.PICKUP);

        for (int i = 1; i <= count; i++) {
            order.process(message);
        }

        long end = System.nanoTime();
        logger.info("Order pickup test took {}ns", (end - start) / count);

    }


    @Override
    public void run(String... args) throws Exception {
        testOrder();
//        testShoppingCartOrder();
    }
}

