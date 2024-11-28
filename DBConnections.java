package __Init__;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

/**
 * 
 * (c) (2023) Alegrarsio Gifta Lesmana
 * Java Database Manager 
 * Is A Library that we use to managed our database this Library is powered by JDBC SQL Connector
 * Requirements
 * -JDBC 8.x.x
 * -Mysql 8.x.x
 * */
public class DBConnections extends Convertables{
	private String databasename;
	private String password;
	private String username;
	private String port = "3306" ;
	private String host = "localhost";
	private Connection connection;
	
	private String link = "jdbc:mysql://localhost:" + this.port +"/" + this.databasename;
	
	
	public DBConnections(String nama, String username , String password )
	{
		this.databasename= nama;
		this.username = username;
		this.password = password;
	}
	
	public String GetDB()
	{
		return this.databasename;
	}
	
	public DBConnections(String nama , String port ,  String username , String password)
	{
		this.databasename = nama;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	public DBConnections(String host ,String nama , String port ,  String username , String password)
	{
		this.host = host;
		this.databasename = nama;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	public void Connect() throws SQLException{
		
		
		String link = "jdbc:mysql://" + this.host +":" + this.port +"/" + this.databasename;
		
		if (connection == null || connection.isClosed())
		{
			connection = DriverManager.getConnection(link, this.username, this.password);
			
			System.out.println("Koneksi Berhasil");
		}
	}
	
    public Connection getConnection() {
        return connection;
    }
 
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conection Down .");
        }
    }
    
    public void CreateTable(String tableName, Map<String, String> columns) {
        if (tableName == null || tableName.isEmpty() || columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("Nama tabel atau kolom tidak boleh kosong.");
        }

        StringBuilder columnsSQL = new StringBuilder();
        
   
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            if (columnsSQL.length() > 0) {
                columnsSQL.append(", ");
            }

            
            String sqlType = Types(entry.getValue());
            columnsSQL.append(entry.getKey()).append(" ").append(sqlType);
        }

       
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnsSQL.toString() + ");";

    
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table '" + tableName + "' created successfully.");
        } catch (SQLException e) {
            System.err.println("Error while creating table: " + e.getMessage());
        }
    }
    
    
    public void InsertTo(String tableName, Map<String, Object> data) {
        if (tableName == null || tableName.isEmpty() || data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Nama tabel atau data tidak boleh kosong.");
        }

      
        StringBuilder columnsSQL = new StringBuilder();
        StringBuilder valuesSQL = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (columnsSQL.length() > 0) {
                columnsSQL.append(", ");
                valuesSQL.append(", ");
            }
            columnsSQL.append(entry.getKey());
            valuesSQL.append("?");
        }

    
        String insertSQL = "INSERT INTO " + tableName + " (" + columnsSQL.toString() + ") VALUES (" + valuesSQL.toString() + ");";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
        
            int index = 1;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                pstmt.setObject(index++, entry.getValue());
            }

           
            pstmt.executeUpdate();
            System.out.println("Data berhasil dimasukkan ke dalam tabel '" + tableName + "'.");
        } catch (SQLException e) {
            System.err.println("Error saat memasukkan data: " + e.getMessage());
        }
    }
    public void SelectFrom(String tableName) throws SQLException {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException("Nama tabel tidak boleh kosong.");
        }

        String selectSQL = "SELECT * FROM " + tableName + ";";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
           
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getMetaData().getColumnName(i) + ": " + rs.getObject(i) + " | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Error saat mengambil data: " + e.getMessage());
        }
    }
    private void printTable(ResultSet rs) throws SQLException {
               int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(rs.getMetaData().getColumnName(i) + "\t\t");
        }
        System.out.println();

        
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getObject(i) + "\t\t");
            }
            System.out.println();
        }
    }
    public boolean login(String username, String password) throws SQLException {
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            
            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Login berhasil!");
                    return true;  
                } else {
                    System.out.println("Username atau password salah.");
                    return false; 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
            return false;
        }
    }
    private int findId(String tableName, Map<String, Object> criteria) throws SQLException {
        if (tableName == null || tableName.isEmpty() || criteria == null || criteria.isEmpty()) {
            throw new IllegalArgumentException("Nama tabel atau kriteria tidak boleh kosong.");
        }

        StringBuilder whereClause = new StringBuilder(" WHERE ");

        
        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            if (!whereClause.toString().endsWith(" WHERE ")) {
                whereClause.append(" AND ");
            }
            whereClause.append(entry.getKey()).append(" = ?");
        }

        String sql = "SELECT id FROM " + tableName + whereClause.toString();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                pstmt.setObject(index++, entry.getValue());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saat mencari ID: " + e.getMessage());
        }
        return -1; 
    }
    public void Update(String tableName, Map<String, Object> data, String condition) {
        if (tableName == null || tableName.isEmpty() || data == null || data.isEmpty() || condition == null || condition.isEmpty()) {
            throw new IllegalArgumentException("Nama tabel, data, atau kondisi tidak boleh kosong.");
        }

        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append("UPDATE ").append(tableName).append(" SET ");

        
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (updateSQL.toString().endsWith(" SET ")) {
                updateSQL.append(entry.getKey()).append(" = ?");
            } else {
                updateSQL.append(", ").append(entry.getKey()).append(" = ?");
            }
        }

        
        updateSQL.append(" WHERE ").append(condition);

        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL.toString())) {
            
            int index = 1;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                pstmt.setObject(index++, entry.getValue());
            }

           
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data berhasil diperbarui di tabel '" + tableName + "'.");
            } else {
                System.out.println("Tidak ada data yang diperbarui.");
            }
        } catch (SQLException e) {
            System.err.println("Error saat memperbarui data: " + e.getMessage());
        }
    }
    public void UpdateAuto(String tableName, Map<String, Object> criteria, Map<String, Object> data) {
        try {
        
            int id = findId(tableName, criteria);
            if (id == -1) {
                System.out.println("Data tidak ditemukan dengan kriteria yang diberikan.");
                return;
            }

            
            String condition = "id = " + id;

           
            Update(tableName, data, condition);

        } catch (SQLException e) {
            System.err.println("Error saat memperbarui data berdasarkan ID otomatis: " + e.getMessage());
        }
    }



}

