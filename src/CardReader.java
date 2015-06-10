import java.io.UnsupportedEncodingException;
import javax.smartcardio.*;

public class CardReader {

	public CardReader() {

	}
	
	//wallet
	public static byte[] SelectAID = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07,
		(byte) 0xF0, (byte) 0x39, (byte) 0x41, (byte) 0x48, (byte) 0x14, (byte) 0x81, (byte) 0x00, (byte) 0x00
	};

	public static byte[] addMoney = new byte[]{(byte) 0x80, (byte) 0x01, (byte) 0x00, (byte) 0x00, 
		(byte) 0x01, (byte) 0x64};
	
	public static byte[] subMoney = new byte[]{(byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00, 
		(byte) 0x01, (byte) 0x34};
	
	public static byte[] checkBalance = new byte[]{(byte) 0x80, (byte) 0x03, (byte) 0x00, (byte) 0x00};
	
	public static void main(String[] args) throws UnsupportedEncodingException {

		TerminalFactory terminalFactory = TerminalFactory.getDefault();

		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					System.out.println("SelectAID ");
					CommandAPDU command = new CommandAPDU(SelectAID);
					ResponseAPDU response = channel.transmit(command);
					byte recv[] = response.getBytes();
					for (int i = 0; i < recv.length; i++) {
						System.out.print(String.format("%02X", recv[i]));
					}
					System.out.println("");

					System.out.println("addMoney ");
					command = new CommandAPDU(addMoney);
					response = channel.transmit(command);
					
					byte recv2[] = response.getBytes();				
					for (int i = 0; i < recv2.length; i++) {
						System.out.print(String.format("%02X", recv2[i]));
					}
					System.out.println("");
					
					System.out.println("subMoney ");
					command = new CommandAPDU(subMoney);
					response = channel.transmit(command);
					
					byte recv3[] = response.getBytes();
					for (int i = 0; i < recv3.length; i++) {
						System.out.print(String.format("%02X", recv3[i]));
					}
					System.out.println("");
					
					System.out.println("check balance ");
					command = new CommandAPDU(checkBalance);
					response = channel.transmit(command);
					
					byte recv4[] = response.getBytes();
					for (int i = 0; i < recv4.length; i++) {
						System.out.print(String.format("%02X", recv4[i]));
					}
					System.out.println("");

				} catch (javax.smartcardio.CardNotPresentException e) {
					// e.printStackTrace();
					continue;
				} catch (CardException e) {
					// e.printStackTrace();
					continue;
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (CardException e) {
			e.printStackTrace();
		}

	}
}
