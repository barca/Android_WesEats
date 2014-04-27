package rss;

public class RssCleanr {

	/**
	 * this replaces specific html ASCII codes with their appropriate value, 
	 * these were chosen based off their likeliness to appear within the bon-appetite website over the course of a few weeks
	 * @param s
	 * @return
	 */
	public String asciiToText(String s){
		
	
		assert s != null;
		
		if (s.length() > 3) {
			if (s.substring(0, 4).equals("&lt;"))
				return "<" + asciiToText(s.substring(4));
			else if (s.substring(0, 4).equals("&gt;"))
				return ">" + asciiToText(s.substring(4));
			if (s.length() > 4)
				if (s.substring(0, 5).equals("&amp;"))
					return "&" + asciiToText(s.substring(5));
			if (s.length() > 5)
				if (s.substring(1, 5).equals("bsp;"))
					return " " + asciiToText(s.substring(5));

			return removeAmp(s.substring(0, 1)) + removeAmp(asciiToText(s.substring(1)));

		}
		return removeAmp(s);
		
		
		
		
	}

	/**
	 * removes any and all ampersand figures(&)
	 * @param s
	 * @return s
	 */
	private String removeAmp(String s) {
		return s.replaceAll("[&]", "");
	}
	
	/**
	 * removes anything between a < and > symbol
	 * @param s
	 * @return s
	 */
	public String removeTags(String s) {
		assert s != null;
		return s.replaceAll("<.*?>", "");
	}
	
	

}
