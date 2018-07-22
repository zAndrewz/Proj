package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Messages {

    @Value("${app.locale}")
    String locale;

    @Autowired
    @Lazy
    private MessageSource ms;

    public String serverStarted(int port){
        String str = ms.getMessage("server.start", new String[]{String.valueOf(port)}, Locale.forLanguageTag(locale));
        return str;
    }

    public String clientConnect(String name){
        return ms.getMessage("user.connected", new String[]{String.valueOf(name)}, Locale.forLanguageTag(locale));
    }

    public String clientLeft(String name) {
        return ms.getMessage("user.left", new String[]{String.valueOf(name)}, Locale.forLanguageTag(locale));
    }
}
