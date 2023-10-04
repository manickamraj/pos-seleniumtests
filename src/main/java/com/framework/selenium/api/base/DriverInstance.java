package com.framework.selenium.api.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverInstance{
	private static final ThreadLocal<RemoteWebDriver> remoteWebdriver = new ThreadLocal<RemoteWebDriver>();
	private static final ThreadLocal<WebDriverWait> wait = new  ThreadLocal<WebDriverWait>();
	public static Properties prop;

	public void setWait() {
		wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
	}

	public WebDriverWait getWait() {
		return wait.get();
	}

	public void setDriver(String browser, boolean headless, String remote, String grid) throws MalformedURLException {		
		switch (browser) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized"); 
			options.addArguments("--disable-notifications"); 
			options.addArguments("--incognito");
		  	DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName("chrome");
			dc.setPlatform(Platform.UNIX);
			options.merge(dc);
			if(remote.equalsIgnoreCase("true")) {
			remoteWebdriver.set(new RemoteWebDriver(new URL(grid), options));
			}
			else {
			remoteWebdriver.set(new ChromeDriver());
			}
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			DesiredCapabilities desiredCap = new DesiredCapabilities();
			desiredCap.setBrowserName("firefox");
			desiredCap.setPlatform(Platform.UNIX);
			firefoxOptions.merge(desiredCap);
			if(remote.equalsIgnoreCase("true")) {
				remoteWebdriver.set(new RemoteWebDriver(new URL(grid), firefoxOptions));
				}
				else {
				remoteWebdriver.set(new FirefoxDriver());
				}
			//remoteWebdriver.set(new RemoteWebDriver(new URL("http://20.244.24.182:4444/wd/hub"), firefoxOptions));
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			DesiredCapabilities dCap = new DesiredCapabilities();
			dCap.setBrowserName("edge");
			dCap.setPlatform(Platform.UNIX);
			edgeOptions.merge(dCap);
			if(remote.equalsIgnoreCase("true")) {
				remoteWebdriver.set(new RemoteWebDriver(new URL(grid), edgeOptions));
				}
				else {
				remoteWebdriver.set(new EdgeDriver());
				}
			break;	
		case "ie":
			remoteWebdriver.set(new InternetExplorerDriver());
		default:
			break;
		}
	}
	public RemoteWebDriver getDriver() {
		return remoteWebdriver.get();
	}
	
}
