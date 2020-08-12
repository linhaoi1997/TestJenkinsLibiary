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
 def link = context.expand( 'www.baidu.com'); 

 driver.get(link)
 brower.maximize_window() 
 brower.save_screenshot('a.png') 
 
 driver.quit()
 sh '''
 ls
 '''
}
