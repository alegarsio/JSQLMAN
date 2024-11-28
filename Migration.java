package __Init__;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * You can Config the Database connection with Configs file
 * 
 */
class Migration extends Configs{
	
	private DBConnections dbs = new DBConnections(this.dbname , this.port , this.username, this.password);
	
	public void Run() throws SQLException
	{
		
		/**
		 * Fill the tablename
		 */
		
		String tablename = ""; 
	    /**
	     * Connection to database
	     */
		DBConnections dbs = new DBConnections(this.host, this.dbname , this.port , this.username, this.password);
		dbs.Connect();
		 
		
		/**
		 * Create Operation
		 */
		
		Map<String, String> data = new HashMap<>();
		
        data.put("id", "INT AUTO_INCREMENT PRIMARY KEY");
        
        /**
         * Insert Attibute here
         * example -> data.put("column","data type")
         */
        
        data.put("created_at", "TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
        
        /**
         * Opertion for Creating a table 
         */
        
        dbs.CreateTable(tablename, data);
        
        dbs.disconnect();
        
	}
	
	
	public static void main(String[] args) throws SQLException
	{
		Migration Creates = new Migration();
		Creates.Run();
	}
		
	
	
}
