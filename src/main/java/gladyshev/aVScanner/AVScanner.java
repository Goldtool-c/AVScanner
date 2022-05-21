package gladyshev.aVScanner;

import java.io.File;
import java.util.*;

public class AVScanner {
    private static AVScanner instance;
    private AVScanner(){}
    public static AVScanner getInstance(){
        if(instance == null){
            instance = new AVScanner();
        }
        return instance;
    }
    public boolean scan(byte[] content, byte[] sequence)
    {
        boolean flag = false;
        int counter = 0;
        LinkedList<Byte> temp = new LinkedList<>();
        for (int i = 0; i < sequence.length; i++) {
            temp.add(content[i]);
            if(sequence[i]==content[i])
            {
                counter++;
            }
        }
        temp.poll();
        if(counter == sequence.length)
        {
            return true;
        }
        for (int i = sequence.length; i < content.length; i++) {
            temp.add(content[i]);
            counter=0;
            for (int j = 0; j < sequence.length; j++) {
                if(sequence[j] == temp.get(j))
                {
                    counter++;
                }
                if(counter==sequence.length)
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
            {
                break;
            }
            temp.poll();
        }
        return flag;
    }
}
