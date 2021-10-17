package ServiceTest;

import DataHelperInstrument.DataHelper;
import PageObject.shouldIncludePurchaseCardPage;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class CardServiceTest {
    shouldIncludePurchaseCardPage VerifyPurchaseCardPage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp() {
        open("http://localhost:8080/");
        VerifyPurchaseCardPage = new shouldIncludePurchaseCardPage();
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
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyCreditTourTest() { //Покупка тура с апрувнутой карты со страницы оформления кредита//
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorBuyTourTest() { // Аналог первого теста, но карта заблокирована//
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.shouldUnsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    void shouldGetErrorBuyCreditTourTest() { // Аналог второго теста, но карта заблокирована//
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.shouldUnsuccessfulSending(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    //Граничные значения//

    @Test
    void shouldSuccessBuyTourWith59MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(59);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWith60MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(60);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(61);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(1);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(0);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithExpiredCardOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(-1);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith59MonthExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(59);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWith60MonthExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(60);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(61);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(1);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(0);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithExpiredCardOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(-1);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith01MonthValueOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith12MonthValueOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.shouldSuccessfulSendForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith13MonthValueOnPaymentPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith01MonthValueOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldSuccessBuyTourWith12MonthValueOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.verifySuccessfulSending(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    void shouldGetErrorIfBuyTourWith13MonthValueOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    // Отправка пустой формы //

    @Test
    void shouldRequireFillPaymentFormFieldsTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        paymentPage.shouldSendClearForm();
    }

    @Test
    void shouldRequireFillCreditFormFieldsTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        creditPage.shouldSendClearForms();
    }

    // Отправка пустой формы в различных полях сервиса //
    @Test
    void shouldRequireCardNumberOnPayPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireMonthOnPayPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireYearOnPayPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireCardOwnerOnPayPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireSecretCodeOnPayPageTest() {
        var paymentPage = VerifyPurchaseCardPage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        paymentPage.inputInvalidError();
    }

    @Test
    void shouldRequireCardNumberOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldRequireMonthOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldRequireYearOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldRequireCardOwnerOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        creditPage.checkInputInvalidError();
    }

    @Test
    void shouldRequireSecretCodeOnCreditPageTest() {
        var creditPage = VerifyPurchaseCardPage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        creditPage.checkInputInvalidError();
    }

    // Проверка переключателей //

    @Test
    void shouldSwitchFromPayToCreditPageTest() {
       SelenideElement header = $("[class = 'heading heading_size_l heading_theme_alfa-on-white']");
       SelenideElement tourConditions =
                $("[class='grid-row grid-row_gutter-mobile-s_16 grid-row_gutter-desktop-m_24 grid-row_justify_between grid-row_theme_alfa-on-white']");
       SelenideElement payButton = $(byText("Купить"));
    }

    @Test
    void shouldSwitchFromCreditToPayPageTest() {
        SelenideElement header = $("[class = 'heading heading_size_l heading_theme_alfa-on-white']");
        SelenideElement tourConditions =
                $("[class='grid-row grid-row_gutter-mobile-s_16 grid-row_gutter-desktop-m_24 grid-row_justify_between grid-row_theme_alfa-on-white']");

        SelenideElement creditButton = $(byText("Купить в кредит"));
    }

}

