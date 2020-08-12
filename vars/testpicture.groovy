import java.util.concurrent.TimeUnit
import groovy.grape.Grape

//@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-chrome-driver', version = '2.33.0')
//@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.33.0")
@Grapes([

    @Grab(group='org.openqa.selenium.webdriver', module='webdriver-parent', version='0.6.1039', type='pom')
    

])


import java.util.concurrent.TimeUnit

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver


def call(String coverage = null, String version="release/3.8.2") {
   sh '''
   echo test
   '''
   System.setProperty("webdriver.chrome.driver","./../tools/chromedriver.exe")

//Define the driver
 def WebDriver driver = new ChromeDriver()
 driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

//And now use this to visit Google
 def link = context.expand( 'url'); 

 driver.get(link)

 def username = context.expand( '${#Project#username}' )

//Find the text input element by its name
 driver.findElement(By.name("email")).sendKeys(username)



//Find the password element by its name
 driver.findElement(By.name("password")).sendKeys("asdfghjkl")

//Click the Login button 
 driver.findElement(By.name("sbutton")).click();

//Enter CVV2
 driver.findElement(By.id("cvv")).sendKeys("672");

 driver.findElement(By.name("termsAndConditionsAccepted")).click();

 driver.findElement(By.xpath("//*[@id="pay-now"]")).click();


//log.info("Page title is:" + driver.getTitle())

 assert (driver.getTitle()=="Mobile Phones | Contract Phones | Cheap Mobile Phone Deals & SIMs")
//Close the browser
 driver.quit()
}
