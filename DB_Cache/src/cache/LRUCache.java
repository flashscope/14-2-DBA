package cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

public class LRUCache {
	private static HashMap<Integer, DoublyLinkedListNode> map = new HashMap<Integer, DoublyLinkedListNode>();
	private static DoublyLinkedListNode headNode;
	private static DoublyLinkedListNode tailNode;
	private static int capacity;
	private static int length;
	private static Connection mainConn;
	private static Statement stmt;
	private static final String MAIN_URL = "jdbc:mysql://localhost/popidb";
	private static final String MAIN_ID = "root";
	private static final String MAIN_PASS = "";
	private static int cacheHit;
	private static int cacheMiss;
	private static int queryCount;

	public static void main(String[] args) {
		initialize();
		LRUCache lruCache = new LRUCache(1000);

		Date startTimeCache = new Date();
		queryWithCache("/Users/Woonohyo/Documents/NEXT/5th/DB_Cache/input.txt");
//		queryWithoutCache("/Users/Woonohyo/Documents/NEXT/5th/DB_Cache/input.txt");
		Date endTimeCache = new Date();
		long elapsedTimeCache = endTimeCache.getTime() - startTimeCache.getTime();
		System.out.println("Elasped Time: " + elapsedTimeCache + "(ms)");
		System.out.println("Cache Hit:" + cacheHit + " Cache Miss:" + cacheMiss);
		
	}

	public LRUCache(int capacity) {
		this.capacity = capacity;
		length = 0;
	}

	private static void queryWithCache(String filePath) {
		ResultSet rs;
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fileReader);
			String key;

			while ((key = br.readLine()) != null) {
				if (get(Integer.parseInt(key)) != null) {
					++cacheHit;
				} else {
					String sql = "select * from ctest where k =" + key;
					rs = stmt.executeQuery(sql);
					rs.next();
					++cacheMiss;
					set(rs.getInt("k"), rs.getString("v"));
				}
			}

			br.close();
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void queryWithoutCache(String filePath) {
		ResultSet rs;
		// int count = 1;
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fileReader);
			String key;

			while ((key = br.readLine()) != null) {
				String sql = "select * from ctest where k = " + key;
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					// System.out.println(count + ": " + rs.getInt("k"));
					// ++count;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 캐쉬 액세스 시, 해당 노드를 headNode로 설정
	public static String get(int key) {
		if (map.containsKey(key)) {
			DoublyLinkedListNode latestNode = map.get(key);
			removeNode(latestNode);
			setHead(latestNode);
			return latestNode.value;
		} else {
			return null;
		}
	}

	// 인자로 받은 node를 제거.
	// headNode와 tailNode인 예외처리 구현.
	public static void removeNode(DoublyLinkedListNode node) {
		DoublyLinkedListNode currentNode = node;
		DoublyLinkedListNode previousNode = currentNode.previousNode;
		DoublyLinkedListNode nextNode = currentNode.nextNode;

		// headNode가 아니라면,
		if (previousNode != null) {
			previousNode.nextNode = nextNode;
		} else {
			headNode = nextNode;
		}

		// tailNode가 아니라면,
		if (nextNode != null) {
			nextNode.previousNode = previousNode;
		} else {
			tailNode = previousNode;
		}
	}

	// 인자로 받은 node를 headNode의 앞으로 이동하고,
	// 새로운 headNode로 설정 및 tailNode가 없는 경우 tailNode로도 설정
	public static void setHead(DoublyLinkedListNode node) {
		node.nextNode = headNode;
		node.previousNode = null;
		if (headNode != null) {
			headNode.previousNode = node;
		}

		headNode = node;

		if (tailNode == null) {
			tailNode = node;
		}
	}

	public static void set(int key, String string) {
		if (map.containsKey(key)) {
			DoublyLinkedListNode oldNode = map.get(key);
			oldNode.value = string;
			removeNode(oldNode);
			setHead(oldNode);
		} else {
			DoublyLinkedListNode newNode = new DoublyLinkedListNode(key, string);
			if (length < capacity) {
				setHead(newNode);
				map.put(key, newNode);
				length++;
				// 캐쉬 capacity가 가득 찬 경우, 가장 오래전에 엑세스 된 tailNode를 제거
			}
			else {
				map.remove(tailNode.key);
				tailNode = tailNode.previousNode;
				if (tailNode != null) {
					tailNode.nextNode = null;
				}

				setHead(newNode);
				map.put(key, newNode);
			}
		}
	}

	private static boolean initialize() {

		try {
			Class.forName("com.mysql.jdbc.Driver");// 드라이버 로딩: DriverManager에 등록
		} catch (ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			return false;
		}

		try {
			mainConn = DriverManager.getConnection(MAIN_URL, MAIN_ID, MAIN_PASS);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		try {
			stmt = mainConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

}

class DoublyLinkedListNode {
	public String value;
	public int key;
	public DoublyLinkedListNode previousNode;
	public DoublyLinkedListNode nextNode;

	public DoublyLinkedListNode(int key, String string) {
		this.value = string;
		this.key = key;
	}
}