//package com.epam.testapp.database;
//
//import com.epam.testapp.database.exception.DaoException;
//
///**
// * Enum Factory for Dao.<p>
// * Uses special id to each RDBMS.
// * @author Dmitry Shanko
// *
// */
//public enum DAOFactory
//{
//	MYSQL(1), ORACLE(2), COMMON(3);
//	private int id;
//	private static DAOFactory e = DAOFactory.COMMON;
//	private DAOFactory(int id)
//	{
//		this.id = id;
//	}
//	/**
//	 * Gets NewsDao for DaoFactory enum. Enum must be chosen to get instance of Dao.
//	 * @return Instance of proper NewsDao
//	 * @throws DaoException
//	 */
//	public NewsDao getNewsDao() throws DaoException 
//	{
//		switch(id)
//		{
//		case 1:
//			break;
//		case 2:
//			return NewsDaoImpl.getInstance();
//		default:
//			break;
//		}
//		throw new DaoException("No NewsDao can be created for such Dao Factory enum");		
//	}
//	/**
//	 * This method must be used before calling any static method with "Initialized" in its names.<p>
//	 * Takes id of DaoFactory enum and sets its value to private static variable. It makes possible to get concrete Dao without choosing enum.
//	 * @param e DaoFactory enum to be set as static initializing variable
//	 */
//	public static void init(DAOFactory e)
//	{
//		if (null != e)
//		{
//			DAOFactory.e = e;
//		}
//	}
//	/**
//	 * Gets NewsDao for initialized DaoFactory enum. Enum must be initialized in DaoFactory to get instance of Dao.
//	 * @return Instance of proper NewsDao
//	 * @throws DaoException
//	 */
//	public static NewsDao getInitializedNewsDao() throws DaoException 
//	{
//		switch(DAOFactory.e.id)
//		{
//		case 1:
//			break;
//		case 2:
//			return NewsDaoImpl.getInstance();
//		default:
//			break;
//		}
//		throw new DaoException("No StudentDao can be created for such initialized Dao Factory enum. Or no StudentDao has been initialized yet.");		
//	}
//}
