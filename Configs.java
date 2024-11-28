package __Init__;

/**
 * Configs File
 * the configs file is use to edit and modifiy connection to database 
 */
public class Configs {
	
	/**
	 * Configable variable
	 */
	
	protected String host = "localhost";
	protected  String username = ""; // username 
	protected String dbname = ""; // database name
	protected String password = ""; // db password
	protected String port = "3306";
	
	
	
	
	/**
	 * Getter Information 
	 * @return 
	 */
	
	
	
	public String GetDB()
	{
		return this.dbname;
	}
	
	public String GetUser()
	{
		return this.username;
	}
	
	public String GetPassword()
	{
		return this.password;
	}
	
	public String GetPort()
	{
		return this.port;
	}
	
	
}
