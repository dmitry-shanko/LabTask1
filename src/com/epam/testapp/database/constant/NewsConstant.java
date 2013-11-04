package com.epam.testapp.database.constant;

public enum NewsConstant 
{
	TITLE("TITLE"),
	BRIEF("BRIEF"),
	DATE("NEWSDATE"),
	CONTENT("CONTENT"),
	ID("IDNEWS");
	private String content;
	private NewsConstant (String content) 
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}
}
