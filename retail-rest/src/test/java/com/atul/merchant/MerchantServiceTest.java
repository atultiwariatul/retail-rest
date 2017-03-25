package com.atul.merchant;

import com.atul.merchant.domain.Merchant;
import com.atul.merchant.service.MerchantService;
import com.atul.retail.configs.MongoConfig;
import com.atul.retail.exceptions.ServiceException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfig.class})
@SpringBootTest
public class MerchantServiceTest {
    /**
     * Declaration of logger for Class.
     */
    static private final XLogger logger = XLoggerFactory
            .getXLogger(MerchantServiceTest.class);

    @Autowired
    MerchantService merchantService;

    private final String merchantId="5754f825300461fa0c9e973a";
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.info("Coming in setup before class");
    }

    @Test
    public void testSaveProduct() {
        merchantService.saveMerchant(new MerchantDataFactory().getMerchant());
    }

    @Test
    public void testFindMerchantDetails() {
        Merchant merchant = null;
        try {
            merchant = merchantService.getMerchantDetails(merchantId);
            Assert.notNull(merchant);
        } catch (ServiceException e) {
            logger.catching(e);
        }
        logger.info("Printing Merchant {} ", merchant);
    }
}
