import java.util.concurrent.TimeUnit
import groovy.grape.Grape

//@Grab(group = 'org.seleniumhq.selenium', module = 'selenium-chrome-driver', version = '2.33.0')
//@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.33.0")
@Grapes([

@Grab("org.gebish:geb-core:0.9.0"),

@Grab("org.gebish:geb-spock:0.9.0"),

@Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.33.0"),

@Grab("org.seleniumhq.selenium:selenium-chrome-driver:2.33.0"),

@Grab("org.seleniumhq.selenium:selenium-support:2.26.0"),

@Grab( group='org.spockframework',

module='spock-core',

version='0.3'

),

@Grab(group='org.gebish', module='geb-implicit-assertions', version='0.9.0')

])

import geb.Browser

import geb.spock.GebReportingSpec

import org.openqa.selenium.chrome.*

import spock.lang.Specification

import geb.navigator.NonEmptyNavigator

import geb.navigator.factory.*

def call(String coverage = null, String version="release/3.8.2") {
   sh '''
   echo test
   '''
}
