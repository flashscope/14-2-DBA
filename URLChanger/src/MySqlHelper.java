import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
CREATE TABLE IF NOT EXISTS `LONG_URL_CHANGER` (
  `_idx` int(11) NOT NULL auto_increment,
  `longUrl` varchar(150) NOT NULL,
  `realUrl` text NOT NULL,
  `password` text NOT NULL,
  PRIMARY KEY  (`_idx`),
  UNIQUE KEY `longUrl` (`longUrl`)
) ENGINE=InnoDB;
*/


public class MySqlHelper implements DBHelper {

	private static final String DB_URL = "jdbc:mysql://10.73.45.74/popi?noAccessToProcedureBodies=true";
	private static final String DB_ID = "popi";
	private static final String DB_PASS = "db1004";
	
	private static Connection dbConn;
	
	@Override
	public boolean Initializer() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");// 드라이버 로딩: DriverManager에 등록
		} catch (ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			return false;
		}
		
		try {
			dbConn = DriverManager.getConnection(DB_URL, DB_ID, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	@Override
	public boolean insertDB(String longUrl, String realUrl, String password) {
		String query = "INSERT INTO LONG_URL_CHANGER(longUrl,realUrl,password) VALUES(?,?,?);";
		
		try {
			PreparedStatement pstmt = dbConn.prepareStatement(query);
			pstmt.setString(1, longUrl);
			pstmt.setString(2, realUrl);
			pstmt.setString(3, password);
			
			int rowsAffected = pstmt.executeUpdate();
			
			if( rowsAffected == 0 ) {
				return false;
			}
			
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	@Override
	public String selectDB(String longUrl, String password) {
		String query = "select realUrl from LONG_URL_CHANGER where (longUrl = ?) AND (password = ?)";
		try {
			PreparedStatement pstmt = dbConn.prepareStatement(query);
			
			pstmt.setString(1, longUrl);
			pstmt.setString(2, password);
			
			//System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			
			return rs.getString("realUrl");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "ERROR";
	}
}
