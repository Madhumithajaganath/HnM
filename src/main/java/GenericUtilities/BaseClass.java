package GenericUtilities;

import java.io.File;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.objectRepo.CartPage;
import com.objectRepo.FollowUsPage;
import com.objectRepo.InboxPage;
import com.objectRepo.LoginPage;
import com.objectRepo.NavigationPage;
import com.objectRepo.PopularcategoryPage;
import com.objectRepo.ProductPage;
import com.objectRepo.SearchPage;
import com.objectRepo.SustainabilityPage;
import com.objectRepo.VerificationPage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseClass {

	public AndroidDriver driver;
	static public AndroidDriver sdriver;
	public DriverUtility dutil;
	public GestureUtility gutil;
	public FileUtility futil = new FileUtility();
	public AppiumDriverLocalService service;
	public CartPage cp;
	public FollowUsPage fup;
	public InboxPage ip;
	public LoginPage lp;
	public SearchPage sp;
	public SustainabilityPage susp;
	public PopularcategoryPage pcp;
	public ProductPage pp;
	public NavigationPage np;
	public VerificationPage vp;
	
	
	@BeforeSuite
	public void startServer() {
		
	//File f = new File("C:\\Users\\HI\\AppData\\Roaming\\npm\\node_modules\\appium\\lib\\main.js");
	File f=new File("C:\\Users\\sys\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js");
 	
		
		service = new AppiumServiceBuilder().withAppiumJS(f).
				withIPAddress("127.0.0.1").usingPort(4723).
				withTimeout(Duration.ofSeconds(300)).build();
		
		service.start();
	}
	
	@BeforeClass
	public void startApp() throws Throwable {
		DesiredCapabilities dc = new DesiredCapabilities();
		
		String platform = futil.dataFromPropertyFile("PLATFORM_NAME");
		String automation = futil.dataFromPropertyFile("AUTOMATION_NAME");
		
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
		//dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Madhumitha jaganath");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy M32 5G");
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, automation);
		//dc.setCapability(MobileCapabilityType.UDID, "RZ8T31JR73N");
		dc.setCapability(MobileCapabilityType.UDID, "RZCRA06JH3F");

		
		dc.setCapability("appPackage", "com.hm.goe");
		dc.setCapability("appActivity", ".app.home.HomeActivity");
		
		URL u = new URL("http://localhost:4723");
		driver = new AndroidDriver(u,dc);
	
		sdriver=driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		gutil = new GestureUtility(driver);
		dutil = new DriverUtility(driver);
		cp = new CartPage(driver);
		fup = new FollowUsPage(driver);
		ip = new InboxPage(driver);
		lp = new LoginPage(driver);
		sp = new SearchPage(driver);
		susp = new SustainabilityPage(driver);
		pcp=new PopularcategoryPage(driver);
		pp=new ProductPage(driver);
		np= new NavigationPage(driver);
		vp=new VerificationPage(driver);
	}
	
	@BeforeMethod
	public void login() {
		lp.login(gutil, "India/English");
	}
	
	@AfterClass
	public void closeApp() {
		driver.quit();
	}
	
	@AfterSuite
	public void stopServer() {
		service.stop();
	}
	
}
