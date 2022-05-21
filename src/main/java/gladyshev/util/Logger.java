package gladyshev.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    private static Logger instance;
    private Logger() { }

    public static Logger getInstance() {
        if(instance==null)
        {
            instance = new Logger();
        }
        return instance;
    }
    public void log(String message)
    {
        message = new Date().toString()+"::"+ message;
        try(FileWriter writer = new FileWriter("logs.log", true))
        {
            writer.write(message);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
