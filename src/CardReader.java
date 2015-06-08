import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.smartcardio.*;

public class CardReader {

	public CardReader() {

	}

	//健保卡
//	public static byte[] SelectAPDU = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x10, (byte) 0xD1,
//			(byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x11, (byte) 0x00};
//	public static byte[] ReadProfileAPDU = new byte[]{(byte) 0x00, (byte) 0xca, (byte) 0x11, (byte) 0x00, (byte) 0x02, (byte) 0x00,
//			(byte) 0x00};

	//eticket
	public static byte[] SelectAPDU = new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x07, (byte) 0x65,
			(byte) 0x54, (byte) 0x69, (byte) 0x63, (byte) 0x6b, (byte) 0x65, (byte) 0x74
	};
//	public static byte[] ReadProfileAPDU = new byte[]{(byte) 0x80, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00};
	public static byte[] ReadProfileAPDU = new byte[]{(byte) 0x80, (byte) 0x02, (byte) 0x00, (byte) 0x00, 
		(byte) 0x0C, (byte) 0x33, (byte) 0x62, (byte) 0x6b, (byte) 0x67, (byte) 0x30, (byte) 0x31, (byte) 0x75,
		(byte) 0x65, (byte) 0x6e, (byte) 0x6f, (byte) 0x34, (byte) 0x6a};
	
	public static void main(String[] args) throws UnsupportedEncodingException {

		TerminalFactory terminalFactory = TerminalFactory.getDefault();

		try {
			for (CardTerminal terminal : terminalFactory.terminals().list()) {
				System.out.println(terminal.getName());
				try {
					Card card = terminal.connect("*");
					CardChannel channel = card.getBasicChannel();

					CommandAPDU command = new CommandAPDU(SelectAPDU);
					ResponseAPDU response = channel.transmit(command);

					command = new CommandAPDU(ReadProfileAPDU);
					response = channel.transmit(command);

					// 健保卡 only
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 0, 12)));                    // 卡號
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 12, 32), "Big5").trim());    // 姓名
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 32, 42)));                   // 身分證號
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 42, 49)));                // 出生年月日
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 49, 50)));                    // 性別
//					System.out.println(new String(Arrays.copyOfRange(response.getData(), 50, 57)));                 // 發卡年月日

//					System.out.println(response.getData());
					byte recv[] = response.getData();
					for (int i = 0; i < recv.length; i++) {

						System.out.print(String.format("%02X", recv[i]));
//						System.out.println( String.format("0x%02X", encryptedText[i]) );
//						System.out.println( Integer.toHexString(encryptedText[i]) );	
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
