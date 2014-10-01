
public class TestMain {
	public static void main(String[] args) {
		
		
		// for test
			DummyDataMaker ddm = new DummyDataMaker();
			ddm.generateDummyDatas();
		//
				
				
		LongMaker maker = new LongMaker();
		
		DBHelper dbHelper = new MySqlHelper();
//		DBHelper dbHelper = new RedisHelper();
		dbHelper.Initializer();
		
		long startTime = System.currentTimeMillis();
		// test
		for (int i = 0; i < GlobalDatas.DUMMY_NUM; ++i) {
			String longUrl = maker.getUrl();
			dbHelper.insertDB(longUrl, DummyDataMaker.dummyList.get(i), "1234");
			
			String realUrl = dbHelper.selectDB(longUrl, "1234");
			
			/*
			// for speed check
			if(!realUrl.equals(DummyDataMaker.dummyList.get(i))) {
				System.out.println("ERROR!");
				break;
			}
			System.out.println( realUrl );
			*/
		}
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("time:"+estimatedTime);
		/*
		String longUrl = maker.getUrl();
		dbHelper.insertDB(longUrl, "http://www.naver.com", "1234");
		System.out.println( longUrl );
		
		String realUrl = dbHelper.selectDB(longUrl, "1234");
		System.out.println( realUrl );
		
		*/
	}

}
