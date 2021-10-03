package ServiceTest;

import DataHelperInstrument.DataBase;
import DataHelperInstrument.DataHelper;
import PageObject.PurchaseCardPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseTest {

    PurchaseCardPage tourPurchasePage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        tourPurchasePage = new PurchaseCardPage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    //
    @Test
    void shouldNotSaveCreditIdOnPayPageTest() throws InterruptedException {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.anyNotification();
        assertEquals("null", DataBase.getCreditId());
    }

    @Test
    void shouldNotSaveCreditIdOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.anyNotification();
        assertEquals("null", DataBase.getCreditId());
    }


    @Test
    void shouldDeclinePaymentsWithDeclinedCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
        creditPage.anyNotification();
        assertEquals("DECLINED", DataBase.getCreditStatus());
    }

    @Test
    void shouldDeclinePaymentsWithDeclinedCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
        paymentPage.anyNotification();
        assertEquals("DECLINED", DataBase.getPaymentStatus());
    }

    @Test
    void shouldApprovePaymentsWithApprovedCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.anyNotification();
        assertEquals("APPROVED", DataBase.getPaymentStatus());
    }

    @Test
    void shouldApprovePaymentsWithApprovedCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.anyNotification();
        assertEquals("APPROVED", DataBase.getCreditStatus());
    }

}
