package network;

import java.rmi.RemoteException;

import components.PlayerImpl;

/**This is a script that mocks up a session as if it was run and tests at each stage that the situation in the 
 * QuizServer remains as expected. As soon as the server encounters a situation that is unexpected it will terminate.
 * 
 * 
 * @author Jamie
 *
 */
public class QuizServerTestScript {
	
	

	public static void main(String[] args) {
		QuizServerTestScript script = new QuizServerTestScript();
		try {
			script.launch();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**Note the server must be running separately for this to work. 
	 * 
	 */
	public void launch() throws RemoteException{
		
		//Opens up a setup client
		SetupClient sc = new SetupClient();
		try{
			sc.launch();
		}catch (java.security.AccessControlException ex){
			System.err.println("SetupClient failed to connect");
			ex.printStackTrace();
		}
		
		//creates a new player
		if (!sc.createPlayer("Player 0"))
			systemExit("Initial SC Login failed - status false ");
		if (sc.player != new PlayerImpl(0, "Player 0"))
			systemExit("Initial SC Login failed - player not assigned correctly. Instead: "+sc.player);

		
		
		System.out.println("Tests complete");
	}
	
	public void systemExit(String message){
		System.err.println(message);
		System.exit(0);
	}

}
