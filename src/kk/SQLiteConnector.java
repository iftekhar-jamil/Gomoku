package kk;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnector {
	public Connection connector = null;
	
	public SQLiteConnector () {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      connector = DriverManager.getConnection("jdbc:sqlite:test.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	   
	    System.out.println("Opened database successfully");
	    
	}
	
	public void close () {
		try {
			connector.close();
			System.out.println("Close database successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createUser (String name, String password) {
		Statement stmt = null;
		String command = "";
		try {
		    
			stmt = connector.createStatement();
			command = "INSERT INTO USER (NAME, PASSWORD) " +
		            "VALUES ('" + name + "', " + password.hashCode() + " );"; 
		    stmt.executeUpdate(command);
		   // modifyFile ();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
	
	public boolean existUser (String userName) {
		Statement stmt = null;
		String command = "";
		try {
			stmt = connector.createStatement();
			command = "Select * from USER where NAME == '" + userName + "';"; 
		    ResultSet result = stmt.executeQuery(command);
		    return result.next();
		} catch (Exception e) {
			
		}
		return true;
	}
	
	public boolean validUser (String userName, String password) {
		Statement stmt = null;
		String command = "";
		viewUser();
		try {
			stmt = connector.createStatement();
			command = "Select * from USER where NAME == '" + userName + "' AND PASSWORD == " + password.hashCode() + ";"; 
			//System.out.println(command);
		    ResultSet result = stmt.executeQuery(command);
		    return result.next();
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public void viewUser () {
		Statement stmt = null;
		String command = "";
		try {
			stmt = connector.createStatement();
			command = "Select * from USER;"; 
		    ResultSet result = stmt.executeQuery(command);
		    
		    while (result.next()) {
		    	System.out.println(result.getString(1) + "\t" + result.getString(2));
		    }
		    
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
	
	public void deleteUser () {
		Statement stmt = null;
		String command = "";
		try {
			stmt = connector.createStatement();
			command = "DROP TABLE USER;"; 
		    stmt.executeQuery(command);
		    
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	/*public void modifyFile (String file, String directory) {
		Statement stmt = null;
		String command = "";
		try {
			stmt = connector.createStatement();
			command = "INSERT INTO FILE (NAME, DIRECTORY) " +
		            "VALUES ('" + file + "', '" + directory + "' );"; 
		    stmt.executeUpdate(command);  
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}*/
	
	public void createTable () {
		Statement stmt = null;
		try {
			stmt = connector.createStatement();
			
			String sql = "DROP TABLE USER;";
			stmt.executeUpdate(sql);
			
			sql = "DROP TABLE FILE;";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE USER " +
	                 "(NAME TEXT PRIMARY KEY NOT NULL," + 
	                 " PASSWORD INTEGER )";
			stmt.executeUpdate(sql);
			
			sql = "CREATE TABLE FILE " +
	                 "(NAME TEXT NOT NULL," + 
	                 " DIRECTORY TEXT PRIMARY KEY NOT NULL )";
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
	}
}
