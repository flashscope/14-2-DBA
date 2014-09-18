
public interface DBHelper {
	
	public boolean Initializer();
	public boolean insertDB(String longUrl, String realUrl, String password);
	public String selectDB(String longUrl, String password);
	
}
