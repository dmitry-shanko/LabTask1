package com.epam.testapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public enum DateFormatter 
{
	;
	private static final Logger log = Logger.getLogger(com.epam.testapp.util.DateFormatter.class);

	/**
	 * This method convert <h1>util.Date</h1> into  <h1>sql.Date</h1>
	 * @param utilDate java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate) 
	{
		long value = utilDate.getTime();
		java.sql.Date sqlDate = new java.sql.Date(value);
		return sqlDate;
	}
	/**
	 * This method convert  <h1>sql.Date</h1> into   <h1>util.Date</h1>
	 * @param sqlDate java.sql.Date
	 * @return java.util.Date
	 */
	public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) 
	{
		java.util.Date utilDate = sqlDate;
		return utilDate;
	}

	/**
	 * 
	 * @param date String with date 
	 * @param dateFormat format of date
	 * @param sqlDateFormat format of date in database
	 * @return java.sql.Date
	 * @throws DateConverterException
	 */
	public static java.sql.Date stringToSqlDate(String date, String dateFormat, String sqlDateFormat)
	{
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		java.util.Date utilDate = new java.util.Date();
		try 
		{
			utilDate = formatter.parse(date);
		} 
		catch (ParseException e) 
		{
			log.error("Can't convert data");
		} 
		DateFormat format = new SimpleDateFormat(sqlDateFormat);
		String stringDate = format.format(utilDate);
		java.sql.Date sqlDate = java.sql.Date.valueOf(stringDate);
		return sqlDate;
	}

	/**
	 * 
	 * @param date String
	 * @param format format date
	 * @return java.util.Date
	 * @throws DateConverterException
	 */
	public static java.util.Date stringToUtilDate(String date, String format) 
	{
		DateFormat formatter = new SimpleDateFormat(format);
		java.util.Date utilDate = new java.util.Date();
		try 
		{
			utilDate = formatter.parse(date);
		} 
		catch (ParseException e) 
		{
			log.error("Can't convert data");
		}
		return utilDate;
	}
}
