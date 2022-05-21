package gladyshev.service;

import gladyshev.DAO.DAO;
import gladyshev.aVScanner.chain.StartScanChain;
import gladyshev.controller.MainController;
import gladyshev.util.Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ScanService {
    private static ScanService instance;
    private ScanService(){}
    public static ScanService getInstance(){
        if(instance == null){
            instance = new ScanService();
        }
        return instance;
    }
    public void scan(DAO dao)
    {
        if(dao.getNumOfSequences()!=0) {
            MainController controller = Controllers.getInstance().getMainController();
            Label label = controller.getErrorLabel();
            label.setText("");
            Stage stage = new Stage();
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            configuringDirectoryChooser(directoryChooser);
            File dir = directoryChooser.showDialog(stage);
            StartScanChain scan = new StartScanChain(dir.getAbsolutePath(), dao);
            scan.setFlag(true);
            Stage stage1 = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/stage/LogStage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage1.setTitle("Logs");
            stage1.setScene(new Scene(root, 346, 203));
            stage1.show();
            scan.scan();
        } else {
            MainController controller = Controllers.getInstance().getMainController();
            Label label = controller.getErrorLabel();
            label.setText("Database of threatening sequences is empty\nPlease, add at least 1 sequence to database");
            label.setStyle("-fx-text-fill: red");
        }
    }

    private void configuringDirectoryChooser(DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("Select directory to scan");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }
}
