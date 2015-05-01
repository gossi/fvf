package gnu.io;

public class RXTXHack {
	private RXTXHack() {
        
    }
    
    public static void closeRxtxPort(RXTXPort port) {
        port.IOLocked = 0;
        port.close();
    }
}
