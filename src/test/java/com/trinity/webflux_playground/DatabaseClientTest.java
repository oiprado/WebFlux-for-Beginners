package com.trinity.webflux_playground;

import com.trinity.webflux_playground.sec02.model.CustomerOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

import static org.slf4j.LoggerFactory.getLogger;

public class DatabaseClientTest extends AbstractTest {

    private static final Logger logger = getLogger(CustomerOrderRepositoryTest.class);

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void orderDetailsByProduct() {
        var sql = """
            SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE p.description = :description
            ORDER BY co.amount DESC""";

        databaseClient.sql(sql)
                .bind("description", "iphone 20")
                .mapProperties(CustomerOrder.class)
                .all()
                .doOnNext(orderDetails ->  logger.info("{}", orderDetails))
                .as(StepVerifier::create)
                .assertNext(orderDetails -> Assertions.assertEquals(975, orderDetails.getAmount()))
                .assertNext(orderDetails -> Assertions.assertEquals(950, orderDetails.getAmount()))
                .expectComplete()
                .verify();
    }
}
