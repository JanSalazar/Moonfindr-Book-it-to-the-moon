package com.tanion.aston.rovery.moonfindr.utility;

import java.util.Calendar;

public class MoonAngle {
	private  static final String TAG = "MoonAngle";

	private MoonLocation eclipticMoon;
	private static final double DTOR = Math.PI/180;
	private static final double RTOD = 180/Math.PI;
	private static final double A = 6378.3800;
	//private static final double B = 6356.9120;
	private static final double F = 1/297;
	private static final double RSAT = Math.pow((6028.82*((24*60)-4)), 2/3);
	
	private double ESHEIGHT; 	//height of your location
	private double ESLAT; 		//latitude of your location (+North)
	private double ESLONG; 		//longitude of your location (+East)
	private double SATLAT; 		//ecliptic latitude of the moon (+North)
	private double SATLONG; 	//ecliptic longitude of the moon (+East)

	private double AZ, EL, RANGE;
	

	public MoonAngle(double ESH, double ESLA, double ESLO) {
		eclipticMoon = new MoonLocation(Calendar.getInstance());
		ESHEIGHT = ESH;
		ESLAT = ESLA;
		ESLONG = ESLO;
		SATLAT = eclipticMoon.getLatitude();
		SATLONG = eclipticMoon.getLongitude();

		double XS = RSAT * Math.cos(DTOR * SATLAT) * Math.cos(DTOR * SATLONG);
		double XS2 = RSAT * Math.cos(-SATLAT * DTOR) * Math.cos(SATLONG * DTOR);
		double YS = RSAT*Math.cos(DTOR*SATLAT)*Math.sin(DTOR*SATLONG);
		double YS2 = RSAT*Math.cos(-SATLAT*DTOR)*Math.sin(SATLONG*DTOR);
		double ZS = RSAT*Math.sin(DTOR*SATLAT);
		double ZS2 = -RSAT*Math.sin(DTOR*SATLAT);
		double EE = 2*F - Math.pow(F, 2);

		double BETA = Math.atan((1 - EE) * Math.tan(ESLAT * DTOR));

		double RHO = A * (1 - F) / Math.sqrt(1 - (2 - F) * F * Math.cos(ESLAT * DTOR) * Math.cos(ESLAT * DTOR));

		double TXS = (XS * Math.cos(ESLONG * DTOR)+ YS * Math.sin(ESLONG * DTOR) -
				RHO * Math.cos(BETA)) * Math.sin(ESLAT * DTOR) - (ZS - RHO * Math.sin(BETA)) *
				Math.cos(ESLAT * DTOR);

		double TXS2 = (XS2 * Math.cos(ESLONG * DTOR) + YS2 * Math.sin(ESLONG * DTOR) - RHO *
                Math.cos(BETA)) * Math.sin(ESLAT * DTOR) - (ZS2 - RHO * Math.sin(BETA)) *
                Math.cos(ESLAT * DTOR);

		double TYS = - XS * Math.sin(ESLONG * DTOR) + YS * Math.cos(ESLONG * DTOR);

		double TYS2 = - XS2 * Math.sin(ESLONG * DTOR) + YS2 * Math.cos(ESLONG * DTOR);

		double TZS = (XS * Math.cos(ESLONG * DTOR) + YS * Math.sin(ESLONG * DTOR) - RHO *
                Math.cos(BETA)) * Math.cos(ESLAT * DTOR) + (ZS - RHO * Math.sin(BETA)) *
                Math.sin(ESLAT * DTOR) - (ESHEIGHT / 1000);

		double TZS2 = (XS2 * Math.cos(ESLONG * DTOR) + YS2 * Math.sin(ESLONG * DTOR) - RHO *
                Math.cos(BETA)) * Math.cos(ESLAT * DTOR) + (ZS2 - RHO * Math.sin(BETA)) *
                Math.sin(ESLAT * DTOR) - (ESHEIGHT / 1000);

		double DIST = Math.sqrt(TXS * TXS + TYS * TYS + TZS * TZS);
		double DIST2 = Math.sqrt(TXS2 * TXS2 + TYS2 * TYS2 + TZS2 * TZS2);

		AZ = 180 - RTOD * atan2(TXS, TYS);
		EL = Math.asin(TZS / DIST) * RTOD;
		RANGE = Math.max(DIST, DIST2);
	}

	public static double atan2 (double x, double y) {
		double tmp;
		if (y == 0) {
			if (x > 0) {tmp = 0;} 
			else {
				if (x < 0) {tmp = Math.PI;} 
				else {tmp = 1;}
			}
			
		} else {
			if (y > 0) {tmp = (Math.PI/2) - Math.atan(x/y);} 
			else {tmp = -(Math.PI/2) - Math.atan(x/y);}
		}
		return tmp;
	}

	public String toString() {
		return ("Azimuth Angle (degrees):		" + AZ
			+ "\nElevation Angle (degrees):		" + EL
			+ "\nMaximum Range (km):		"	+ RANGE);
	}

	public double getAzimuthAngle() {
		return AZ;
	}

	public double getElevationAngle() {
		return EL;
	}
}