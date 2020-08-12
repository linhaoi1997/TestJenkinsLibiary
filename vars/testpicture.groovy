import java.util.concurrent.TimeUnit
import groovy.grape.Grape

@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-chrome-driver', version = '2.33.0')
//@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.33.0")

def call(String coverage = null, String version="release/3.8.2") {
   sh '''
   echo test
   '''
}
