package com.jft.utils;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class DBUtil
{

	Connection dbConn = null;
	
	

	public Connection getOracleConnectionToODB1 (String nameEnv, String username, String password)
	{
		String urlDB = null;
	    
		try
		{
	
			if(nameEnv.equalsIgnoreCase("QA"))
			{
				 urlDB = "";  // Enter url to DB
				 dbConn = DriverManager.getConnection(urlDB, username, password);
			}
			else if(nameEnv.equalsIgnoreCase("DEV"))
			{
				urlDB = "";
			}
			
			 dbConn = DriverManager.getConnection(urlDB, username, password);
			
			if(dbConn == null)
			{
				System.out.println("[Info] - Could not connect to  DB in " + nameEnv);
			}
			else
			{
				System.out.println("[Info] - Successfully connected to DB in " + nameEnv);
			}
		}
			
		catch (SQLException e)
		{
			System.out.println("[Error] - DB Connection Operation failed in " + nameEnv);
		}
		
		return dbConn;
	
	}
	
	
	

	public void closeEodConnection () throws SQLException
	{
		
		dbConn.close ();
	}
	
	
	public int getAccountInfo(String sEnv, String sUserName)throws SQLException
	{
		int sAccountId = 0;
		
		Statement userNameStmt;
		ResultSet userNameRs;
		
		//Connect to the EOD environment 
		this.getOracleConnectionToODB1(sEnv, "username", "password");
		
		userNameStmt = dbConn.createStatement();
		userNameRs = userNameStmt.executeQuery("select account_id from epl.system_account where user_name='" + sUserName.toUpperCase() + "'");
		
		if (userNameRs == null)
			System.out.println("[Info] - UserName Resultset is empty");
		
		while(userNameRs.next ())
		{
			sAccountId = userNameRs.getInt("account_id");
			
		}
		
		this.closeEodConnection();
		return sAccountId;
		
	}
	

	public List<String> getJobTitles(String sEnv, String sPgmCode)throws SQLException
	{
		List<String> jobSet = new ArrayList <String> ();
		
		Statement jobStmt;
		ResultSet jobRs;
		
	
		this.getOracleConnectionToODB1(sEnv, "username", "password");
		
		jobStmt = dbConn.createStatement();
		jobRs = jobStmt.executeQuery("select descr from epd.cd_professional_job_title where job_pgm_category_cd='" + sPgmCode + "' order by sort_order");
		
		if (jobRs == null)
			System.out.println("[Info] - Resultset is empty");
		
		while(jobRs.next ())
		{
			String sAIName = jobRs.getString("descr");
						
			jobSet.add(sAIName);
			
		}
		
		this.closeEodConnection();
		return jobSet;
		
	}
	
	//Unlock the user account
	public void unlockAccount(String envName, String sUserName) throws SQLException
	{
		Statement lockStmt;
		ResultSet lockRs;
		
		//Connect to the EOD environment 
		this.getOracleConnectionToODB1(envName, "username", "password");
		
		lockStmt = dbConn.createStatement();
		lockRs = lockStmt.executeQuery("update epl.system_account set lock_expire_dt=null where user_name='" + sUserName.toUpperCase() + "'");
		
		if (lockRs == null)
			System.out.println("[Info] - UserName Resultset is empty");
		
		this.closeEodConnection();
		
	}
	

	public void activateOrDeactivateAccount(String envName, String sUserName, int iStatus) throws SQLException
	{
		Statement lockStmt;
		ResultSet lockRs;
		

		this.getOracleConnectionToODB1(envName, "username", "password");
		
		lockStmt = dbConn.createStatement();
		lockRs = lockStmt.executeQuery("update epl.system_account set status_cd=" + iStatus + " where user_name='" + sUserName.toUpperCase() + "'");
		
		if (lockRs == null)
			System.out.println("[Info] - Resultset is empty");
		
		this.closeEodConnection();
		
	}
	
	
	
        
     
}


