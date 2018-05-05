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

        // Заполнение полей данными
        WebElement surname = driver.findElement(By.xpath
                (".//label[text() = 'Фамилия']/following-sibling::input"));
        surname.sendKeys("Васильев");
        WebElement name = driver.findElement(By.xpath
                (".//input[@name = 'FirstName']"));
        name.sendKeys("Василий");
        WebElement patronymic = driver.findElement(By.xpath
                (".//input[@name = 'MiddleName']"));
        patronymic.sendKeys("Васильевич");
        WebElement region = driver.findElement(By.xpath
                (".//select[@name = 'Region']"));
        region.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath
                (".//option[@value = '77']"))));
        WebElement region77 = driver.findElement(By.xpath(".//option[@value = '77']"));
        region77.click();
        WebElement phoneNumber = driver.findElement(By.xpath
                (".//label[text() = 'Телефон']/following-sibling::input"));
        phoneNumber.sendKeys("9161505050");
        WebElement email = driver.findElement(By.xpath
                (".//label[text() = 'Эл. почта']/following-sibling::input"));
        email.sendKeys("qwertyqwerty");
        WebElement contactDate = driver.findElement(By.xpath
                (".//input[@name='ContactDate']"));
        contactDate.sendKeys("22052018");
        WebElement commentary = driver.findElement(By.xpath
                (".//textarea[@name='Comment']"));
        commentary.sendKeys("testtesttest");
        WebElement checkbox = driver.findElement(By.className("checkbox"));
        checkbox.click();

        Assert.assertEquals("Васильев", driver.findElement(By.name("LastName")).getAttribute("value"));
        Assert.assertEquals("Василий", driver.findElement(By.name("FirstName")).getAttribute("value"));
        Assert.assertEquals("Васильевич", driver.findElement(By.name("MiddleName")).getAttribute("value"));
        Assert.assertEquals("77", driver.findElement(By.xpath(".//select[@name = 'Region']")).getAttribute("value"));
        Assert.assertEquals("+7 (916) 150-50-50", driver.findElement(By.xpath
                (".//label[contains(text(), 'Телефон')]/following-sibling::input")).getAttribute("value"));
        Assert.assertEquals("qwertyqwerty", driver.findElement(By.name("Email")).getAttribute("value"));
        Assert.assertEquals("22.05.2018", driver.findElement(By.name("ContactDate")).getAttribute("value"));
        Assert.assertEquals("testtesttest", driver.findElement(By.name("Comment")).getAttribute("value"));

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
