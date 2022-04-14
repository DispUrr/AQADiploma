package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLData;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DebitPaymentServiceTest {
    @BeforeEach
    public void openPage() {
        String url = System.getProperty("sut.url");
        open(url);
    }

    @AfterEach
    public void cleanDB() {
        SQLData.clearTables();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    @DisplayName("Покупка по карте, операция прошла успешно, в БД появилась запись со статусом APPROVED")
    void shouldConfirmPaymentWithValidCard() {
        val startPage = new StartPage();
        val payment = startPage.pickDebitWay();
        payment.fillingOfFormFields(DataHelper.getValidCard());
        payment.waitNotificationSuccessVisible();
        assertEquals("APPROVED", SQLData.findPaymentStatus());
    }

    @Test
    @DisplayName("Покупка по карте, операция отклонена банком, в БД появилась запись со статусом DECLINED")
    void shouldDeniedPaymentWithDeclinedCard() {
        val startPage = new StartPage();
        val payment = startPage.pickDebitWay();
        payment.fillingOfFormFields(DataHelper.getDeclinedCard());
        payment.waitNotificationFailedVisible();
        assertEquals("DECLINED", SQLData.findPaymentStatus());

    }



}