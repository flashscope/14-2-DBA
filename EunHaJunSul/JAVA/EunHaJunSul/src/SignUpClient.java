import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


public class SignUpClient implements Runnable {

	// 사용하는 데이터베이스명을 포함한 url
	private static final String MAIN_URL = "jdbc:mysql://10.73.45.74/popi?noAccessToProcedureBodies=true";
	private static final String MAIN_ID = "popi";// 사용자계정
	private static final String MAIN_PASS = "db1004";// 사용자 패스워드

	private static final String DB1_URL = "jdbc:mysql://10.73.45.74/popi?noAccessToProcedureBodies=true";
	private static final String DB1_ID = "popi";
	private static final String DB1_PASS = "db1004";
	
	private static final String DB2_URL = "jdbc:mysql://10.73.45.74/popi?noAccessToProcedureBodies=true";
	private static final String DB2_ID = "popi";
	private static final String DB2_PASS = "db1004";
	
	
	private ArrayList<Connection> mConnectionList = new ArrayList<Connection>();
	
	public boolean initialize() {

		try {
			Class.forName("com.mysql.jdbc.Driver");// 드라이버 로딩: DriverManager에 등록
		} catch (ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			return false;
		}
		
		try {
			Connection mainConn = DriverManager.getConnection(MAIN_URL, MAIN_ID, MAIN_PASS);
			mConnectionList.add(mainConn);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			Connection db1Conn = DriverManager.getConnection(DB1_URL, DB1_ID, DB1_PASS);
			mConnectionList.add(db1Conn);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			Connection db2Conn = DriverManager.getConnection(DB2_URL, DB2_ID, DB2_PASS);
			mConnectionList.add(db2Conn);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public void run() {
		
		for (int i = 0; i < 1000; ++i) {
			
			int UID = 0;
			int GID = 0;
			int DBID = 0;
			
			try {
				// add user and get user ID, Galaxy ID, DB ID
				Connection mainConnection = mConnectionList.get(0);
				CallableStatement cs = mainConnection.prepareCall("{CALL SP_AddUser(?,?,?)}");
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.registerOutParameter(2, java.sql.Types.TINYINT);
				cs.registerOutParameter(3, java.sql.Types.TINYINT);
				
				cs.executeUpdate();
				
				UID = cs.getInt(1);
				GID = cs.getInt(2);
				DBID = cs.getInt(3);
				
				cs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
			
			
			
			try {
				// add user and ships
				Connection subConnection = mConnectionList.get(DBID);
				CallableStatement cs = subConnection.prepareCall("{CALL SP_AddShips(?,?)}");
				cs.setInt(1, UID);
				cs.setInt(2, GID);
				
				cs.executeUpdate();
				
				cs.close();
					
			} catch (SQLException e) {
				e.printStackTrace();
				continue;
			}
			
		}
		
	}

}
