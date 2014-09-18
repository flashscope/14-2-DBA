import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class LongMaker implements URLMaker {

	private static MessageDigest md5 = null;
	private static Random rand = new Random();
	
	
	LongMaker() {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private String getMd5( String data ) {
		md5.update(data.getBytes()); 
		byte byteData[] = md5.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	private String getRandomUrl() {
		
		StringBuilder sb = new StringBuilder();
		sb.append( getMd5(System.nanoTime()+"") );
		sb.append( getMd5(rand.nextDouble()+"") );
		sb.append( getMd5(sb.hashCode()+"") );
		return sb.toString();

	}

	
	
	@Override
	public String getUrl() {
		return getRandomUrl();
	}
	
}
