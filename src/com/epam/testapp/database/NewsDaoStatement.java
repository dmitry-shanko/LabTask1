package com.epam.testapp.database;

public enum NewsDaoStatement 
{
	collectNews("SELECT TITLE, BRIEF, CONTENT, NEWSDATE, IDNEWS FROM \"ROOT\".\"T_NEWS\" ORDER BY NEWSDATE desc"),
	collectNewsById("SELECT TITLE, BRIEF, CONTENT, NEWSDATE, IDNEWS FROM \"ROOT\".\"T_NEWS\" WHERE IDNEWS=?"),
	createNews("INSERT INTO \"ROOT\".\"T_NEWS\" (TITLE, BRIEF, CONTENT, NEWSDATE, IDNEWS) VALUES (?, ?, ?, ?, T_NEWS_SEQ.NEXTVAL)"),
	deleteNews("DELETE FROM \"ROOT\".\"T_NEWS\" WHERE IDNEWS IN ("),
	updateNews("UPDATE \"ROOT\".\"T_NEWS\" SET TITLE=?, BRIEF=?, CONTENT=?, NEWSDATE=? WHERE IDNEWS=?");
	private String statement;
	private NewsDaoStatement (String s)
	{
		this.statement = s;
	}
	
	public String getStatement()
	{
		return statement;
	}
}
