package com.epam.testapp.database.cp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.epam.testapp.database.cp.exception.ConnectionSQLException;
import com.epam.testapp.database.cp.exception.NoConnectionException;
/**
 * Class for containing some PooledConnection to RDBMS
 * @author DmitryShanko
 *
 */
public class ConnectionPool
{
	private static final Logger log = Logger.getLogger(ConnectionPool.class);
	
	/** Constant default value of pool capacity */
	private static final int DEFAULT_POOL_SIZE = 5;
	
	/** free connections queue */
	private BlockingQueue<PooledConnection> connectionQueue;
	
	private String driver = "";
	private String url = "";
	private String user = "";
	private String password = "";
	private int poolSize = DEFAULT_POOL_SIZE;
	private int count = 0;
	private static Lock mainLock = new ReentrantLock();
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	/**
	 * Default Constructor. Sets Locale to the Locale.US. Very important action for working with Oracle RDBMS.
	 */
	private ConnectionPool()
	{
		Locale.setDefault(Locale.US);
	}
	/**
	 * Private constructor to create proper ConnectionPool to RDBMS. Must get required params to make proper connection to RDBMS.
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	private ConnectionPool(String driver, String url, String user, String password, int poolSize) throws NoConnectionException, ConnectionSQLException
	{		
		this();	
		if (null == driver || null == url)
		{
			log.error("Attempt to create ConnectionPoll without any driver or url.\nUrl=".concat(url).concat("\nDriver=".concat(driver)));
			throw new NoConnectionException("Attempt to create ConnectionPoll without any driver or url.\nUrl=".concat(url).concat("\nDriver=".concat(driver)));
		}
		if (poolSize > 0)
		{
			this.poolSize = poolSize;
		}
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;		
		init();
	}
	/**
	 * Setter for driver param
	 * @param driver
	 */
	public void setDriver(String driver)
	{
		if (null != driver)
		{
			this.driver = driver;
		}
	}
	/**
	 * Setter for url param
	 * @param url
	 */
	public void setUrl(String url)
	{
		if (null != url)
		{
			this.url = url;
		}
	}
	/**
	 * Setter for user param
	 * @param user
	 */
	public void setUser(String user)
	{
		if (null != user)
		{
			this.user = user;
		}
	}
	/**
	 * Setter for password param
	 * @param password
	 */
	public void setPassword(String password)
	{
		if (null != password)
		{
			this.password = password;
		}
	}
	/**
	 * Setter for poolSize param
	 * @param poolSize
	 */
	public void setPoolSize(int poolSize)
	{
		if (poolSize > 0)
		{
			this.poolSize = poolSize;
		}
	}
	/**
	 * Clears this ConnectionPool.
	 * @throws ConnectionSQLException
	 */
	public void dispose() throws ConnectionSQLException 
	{
		try
		{
			this.clearConnectionQueue();
			log.info("ConnectionPool was disposed.");
		}
		catch (ConnectionSQLException e) 
		{
			throw e;
		}			
	}
	/**
	 * Uses lock2. Takes some connection from the connectionQueue.<p>
	 * Calls init() method to re-initialize ConnectionPool if taken connection isClosed() or all released connections were closed.
	 * @return PooledConnection from existing ConnectionPool
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	public Connection takeConnection () throws NoConnectionException, ConnectionSQLException 
	{
		lock2.lock();
		Connection connection = null;
		try 
		{
			if (count == poolSize)
			{
				init(0);
			}
			connection = connectionQueue.take();
			if (connection != null && connection.isClosed())
			{
				init(connectionQueue.size() + 1);
				connection = this.takeConnection();
			}
			return connection;
		} 
		catch (InterruptedException e) 
		{
			throw new NoConnectionException("InterruptedException in ConnectionPool", e);
		} 
		catch (ConnectionSQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		} 
		catch (SQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		}		
		finally
		{
			lock2.unlock();
		}
	}
	/**
	 * Uses lock1. Releases connection back to this ConnectionPool.<p>
	 * If got connection isClosed(), increments private count++. It will be used in re-initialization method.
	 * @param connection Connection to release.
	 * @throws ConnectionSQLException
	 */
	public void releaseConnection (PooledConnection connection) throws ConnectionSQLException
	{
		try 
		{
			lock1.lock();
			if (connection != null && !connection.isClosed ()) 
			{
				if (!connectionQueue.offer (connection)) 
				{
					lock1.unlock();
					log.error("Connection was not released. Error in syhchronization");
					/*"Connection not added. Possible `leakage` of
					 connections"*/
				}
				else
				{
					lock1.unlock();
				}
			} 
			else 
			{
				if (connection.isClosed())
				{
					count++;
				}
				lock1.unlock();
				log.error("Connection has been alreay closed. Error in syhchronization");
				//"Trying to release closed connection. Possible
				// `leakage` of connections"
			}
		} 
		catch (SQLException e) 
		{
			throw new ConnectionSQLException("SQLException in connection.isClosed()", e);
		}
	}	
	/**
	 * Initialization of pool with value of poolSize as this.poolSize.<p>
	 * Is useful in 1st initialization.
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	private void init() throws NoConnectionException, ConnectionSQLException
	{
		this.init(this.poolSize);
	}
	/**
	 * Initializes connectionQueue with proper PooledConnections.<p>
	 * Uses some "magic" count "private int count". This count represents the number of released connections that were closed.<p>
	 * It helps to re-initialize ConnectionPool with the number of Connections that was taken from properties file.<p>
	 * Uses mainLock to provide proper synchronization path.
	 * @param size Size of connectionQueue before calling this method, or number of connections to create.
	 * @throws NoConnectionException
	 * @throws ConnectionSQLException
	 */
	private void init(int size) throws NoConnectionException, ConnectionSQLException
	{
		mainLock.lock();
		if (connectionQueue != null)
		{
			for (PooledConnection pc : connectionQueue)
			{
				Connection con = pc.getConnection();
				try
				{
					if (con != null && !con.isClosed())
					{
						con.close();
					}
				}
				catch (SQLException e)
				{
					throw new ConnectionSQLException("SQLException in connection.close()", e);
				}
			}
			connectionQueue = null;
		}
		try 
		{
			Class.forName (driver);
			connectionQueue = new ArrayBlockingQueue<PooledConnection>(poolSize);
			int prevSize = size;
			size += count;
			// This operation was made for synchronization. Because I didn't want to write many locks, so, if count will be changed HERE, it's number will be saved and
			// will be used in another re-initialize calling.
			count -= size - prevSize;
			for (int i = 0; i < size; i++) 
			{
				Connection connection = DriverManager.getConnection(url, user, password);
				log.info("Connection number " + i + " was created");
				try 
				{
					connectionQueue.offer (new PooledConnection(connection));
				} 
				catch (NoConnectionException e) 
				{
					log.error("Error in creating connection. Driver returned null connection", e);
					throw new NoConnectionException("Error in creating connection. Driver returned null connection", e);
				}
			}
			log.info("Connection pool has been initialized successfully.");
			log.info("Driver=".concat(driver));
			log.info("URL=".concat(url));
			log.info("User=".concat(user));
			log.info("PoolSize=" + poolSize);
			log.info("CurrentSize=" + size);
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			throw new NoConnectionException("Can't initialize ConnectionPool", e);
		}
		finally
		{
			mainLock.unlock();
		}
	}
	/**
	 * Clears connectionQueue with closing all not released conenctions.
	 * @throws ConnectionSQLException
	 */
	private void clearConnectionQueue () throws ConnectionSQLException
	{
		PooledConnection connection;
		while ((connection = connectionQueue.poll()) != null) 
		{
			try
			{
				if (!connection.getAutoCommit ()) 
				{
					connection.commit ();
					Connection con = connection.getConnection();
					if (con != null && !con.isClosed())
					{
						con.close();	
					}
				}
			}
			catch (SQLException e)
			{
				throw new ConnectionSQLException("SQLException in connection.close()", e);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable 
	{
		super.finalize();
		this.clearConnectionQueue();
		connectionQueue = null;
	}
} 