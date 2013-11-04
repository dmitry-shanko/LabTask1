package com.epam.testapp.presentation.form;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.epam.testapp.model.News;
import com.epam.testapp.util.DateFormatter;

public class NewsForm extends ActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042849036398988864L;

	private List<News> newsList = new ArrayList<News>();
	private static final String resources = "resources.jsp.MessageResources";
	private static final String datePattern = "date.pattern";
	private static final Logger log = Logger.getLogger(ActionForm.class);
	private final News emptyNews = new News();
	private News news = emptyNews;
	private Integer[] selectedItems;
	private String dateString;
	private Locale locale;

	public NewsForm()
	{

	}
	/**
	 * @return the dateString
	 */
	public String getDateString() 
	{
		return dateString;
	}

	/**
	 * @param news
	 * the news to set
	 */
	public void setNews(News news) 
	{
		if (null != news)
		{
			if (news.getDate() != null) 
			{
				ResourceBundle bundle = ResourceBundle.getBundle(resources, locale);
				String pattern = bundle.getString(datePattern);
				DateFormat dateFormat = new SimpleDateFormat(pattern);
				dateString = dateFormat.format(news.getDate());
			}
			this.news = news;
		}
		else
		{
			this.news = emptyNews;
		}
	}

	/**
	 * @param dateString
	 * the dateString to set
	 */
	public void setDateString(String dateString)
	{
		if (dateString != null && !(dateString.isEmpty())) 
		{
			ResourceBundle bundle = ResourceBundle.getBundle(resources, locale);
			String pattern = bundle.getString(datePattern);
			DateFormat dateFormat = new SimpleDateFormat(pattern);

			java.util.Date utilDate = null;
			try 
			{
				utilDate = dateFormat.parse(dateString);
			} 
			catch (ParseException e) {
				log.error("Can't parse date", e);
			}
			Date sqlDate = DateFormatter.utilDateToSqlDate(utilDate);
			news.setDate(sqlDate);
		}
		this.dateString = dateString;
	}

	/**
	 * @return the selectedItems
	 */
	public Integer[] getSelectedItems() {
		return selectedItems;
	}

	/**
	 * @param selectedItems
	 * the selectedItems to set
	 */
	public void setSelectedItems(Integer[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	/**
	 * @return the newsList
	 */
	public List<News> getNewsList() {
		return newsList;
	}

	/**
	 * @param newsList
	 * the newsList to set
	 */
	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	/**
	 * @return the news
	 */
	public News getNews() {
		return news;
	}
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 * the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}

