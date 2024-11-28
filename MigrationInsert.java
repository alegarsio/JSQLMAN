package __Init__;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class MigrationInsert extends Configs {
	
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
		dbs.Connect();
		
		
		/**
		 * Insert Here
		 */
		
		Map<String, Object> data = new HashMap<>();

		/**
		 * insert your table column data here 
		 * with using data.put()
		 */
	
		
		dbs.InsertTo(tablename, data);
	}
	
	public static void main(String[] args) throws SQLException
	{
		MigrationInsert Insertion = new MigrationInsert();
		Insertion.Run();
	}
}
