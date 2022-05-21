package gladyshev.service;

import gladyshev.DAO.DAO;

public class AddSequenceService {
    private static AddSequenceService instance;
    private AddSequenceService(){}
    public static AddSequenceService getInstance(){
        if(instance == null){
            instance = new AddSequenceService();
        }
        return instance;
    }
    public void addSequence(DAO dao)
    {

    }
}
