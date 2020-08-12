import java.util.concurrent.TimeUnit

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver


//set up the driver
 System.setProperty("webdriver.chrome.driver","./../tools/chromedriver.exe")

//Define the driver
 def WebDriver driver = new ChromeDriver()
 driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

//And now use this to visit Google
 def link = context.expand( 'www.baidu.com'); 

 driver.get(link)

 driver.quit()