package __Init__;

import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;

public class MigrationUpdate extends Configs {
	
	DBConnections dbs = new DBConnections(this.dbname, this.port ,this.username, this.password);
	
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
		 * Match the criteria
		 */
		
		Map<String,Object> criteria = new HashMap<>();
		
		/**
		 * criteria.put()
		 */
		
		
		Map<String , Object > newData = new HashMap<>();
		
		/**
		 * newData.put()
		 */
		
		
		dbs.UpdateAuto(tablename, criteria, newData);
		dbs.disconnect();
	}
	
	public static void main(String[] args) throws SQLException
	{
		MigrationUpdate Update = new MigrationUpdate();
		Update.Run();
	}
}
