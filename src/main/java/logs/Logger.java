package logs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements ILogger{
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");

    @Override
    public void logClient(String str) {
        System.out.println("[CLIENT]" + format.format(new Date()) + str);
    }

    @Override
    public void logServer(String str) {
        System.out.println("[SERVER]" + format.format(new Date()) + str);
    }
}
