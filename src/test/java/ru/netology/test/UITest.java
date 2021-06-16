package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SqlUtils;
import ru.netology.page.TripPage;

import static com.codeborne.selenide.Selenide.open;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UITest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        String appUrl = System.getProperty("app.url");
        open(appUrl);
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessWithValidDebitCard() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByDebitCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        cardData.fillCardInformationForSelectedWay(validCardInformation);
        cardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        val paymentAmount = SqlUtils.getPaymentAmount(paymentId);
        assertEquals("APPROVED", statusForPaymentByDebitCard);
        assertEquals("4500000", paymentAmount);

    }

    @Test
    void shouldSuccessWithValidCreditCard() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getValidCardInformation();
        cardData.fillCardInformationForSelectedWay(validCardInformation);
        cardData.checkIfPaymentSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertEquals("APPROVED", statusForPaymentByCreditCard);
    }

    @Test
    void shouldNotSuccessWithInvalidDebitCard() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByDebitCard();
        val invalidCardInformation = DataHelper.getInvalidCardInformation();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByDebitCard = SqlUtils.getStatusForPaymentByDebitCard(paymentId);
        assertThat(statusForPaymentByDebitCard, equalTo("DECLINED"));
    }

    @Test
    void shouldNotSuccessWithInvalidCreditCard() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val validCardInformation = DataHelper.getInvalidCardInformation();
        cardData.fillCardInformationForSelectedWay(validCardInformation);
        cardData.checkIfPaymentNotSuccessful();
        val paymentId = SqlUtils.getPaymentId();
        val statusForPaymentByCreditCard = SqlUtils.getStatusForPaymentByCreditCard(paymentId);
        assertThat(statusForPaymentByCreditCard, equalTo("DECLINED"));
    }

    @Test
    void shouldNotSuccessWithWrongCardNumber() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongLongCardNumber();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithShortestCardNumber() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithShortestCardNumber();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongMonth() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongMonth();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongYear() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYear();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongYearFromOneNumber() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongYearWithOneNumber();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongCVC() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongCvc();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithWrongName() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithWrongHolderName();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }

    @Test
    void shouldNotSuccessWithoutName() {
        val tripPage = new TripPage();
        val cardData = tripPage.selectBuyByCreditCard();
        val invalidCardInformation = DataHelper.getCardInformationWithoutName();
        cardData.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData.checkIfWrongFormatOfField();
        val cardData2 = tripPage.selectBuyByDebitCard();
        cardData2.fillCardInformationForSelectedWay(invalidCardInformation);
        cardData2.checkIfWrongFormatOfField();
    }
}
