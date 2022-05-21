package gladyshev.aVScanner.chain;

import com.github.katjahahn.parser.PESignature;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class isPE {

    static void scan(String path, String content) {
        File file = new File(path);
        PESignature peSignature = new PESignature(file);
        try {
            peSignature.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(peSignature.getInfo());

    }
}