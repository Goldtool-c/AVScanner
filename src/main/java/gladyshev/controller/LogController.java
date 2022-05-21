package gladyshev.controller;

import gladyshev.util.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LogController implements Initializable {

    @FXML
    private Label logs;
    {
        Controllers.getInstance().setLogController(this);
    }
    @FXML
    private ScrollBar scroll;
    private int maxScroll;
    private int ptx = 10;
    public void incMaxScroll()
    {
        maxScroll=maxScroll+(ptx*2);
    }
    public ScrollBar getScroll() {
        return scroll;
    }

    public void setScroll(ScrollBar scroll) {
        this.scroll = scroll;
    }

    public int getMaxScroll() {
        return maxScroll;
    }

    public void setMaxScroll(int maxScroll) {
        this.maxScroll = maxScroll;
    }

    public Label getLogs() {
        return logs;
    }

    public void setLogs(Label logs) {
        this.logs = logs;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logs.setText("logs:\n");
        logs.setStyle("-fx-font-size: "+ptx);
        scroll.setMin(0.0);
        scroll.valueProperty().addListener(event -> {
            scroll.setMax(maxScroll);
            logs.setLayoutY(-scroll.getValue());
        });
    }
}
