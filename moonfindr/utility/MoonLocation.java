package com.tanion.aston.rovery.moonfindr.utility;

import java.util.Calendar;

public class MoonLocation {
	public static final String TAG = "MoonLocation";

	private double mAge; //Moon's age
	private double mDistance; //Moon's distance in earth radii
	private double mLatitude; //Moon's ecliptic latitude
	private double mLongitude; //Moon's ecliptic longitude
	private String mPhase;
	private String mZodiac;

	public MoonLocation(Calendar calendar) {

		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
	     
	     //calculate the Julian date
		int YY = year - (int) Math.floor((12 - month) / 10);
		int MM = month + 9;
			if (MM >= 12) {
				MM = MM - 12;}

		int k1 = (int) Math.floor(365.25 * (YY + 4712));
		int k2 = (int) Math.floor(30.6 * MM + 0.5);
		int k3 = (int) Math.floor(Math.floor((YY / 100) + 49) * 0.75) - 38;

		int JD = k1 + k2 + day + 59;
        //Gregorian calendar
        if (JD > 2299160) JD = JD - k3;
			
			
        //calculate the age of the moon
		double IP = normalize((float) ((JD - 2451550.1) / 29.530588853));
		mAge =  (IP *29.53);

		if   (mAge <  1.84566) mPhase = "New moon";
		else if (mAge <  5.53699) mPhase = "Waxing crescent";
		else if (mAge <  9.22831) mPhase = "First quarter";
		else if (mAge < 12.91963) mPhase = "Waxing gibbous";
		else if (mAge < 16.610969) mPhase = "Full moon";
		else if (mAge < 20.302289) mPhase = "Waning gibbous";
		else if (mAge < 23.99361) mPhase = "Last quarter";
		else if (mAge < 27.684939) mPhase = "Waning crescent";
		else mPhase = "New moon";
			 
		IP *= 2 * Math.PI; //Convert phase to radian

		// Calculating the distance of the moon from the earth
		double DP = 2 * (float) Math.PI * normalize((float) ((JD - 2451562.2) / 27.55454988));

		mDistance = (60.4 - 3.3 * Math.cos(DP) - 0.6 *
                Math.cos(2 * IP - DP) - 0.5 * Math.cos(2 * IP));
				
		// Calculating the latitude of the moon
		double NP = 2 * (float) Math.PI * normalize((float) ((JD - 2451565.2) / 27.212220817));
		mLatitude = (5.1*Math.sin(NP));

		// Calculating the longitude of the moon
		double RP = normalize((float) ((JD - 2451555.8) / 27.321582241));
		mLongitude = (360* RP + 6.3*Math.sin(DP) + 1.3*Math.sin( 2* IP - DP) + 0.7*Math.sin( 2* IP));

		if (mLongitude <  33.18) mZodiac = "Pisces";
		else if (mLongitude <  51.16) mZodiac = "Aries";
		else if (mLongitude <  93.44) mZodiac = "Taurus";
		else if (mLongitude < 119.48) mZodiac = "Gemini";
		else if (mLongitude < 135.30) mZodiac = "Cancer";
		else if (mLongitude < 173.34) mZodiac = "Leo";
		else if (mLongitude < 224.17) mZodiac = "Virgo";
		else if (mLongitude < 242.57) mZodiac = "Libra";
		else if (mLongitude < 271.26) mZodiac = "Scorpio";
		else if (mLongitude < 302.49) mZodiac = "Sagittarius";
		else if (mLongitude < 311.72) mZodiac = "Capricorn";
		else if (mLongitude < 348.58) mZodiac = "Aquarius";
		else mZodiac = "Pisces";
			 
	}
	
	public double getLongitude() {
		return mLongitude;
	}
	
	public double getLatitude() {
		return mLatitude;
	}

    public double getDistance() {
        return mDistance;
    }

    public String getPhase() {
        return mPhase;
    }

    public double getAge() {
        return mAge;
    }

    // normalize values to range 0..1
	public static float normalize(float v) {
		float newV;
		newV = v - (float) Math.floor(v);
		if (newV < 0) {newV += 1;}
		return newV;
	}
}
