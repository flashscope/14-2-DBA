
public class TestMain {

	
	public static void main(String[] args) {
		LongMaker maker = new LongMaker();
		
		//DBHelper dbHelper = new MySqlHelper();
		DBHelper dbHelper = new RedisHelper();
		
		dbHelper.Initializer();
		
		String longUrl = maker.getUrl();
		dbHelper.insertDB(longUrl, "http://www.naver.com", "1234");
		System.out.println( longUrl );
		
		String realUrl = dbHelper.selectDB(longUrl, "1234");
		System.out.println( realUrl );
	}

}
