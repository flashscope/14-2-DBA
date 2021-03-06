import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameClient implements Runnable {
	
	public static boolean isGamePlaying = true;
	
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
	private int errorCount = 0;
	private final int errorCountMax = 10;
	
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

		if(mConnectionList.size() < 3) {
			System.out.println("Dosen't Initialized!");
			return;
		}
		
		try {
			// wait for sign up little
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(true) {
			
			int UID = 0;
			int GID = 0;
			int DBID = 0;
			int UID2 = 0;//공격 받는쪽
			int GID2 = 0;
			int DBID2 = 0;
			
			try {
				// get userID, GID, DBID and gamePlaying
				Connection mainConnection = mConnectionList.get(0);
				CallableStatement cs = mainConnection.prepareCall("{CALL SP_GET_USER_DATA(?,?,?)}");
				cs.registerOutParameter(1, java.sql.Types.INTEGER);
				cs.registerOutParameter(2, java.sql.Types.TINYINT);
				cs.registerOutParameter(3, java.sql.Types.TINYINT);
				
				cs.executeUpdate();
				
				UID = cs.getInt(1);
				GID = cs.getInt(2);
				DBID = cs.getInt(3);
				
				//System.out.println("UID:"+UID+" GID:"+GID+" DBID:" + DBID);
				cs.close();
				
				
				CallableStatement cs2 = mainConnection.prepareCall("{CALL SP_GET_USER_DATA(?,?,?)}");
				cs2.registerOutParameter(1, java.sql.Types.INTEGER);
				cs2.registerOutParameter(2, java.sql.Types.TINYINT);
				cs2.registerOutParameter(3, java.sql.Types.TINYINT);
				
				cs2.executeUpdate();
				
				UID2 = cs2.getInt(1);
				GID2 = cs2.getInt(2);
				DBID2 = cs2.getInt(3);
				
				//System.out.println("UID:"+UID+" GID:"+GID+" DBID:" + DBID);
				cs2.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// GET SHIP...
			int attack = 100;
			
			
			
			// attack galaxy
			try {
				Connection subConnection = mConnectionList.get(DBID2);
				CallableStatement cs = subConnection.prepareCall("{CALL SP_ATTACK_GALAXY(?,?,?)}");
				cs.setInt(1, GID2);
				cs.setInt(2, attack);
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				
				cs.executeUpdate();
				
				int galaxyHP = cs.getInt(3);
				
				
				
				synchronized (SimpleServer.messageList) {
					String message = GID2 + "" + galaxyHP + "";
					SimpleServer.messageList.add(message);
				}
				
				if(galaxyHP<=0) {
					isGamePlaying = false;
				}
				
				cs.close();
				
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			if(!isGamePlaying) {
				break;
			}
			
		}
		

	}
	
	
}
