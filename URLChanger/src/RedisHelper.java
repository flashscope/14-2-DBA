import redis.clients.jedis.Jedis;


public class RedisHelper implements DBHelper {

	private Jedis jedis = null;
	
	@Override
	public boolean Initializer() {
		
		try {
			jedis = new Jedis(GlobalDatas.SERVER_URL);
		}catch(Exception e){
			System.out.println("JEDIS ERROR :" + e.getMessage());
			return false;
		}
		
		return true;
		
	}

	@Override
	public boolean insertDB(String longUrl, String realUrl, String password) {
		//if(null==jedis)
		jedis.set(longUrl+"_"+password, realUrl);
		return false;
	}

	@Override
	public String selectDB(String longUrl, String password) {
		return jedis.get(longUrl+"_"+password);
	}

}
