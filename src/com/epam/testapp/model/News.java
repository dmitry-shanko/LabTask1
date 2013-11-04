package com.epam.testapp.model;

import java.io.Serializable;
import java.sql.Date;

public class News implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8303915975000106941L;
//	private final String TITLE_CONST = "TITLE:";
//	private final String DATE_CONST = "DATE:";
//	private final String BRIEF_CONST = "BRIEF:";
//	private final String CONTENT_CONST = "CONTENT:";
//	private final String ID_CONST = "ID:";
	private String title;
	private Date date;
	private String brief;
	private String content;
	private int id;

	public News()
	{

	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		if (id > 0)
		{
			this.id = id;
		}
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return String presentation of this bean.
	 */
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(NEWS: {ID=");
		sb.append(id);
		sb.append("; TITLE=");
		sb.append(title);
		sb.append("; DATE=");
		sb.append(date);
		sb.append("; BRIEF=");
		sb.append(brief);
		sb.append("; CONTENT=");
		sb.append(content);
		sb.append("});");
		return sb.toString();
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return false if any field is not equals to the field of comparing object.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass())
		{
			return false;
		}
		News news = (News) obj;
		if (title != null ? !title.equals(news.title) : news.title != null) 
		{
			return false;
		}
		if (content != null ? !content.equals(news.content) : news.content != null) 
		{
			return false;
		}
		if (id != news.id)
		{
			return false;
		}
		if (brief != null ? !brief.equals(news.brief) : news.brief != null)
		{
			return false;
		}
		if (date != null ? !date.equals(news.date) : news.date != null)
		{
			return false;
		}
		return true;
	}
	/**
	 * Overridden Object.hashCode()<p>
	 * final int hash = 1;<p>
	 *	int result = 11;<p>
	 *	result = hash * result + id;<p>
	 *	result = hash * result + ((null != content) ? content.hashCode() : 1);<p>
	 *	result = hash * result + ((null != title) ? title.hashCode() : 1);<p>
	 *	result = hash * result + ((null != date) ? date.hashCode() : 1);<p>
	 *	result = hash * result + ((null != brief) ? brief.hashCode() : 1);<p>
	 *	@ return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 1;
		int result = 11;
		result = hash * result + id;
		result = hash * result + ((null != content) ? content.hashCode() : 1);
		result = hash * result + ((null != title) ? title.hashCode() : 1);
		result = hash * result + ((null != date) ? date.hashCode() : 1);
		result = hash * result + ((null != brief) ? brief.hashCode() : 1);
		return result;
	}
}
