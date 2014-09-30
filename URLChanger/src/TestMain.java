
public class TestMain {

	
	public static void main(String[] args) {
		LongMaker maker = new LongMaker();
		
		DBHelper dbHelper = new MySqlHelper();
		//DBHelper dbHelper = new RedisHelper();
		
		dbHelper.Initializer();
		
		//String longUrl = maker.getRandomUrl();
		//dbHelper.insertDB(longUrl, "http://www.naver.com", "1234");
		//System.out.println( longUrl );
		
		String longUrl = "596054c7a8f99c3fd1a50ea81aed7d7adac9a24423e58daabcafcfd6a71b2e36bdb6d288859d2255d13c34384de82b13";
		String realUrl = dbHelper.selectDB(longUrl, "1234");
		System.out.println( realUrl );
	}

}
