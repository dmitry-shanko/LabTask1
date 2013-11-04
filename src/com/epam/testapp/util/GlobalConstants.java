package com.epam.testapp.util;

public enum GlobalConstants 
{
	ENCODING_PARAM("encoding"),
	LANG_PARAM_REQUEST("lang"),
	COUNTRY_RU("RU"),
	COUNTRY_EN("US"),
	NEWSLIST_PARAM_REQUEST("newslist"),
	ERROR_PAGE("/error"),
	DATE_PATTERN("dd-MMM-yy"),
	DATE_STRING_PATTERN_RU("dd/MM/yyyy"),
	DATE_STRING_PATTERN_EN("MM/dd/yyyy")
	;
	
	private String content;
	private GlobalConstants (String content) 
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}
}
