
public class RedisHelper implements DBHelper {

	@Override
	public boolean Initializer() {
		return false;
	}

	@Override
	public boolean insertDB(String longUrl, String realUrl, String password) {
		return false;
	}

	@Override
	public String selectDB(String longUrl, String password) {
		return "";
	}

}
