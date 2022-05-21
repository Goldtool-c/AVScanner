package gladyshev.controller;

import gladyshev.DAO.DAO;
import gladyshev.config.SpringConfig;
import gladyshev.service.ScanService;
import gladyshev.util.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button scan;
    @FXML
    private Button addSequence;
    @FXML
    private TextField sequence;

    @FXML
    private Button seq;
    private String sequenceStr;

    public Label getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    @FXML
    private Label errorLabel;


    private ScanService scanService = ScanService.getInstance();
    {
        Controllers.getInstance().setMainController(this);
    }
    private ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scan.setOnAction(event -> scanService.scan(context.getBean(DAO.class)));
        sequence.textProperty().addListener((observable, oldValue, newValue) ->
                sequenceStr = newValue);
        addSequence.setOnAction(event -> context.getBean(DAO.class).setSequence(sequenceStr));
        seq.setOnAction(event -> {
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/stage/SequenceStage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Sequences");
            stage.setScene(new Scene(root, 378, 252));
            stage.show();
        });
    }
}
