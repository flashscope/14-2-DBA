import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class LongMaker {

	protected static MessageDigest md5 = null;
	protected static Random rand = new Random();
	
	
	LongMaker() {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	protected String getMd5( String data ) {
		md5.update(data.getBytes()); 
		byte byteData[] = md5.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	protected String getRandomUrl() {
		
		StringBuilder sb = new StringBuilder();
		sb.append( getMd5(System.nanoTime()+"") );
		sb.append( getMd5(rand.nextDouble()+"") );
		sb.append( getMd5(sb.hashCode()+"") );
		return sb.toString();

	}
}
