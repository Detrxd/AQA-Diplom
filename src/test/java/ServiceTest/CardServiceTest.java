package ServiceTest;

import DataHelperInstrument.DataHelper;
import PageObject.PurchaseCardPage;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

public class CardServiceTest {
    PurchaseCardPage PurchaseCardPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        PurchaseCardPage = new PurchaseCardPage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessBuyTourTest() { //Покупка тура с апрувнутой карты со страницы оплаты//
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyCreditTourTest() { //Покупка тура с апрувнутой карты со страницы оформления кредита//
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorBuyTourTest() { // Аналог первого теста, но карта заблокирована//
        var paymentPage = PurchaseCardPage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    void shouldGetErrorBuyCreditTourTest() { // Аналог второго теста, но карта заблокирована//
        var creditPage = PurchaseCardPage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.unsuccessfulSending(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    //Граничные значения//

    @Test
    void shouldSuccessBuyTourWith59MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(59);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWith60MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(60);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(61);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(1);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(0);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithExpiredCardOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(-1);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith59MonthExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(59);
        creditPage.successfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWith60MonthExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(60);
        creditPage.successfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(61);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(1);
        creditPage.successfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(0);
        creditPage.successfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithExpiredCardOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(-1);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith01MonthValueOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith12MonthValueOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith13MonthValueOnPaymentPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith01MonthValueOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSending(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith12MonthValueOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSending(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith13MonthValueOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    // Отправка пустой формы //

    @Test
    void shouldRequireFillPaymentFormFieldsTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        paymentPage.sendClearForm();
    }

    @Test
    void shouldRequireFillCreditFormFieldsTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        creditPage.sendClearForm();
    }

    // Отправка пустой формы в различных полях сервиса //
    @Test
    void shouldRequireCardNumberOnPayPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireMonthOnPayPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireYearOnPayPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireCardOwnerOnPayPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireSecretCodeOnPayPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireCardNumberOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldRequireMonthOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldRequireYearOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldRequireCardOwnerOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    void shouldRequireSecretCodeOnCreditPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        creditPage.inputInvalidError();
    }

    // Проверка переключателей //

    @Test
    void shouldSwitchFromPayToCreditPageTest() {
        var paymentPage = PurchaseCardPage.payForTour();
        var creditPage = paymentPage.buyWithCredit();
    }

    @Test
    void shouldSwitchFromCreditToPayPageTest() {
        var creditPage = PurchaseCardPage.buyWithCredit();
        var paymentPage = creditPage.TourPay();
    }

}

