package spirit.db;

import org.hsqldb.server.Server;

public class HSQLServer {
	private Server hsqlServer;
	private static HSQLServer instance;
	public static HSQLServer getInstance(){
		if(instance==null)
			instance=new HSQLServer();
		return instance;
	}
	private HSQLServer() {
		
            hsqlServer = new Server();

            // HSQLDB prints out a lot of informations when
            // starting and closing, which we don't need now.
            // Normally you should point the setLogWriter
            // to some Writer object that could store the logs.
            hsqlServer.setLogWriter(null);
            hsqlServer.setSilent(true);

            // The actual database will be named 'xdb' and its
            // settings and data will be stored in files
            // testdb.properties and testdb.script
            hsqlServer.setDatabaseName(0, "SpiritDB");
            hsqlServer.setDatabasePath(0, "file:spiritdb");
           /* hsqlServer.stop();
            hsqlServer.shutdown();*/
	}
	public void startDatabase(){
		hsqlServer.start();
	}
	public void stopFatabase(){
		hsqlServer.stop();
	}
}
