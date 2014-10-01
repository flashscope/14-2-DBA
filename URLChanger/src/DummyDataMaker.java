import java.util.ArrayList;


public class DummyDataMaker {
	public static ArrayList<String> dummyList = new ArrayList<String>();
	
	public static void generateDummyDatas() {
		dummyList.clear();
		
		for (int i = 0; i < GlobalDatas.DUMMY_NUM; ++i) {
			dummyList.add("http://www.abc"+i+".com");
		}
	}
	
}
