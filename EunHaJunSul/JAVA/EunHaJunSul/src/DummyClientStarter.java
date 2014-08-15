
public class DummyClientStarter {

	public static void main(String[] args) {
		
		SimpleServer simpleServer = new SimpleServer();
		
		SignUpClient cli01 = new SignUpClient();
		GameClient cli02 = new GameClient();
		GameClient cli03 = new GameClient();
		GameClient cli04 = new GameClient();
		GameClient cli05 = new GameClient();
		
		
		if( !cli01.initialize() ) {
			System.out.println("error thread initilize");
		}
		if( !cli02.initialize() ) {
			System.out.println("error thread initilize");
		}
		if( !cli03.initialize() ) {
			System.out.println("error thread initilize");
		}
		if( !cli04.initialize() ) {
			System.out.println("error thread initilize");
		}
		if( !cli05.initialize() ) {
			System.out.println("error thread initilize");
		}
		
		
		Thread t01 = new Thread(cli01);
		Thread t02 = new Thread(cli02);
		Thread t03 = new Thread(cli03);
		Thread t04 = new Thread(cli04);
		Thread t05 = new Thread(cli05);
		
		
		
		t01.start();
		t02.start();
		t03.start();
		t04.start();
		t05.start();
		
		Thread tServer = new Thread(simpleServer);
		tServer.start();
		
		try {
			t01.join();
			t02.join();
			t03.join();
			t04.join();
			t05.join();
			tServer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("game over");
		
	}

}
