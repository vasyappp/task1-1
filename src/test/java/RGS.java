import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RGS {
    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.rgs.ru/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void rgs_test_1() {
        WebElement webElement = driver.findElement(By.xpath(".//a[contains(text(),'Страхование')]"));
        webElement.click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(".//a[contains(text(),'ДМС')]"))));

        WebElement dms = driver.findElement(By.xpath(".//a[contains(text(),'ДМС')]"));
        dms.click();

        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(".//div[@class='page-header']/span[text() != '']"))));

        WebElement pageHeader = driver.findElement(By.xpath
                (".//div[@class='page-header']/span[text() != '']"));
        String textPageHeader = pageHeader.getText();
        Assert.assertTrue
                ("На странице нет заголовка с текстом \"Добровольное медицинское страхование\"",
                        textPageHeader.contains("Добровольное медицинское страхование"));

        WebElement sendRequest = driver.findElement(By.xpath
                (".//a[contains(text(), 'Отправить заявку')]"));
        sendRequest.click();

        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(".//div[@class='modal-header']//b[text() != '']"))));

        WebElement modalHeader = driver.findElement(By.xpath
                (".//div[@class='modal-header']//b[text() != '']"));
        String textModalHeader = modalHeader.getText();
        Assert.assertTrue
                ("На странице нет заголовка с текстом \"Заявка на добровольное медицинское страхование\"",
                        textModalHeader.contains("Заявка на добровольное медицинское страхование"));

        WebElement email = driver.findElement(By.xpath
                (".//label[text() = 'Эл. почта']/following-sibling::input"));
        email.sendKeys("qwertyqwerty");
        //WebElement emailInput = driver.findElement(By.xpath
        //        (".//label[text() = 'Эл. почта']/following-sibling::input//*[text()!='']"));
        //String textEmail = email.getText();
        //Assert.assertTrue("Email не отобразился", textEmail.contains("qwertyqwerty"));

        WebElement checkbox = driver.findElement(By.className("checkbox"));
        checkbox.click();

        WebElement send = driver.findElement(By.id("button-m"));
        send.click();

        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath(".//*[contains(text(), 'Эл. почта')]/following::*//span[@class='validation-error-text']"))));

        WebElement error = driver.findElement(By.xpath
                (".//*[contains(text(), 'Эл. почта')]/following::*//span[@class='validation-error-text']"));
        String textError = error.getText();
        Assert.assertTrue("Текст ошибки не соответствует указанному в тесте",
                textError.contains("Введите адрес электронной почты"));
    }
}
