package com.epam.testapp.presentation.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.MappingDispatchAction;

import com.epam.testapp.database.NewsDao;
import com.epam.testapp.database.exception.DaoException;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;
import com.epam.testapp.util.GlobalConstants;


public class NewsAction extends MappingDispatchAction
{
	private NewsDao newsDao;
	private static final Logger log = Logger.getLogger(NewsAction.class);

	private static final String MAIN_PAGE = "mainpage";
	private static final String NEWS_LIST_PAGE = "newsList";
	private static final String VIEW_NEWS_PAGE = "newsView";
	private static final String VIEW_NEWS_PAGE_ACTION = "newsViewAction";
	private static final String EDIT_NEWS_PAGE = "newsEdit";
	private static final String ERROR_PAGE = "error";
	private static final String PREVIOUS_PAGE = "previousPage";;

	private NewsAction()
	{

	}

	public void setNewsDao(NewsDao newsDao)
	{
		this.newsDao = newsDao;
	}
	/**
	 * Forwards to the News list page with list of news got from newsDao.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newsList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		log.info("newsList action in progress.");
		String target = ERROR_PAGE;
		request.getSession().setAttribute(PREVIOUS_PAGE, MAIN_PAGE);
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm.getLocale() == null) 
		{
			//			newsForm.setLocale(request.getLocale());
			newsForm.setLocale(Locale.US);
			setLocale(request, Locale.US);
		}
		List<News> newsList = newsDao.getList();
		if (newsList != null) 
		{
			target = NEWS_LIST_PAGE;
			newsForm.setNewsList(newsList);
		}
		log.info("in newsList action were collected " + (newsList != null ? newsList.size() : null) + " news");
		return actionMapping.findForward(target);
	}
	/**
	 * Forwards to the View page with news in newsForm to be viewed
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward newsView(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.info("newsView action in progress.");
		String target = ERROR_PAGE;
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm != null) 
		{
			target = VIEW_NEWS_PAGE;
			News news = null;
			if (null != newsForm.getNews())
			{
				try 
				{
					news = newsDao.fetchById(newsForm.getNews().getId());
				} 
				catch (DaoException e) 
				{
					log.error("Can't com.epam.testapp.database.NewsDao.fetchById(" + newsForm.getNews().getId() + "). DaoException.", e);
				}
				newsForm.setNews(news);
				request.getSession().setAttribute(PREVIOUS_PAGE, VIEW_NEWS_PAGE_ACTION);
				log.info("Attempt to view news: ".concat(news != null ? news.getId() + "" : " news was not found in database. NewsId was " + newsForm.getNews().getId()));
			}
			else
			{
				log.warn("news in newsForm in newsView is null.");
			}
		}	
		else
		{
			log.warn("newsForm is null");
		}
		return actionMapping.findForward(target);
	}
	/**
	 * Forwards to the Edit page with news in newsForm to be edited.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward newsEdit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.info("newsEdit action in progress.");
		String target = EDIT_NEWS_PAGE;
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm != null) 
		{			
			News news = null;
			try 
			{
				news = newsDao.fetchById(newsForm.getNews().getId());
			} 
			catch (DaoException e) 
			{
				log.error("Can't com.epam.testapp.database.NewsDao.fetchById(" + newsForm.getNews().getId() + "). DaoException.", e);
			}
			newsForm.setNews(news);
			log.info("News to edit: ".concat(news.toString()));
		}
		else
		{
			log.warn("newsForm is null.");
		}
		return actionMapping.findForward(target);
	}	
	/**
	 * Forwards to the Edit page without any news to add.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward newsAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.info("newsAdd action in progress.");
		String target = EDIT_NEWS_PAGE;
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm != null) 
		{				
			News news = new News();
			news.setDate(new java.sql.Date(System.currentTimeMillis()));
			newsForm.setNews(news);
			//			newsForm.setDateString("");
		}
		return actionMapping.findForward(target);
	}	
	/**
	 * Saves changed news. Creates new if id of news from newsForm.getNews() is less then 1 and updates existing if id is 1 or more.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward newsSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		String target = ERROR_PAGE;
		log.info("newsSave action in progress.");
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm != null) 
		{
			News news = newsForm.getNews();
			log.info("Attempt to save News: ".concat((news != null ? news.toString() : null)));
			if ((null != news) && (news.getBrief() != null) && (news.getContent() != null) && (news.getDate() != null) && (news.getTitle() != null))
			{
				try 
				{					
					if (news.getId() < 1)
					{
						if (newsDao.save(news))
						{
							target = VIEW_NEWS_PAGE_ACTION;
							log.info("News have been successfully saved: ".concat(news.toString()));
						}
						else
						{							
							log.warn("News were not saved: ".concat(news.toString()));
						}
					}
					else
					{
						if (newsDao.updateNews(news))
						{
							target = VIEW_NEWS_PAGE_ACTION;
							log.info("News have been successfully updated: ".concat(news.toString()));
						}
						else
						{
							log.warn("News were not updated: ".concat(news.toString()));
						}
					}
				}
				catch (DaoException e) 
				{
					log.error("Error in trying to save or update news.", e);
				}
			}
		}		
		log.info("newsSave action finished.");
		//		ActionForward redirect = new ActionRedirect(target);
		//		return actionMapping.findForward(target);
		//		return new ActionForward(target, true);
		//		return actionMapping.findForward(target);
		return new ActionRedirect(actionMapping.findForward(target));
	}
	/**
	 * This mapped action deletes list of news with ID taken from newsForm.getSelectedItems().
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward newsDelete(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) 
	{
		log.info("newsDelete action in progress.");
		String target = NEWS_LIST_PAGE;
		NewsForm newsForm = (NewsForm) actionForm;
		if (newsForm != null) 
		{
			Integer[] ids = newsForm.getSelectedItems();
			if ((null != ids) && (ids.length > 0))
			{
				try 
				{
					if (newsDao.remove(ids))
					{
						target = MAIN_PAGE;
						StringBuilder sb = new StringBuilder("News with these id have been deleted: ");
						for (Integer id : ids)
						{
							sb.append(id);
							sb.append(", ");
						}
						sb.deleteCharAt(sb.length() - 1);
						sb.deleteCharAt(sb.length() - 1);
						sb.append(".");
						log.info(sb.toString());
					}
				} 
				catch (DaoException e) 
				{
					log.error("Can't delete news in dao", e);
				}
			}
		}
		log.info("newsDelete action finished.");
		//		return actionMapping.findForward(target);
		return new ActionRedirect(actionMapping.findForward(target));
	}

	public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		String target = (String) request.getSession().getAttribute(PREVIOUS_PAGE);
		return actionMapping.findForward(target);
	}
	/**
	 * In the case of error forwards to the Error page.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward error(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	{
		return actionMapping.findForward(ERROR_PAGE);
	}
	/**
	 * Mapped ActionForward for changing language. Forwards to the request.getHeader("Referer") link.<p>
	 * Parameter of new locale is taking from request.
	 * @param actionMapping
	 * @param actionForm 
	 * @param request
	 * @param response
	 * @return new ActionForward(request.getHeader("Referer"), true)
	 * @throws Exception
	 */
	public ActionForward lang(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		log.info("lang action in progress.");
		String lang = request.getParameter(GlobalConstants.LANG_PARAM_REQUEST.getContent()); 		
		if (null != lang)
		{
			String country = GlobalConstants.COUNTRY_EN.getContent();
			lang = lang.trim().toLowerCase();
			switch (lang)
			{
			case "ru":
				country = GlobalConstants.COUNTRY_RU.getContent();
				break;
			case "en":
				country = GlobalConstants.COUNTRY_EN.getContent();
				break;
			}
			Locale locale = new Locale(lang, country);
			setLocale(request, locale);
			NewsForm newsForm = (NewsForm) actionForm;
			newsForm.setLocale(locale);	
			log.info("Now locale is ".concat(locale.toString()));
		}
		else
		{
			log.info("Attempt to change locale to null");
		}
		String path = request.getHeader("Referer");
		return new ActionForward(path, true);
	}
}
