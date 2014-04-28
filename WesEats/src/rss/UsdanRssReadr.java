package rss;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UsdanRssReadr {

	private URL url;
	
	/**
	 * get url
	 * @return url
	 */
	public URL getUrl(){
		return this.url;
	}
/**
 * This turns a string into a URL object
 * @param webAddress
 * @return url
 */
	
	public URL setUrl(String webAddress) {
		assert webAddress != null;
		try {
			this.url = new URL(webAddress);
			return url;
		} catch (MalformedURLException e) {
			System.out.println("failed to accept url");
			return url;
		}
	}
/**
 * this is a specialized reader for the Bon-appetite, auto generated RSS feed.
 *  invokes the cleanr class to scrub the feed of mistakes and unnecessary tagging.
 * @param inputURL
 * @return menuItems (the weekly menu in string format)
 * @throws Exception
 */
	public String readFromFeed(URL inputURL) throws Exception {
		URL u = null;
		try {
			u = new URL("http://legacy.cafebonappetit.com/rss/menu/332");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert u == inputURL;
		int count = 0;
		String menuItems = "";
		RssCleanr cleanr = new RssCleanr();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputURL.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {

			if (cleanr.asciiToText(inputLine).length() > 5) {
				if ((cleanr.asciiToText(inputLine)).substring(0, 6)
						.equals("<title")) {
					count++;
				}
				if (count > 1)
					menuItems = menuItems + "\n" + cleanr.asciiToText(inputLine);

			}
		}
		in.close();
		return cleanr.removeTags(menuItems);
	}

	/**
	 * Print's weekly menu for Usdan.
	 */
	public String getMenu() {
		try {
			return (readFromFeed(setUrl("http://legacy.cafebonappetit.com/rss/menu/332")));
		} catch (Exception e) {
			System.out.println("wrong URL system crash");
			return null;
		}
	}
	/**
	 * return the daily menu for usdan based off system time.
	 * @return
	 */
	public String getTodaysMenu(){
		
		
		String weekMenu = getMenu();

		DateFormat todayFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
		DateFormat dayFormat = new SimpleDateFormat("EEE");
		Date dateToday = new Date();
		String today = todayFormat.format(dateToday);
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date dateTomorrow = calendar.getTime();
		
		String tomorrow = todayFormat.format(dateTomorrow);
		String day = todayFormat.format(dateToday);
		day = day.substring(0,3);
		String dailyMenu = "";

		String Temp = "";
		while (weekMenu.length() > today.length()){
			if(weekMenu.substring(0,today.length()).equals(today)){
				Temp = weekMenu;
				if(day.equals("Sun")){
					return weekMenu;
				}
				else{
					
					
					while(weekMenu.length() > tomorrow.length()){

						dailyMenu = dailyMenu + weekMenu.substring(0,1);
						
						weekMenu = weekMenu.substring(1);
						
						if(weekMenu.substring(0,tomorrow.length()).equals(tomorrow)){
							return dailyMenu;
						}
						
						
					}		
				}
			}
			else{
				weekMenu = weekMenu.substring(1);
			
			}
			
		}
		
		return Temp.replace(dailyMenu,"");
	}



}
