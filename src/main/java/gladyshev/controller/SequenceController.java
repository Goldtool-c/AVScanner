package gladyshev.controller;

import gladyshev.DAO.DAO;
import gladyshev.config.SpringConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SequenceController implements Initializable {
    @FXML
    private Label seq;

    @FXML
    private Label title;

    @FXML
    private ScrollBar scroll;
    private int ptx = 10;
    private List<String> content = getContent();
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seq.setStyle("-fx-font-size: "+ptx);
        scroll.setMax(ptx*content.size());
        scroll.setMin(0);
        StringBuilder sb = new StringBuilder();
        for (String s : content) {
            sb.append(s);
            sb.append('\n');
        }
        seq.setText(sb.toString());
        scroll.valueProperty().addListener(event -> {
            title.setLayoutY(-scroll.getValue());
            seq.setLayoutY((title.getLayoutY()+32)-scroll.getValue());
        });
    }
    private List<String> getContent()
    {
        DAO dao = context.getBean(DAO.class);
        return dao.getAll();
    }
}
