package com.laca.springpaypal;

import com.laca.springpaypal.Service.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaypalServiceTest {

    @Autowired
    private PaypalService paypalService;

    private static final String PAYMENT_ID = "PAYID-MAZ7RVA3WL27699HA3776845";
    private static final String PAYER_ID = "G2B5L8MG8WC4U";


    @Test
    public void createNewPayment() {
        Payment testPayment = null;

        try {
            testPayment = paypalService.createPayment(
                    10.00,
                    "USD",
                    "paypal",
                    "sale",
                    "testdescription",
                    "/pay/cancel",
                    "/pay/success"
            );
            System.out.println(testPayment.getState());
            System.out.println(testPayment.toJSON());
        } catch (PayPalRESTException e) {
            fail();
        }
        assertNotNull(testPayment);
    }

    @Test
    public void executePayment() {
        try {
            paypalService.executePayment(PAYMENT_ID, PAYER_ID);
        } catch (Exception e) {
            assertTrue(e instanceof PayPalRESTException);
            assertTrue((e).getMessage().contains(
                    "\"message\":\"Payer has not approved payment\""));
        }
    }
}
