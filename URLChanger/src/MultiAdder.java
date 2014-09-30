
public class MultiAdder {

	public static void main(String[] args) {
		
		long startTime = System.nanoTime();    
		
		double result = 0;
		for (int i = 0; i < 10000; ++i) {
		
			if( (i & 1) == 0) {
			//if(i%2 == 0) {
				result += (i*i);
			}
			
		}
		
		// ... the code being measured ...    
		long estimatedTime = System.nanoTime() - startTime;
		
		System.out.println("Result:"+result + " Time:" + estimatedTime);
		
	}

}
