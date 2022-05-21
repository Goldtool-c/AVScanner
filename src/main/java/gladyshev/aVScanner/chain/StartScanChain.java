package gladyshev.aVScanner.chain;

import gladyshev.DAO.DAO;
import gladyshev.aVScanner.AVScanner;
import gladyshev.config.SpringConfig;
import gladyshev.util.Controllers;
import gladyshev.util.Logger;
import javafx.scene.control.Label;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StartScanChain extends MiddleScanChain {
    private AVScanner scanner = AVScanner.getInstance();
    private DAO sequenceDao;
    private boolean flag = false;
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    private static Logger LOGGER = Logger.getInstance();
    public StartScanChain(String path, DAO dao)
    {
        this.scanPath = path;
        sequenceDao = dao;
    }
    private StartScanChain(String path, MiddleScanChain prev, DAO dao)
    {
        this.scanPath = path;
        this.prev = prev;
        sequenceDao = dao;
    }
    @Override
    public void scan()
    {
        File file = new File(this.scanPath);
        String[] files = file.list();
        if(files!=null)
        {
            scanDir(files);
        } else
        {
            System.out.println("Scanning " +scanPath);
            scanFile(file);
        }
        if(flag)
        {
            Label label = Controllers.getInstance().getLogController().getLogs();
            String str = label.getText();
            label.setText(str+"\nScanning finished");
            System.out.println("Scanning finished");
        }
    }
    private void scanDir(String[] files)
    {
        StringBuilder sb = new StringBuilder();
        String temp;
        sb.append(scanPath);
        for (int i = 0; i < files.length; i++) {
            sb.append("\\");
            sb.append(files[i]);
            temp = sb.toString();
            this.next = new StartScanChain(temp, this, sequenceDao);
            next.scan();
            temp = scanPath;
            sb = new StringBuilder();
            sb.append(temp);
        }
    }
    private void scanFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(this.scanPath)) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        String content = sb.toString();
        if (content.length() >= 2) {
            sb = new StringBuilder();
            sb.append(content.charAt(0));
            sb.append(content.charAt(1));
            if (sb.toString().equals("MZ")) {
                System.out.println("MZ signature found");
                isPE.scan(scanPath, content);
                System.out.println(scanPath + " is executable");
                byte[] byteContent = new byte[0];
                try {
                    byteContent = Files.readAllBytes(Paths.get(scanPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < sequenceDao.getNumOfSequences(); i++) {
                    if (scanner.scan(byteContent, sequenceDao.getSequence(i + 1))) {
                        Label label = Controllers.getInstance().getLogController().getLogs();
                        String sb2 = label.getText() +
                                '\n' +
                                "Threat found in " + scanPath +
                                '\n' +
                                "Threat id = " + (i + 1);
                        label.setText(sb2);
                        Controllers.getInstance().getLogController().incMaxScroll();
                        LOGGER.log("Threat found in " + scanPath+"\n"+"Threat id = " + (i + 1));
                        System.out.println("Threat found in " + scanPath);
                        System.out.println("Threat id = " + (i + 1));
                    }
                }

                //todo log
            }
            System.gc();
        }
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
        Controllers.getInstance().getLogController().setMaxScroll(0);
    }
}
