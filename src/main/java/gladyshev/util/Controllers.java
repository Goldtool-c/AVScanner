package gladyshev.util;

import gladyshev.controller.LogController;
import gladyshev.controller.MainController;
import gladyshev.service.ScanService;

public class Controllers {
    private static Controllers instance;
    private LogController logController;
    private MainController mainController;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Controllers (){}
    public static Controllers  getInstance(){
        if(instance == null){
            instance = new Controllers ();
        }
        return instance;
    }

    public LogController getLogController() {
        return logController;
    }

    public void setLogController(LogController logController) {
        this.logController = logController;
    }
}
