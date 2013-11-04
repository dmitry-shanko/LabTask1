package com.epam.testapp.database;

import java.util.List;

import com.epam.testapp.database.exception.DaoException;
import com.epam.testapp.model.News;

public interface NewsDao extends GeneralDao<News>
{
	
	List<News> getList() throws DaoException;
	News fetchById(int id) throws DaoException;
	boolean save(News news) throws DaoException;
	boolean remove(Integer[] id) throws DaoException;
	boolean updateNews(News news) throws DaoException;
	
}
