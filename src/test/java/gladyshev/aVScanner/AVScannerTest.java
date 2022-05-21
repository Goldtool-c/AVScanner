package gladyshev.aVScanner;


import org.junit.Test;
import static org.junit.Assert.*;

public class AVScannerTest {


    @Test
    public void scan() {
        String[] c = {"0xD", "0xA", "0xC", "0xF", "0x2D"};
        String[] s = {"0xC", "0xF"};//true
        String[] s1 = {"0xC", "0xF", "0x2D"};//true
        String[] s2 = {"0xD", "0xA"};//true
        String[] s3 = {"0xD", "0xC"};//false
        String[] s4 = {"0xD", "0xA", "0xC", "0x5D"};//false
        byte[] content = new byte[c.length];
        byte[] sequence = toByte(s);
        byte[] sequence1 = toByte(s1);
        byte[] sequence2 = toByte(s2);
        byte[] sequence3 = toByte(s3);
        byte[] sequence4 = toByte(s4);
        for (int i = 0; i < c.length; i++) {
            content[i] = Byte.decode(c[i]);
        }
        AVScanner scanner = AVScanner.getInstance();
        assertTrue(scanner.scan(content, sequence));
        assertTrue(scanner.scan(content, sequence1));
        assertTrue(scanner.scan(content, sequence2));
        assertFalse(scanner.scan(content, sequence3));
        assertFalse(scanner.scan(content, sequence4));
    }
    private byte[] toByte(String[] s)
    {
        byte[] sequence = new byte[s.length];
        for (int i = 0; i < s.length; i++) {
            sequence[i] = Byte.decode(s[i]);
        }
        return sequence;
    }
}
