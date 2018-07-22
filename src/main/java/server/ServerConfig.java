package server;


import logs.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ResourceBundle;

@Configuration
public class ServerConfig {

    @Bean
    public Server server() {return new Server();}

    @Bean
    public ClientHandlerFactory clientHandlerFactory(){return new ClientHandlerFactory();}

    @Bean
    public Logger log() {return new Logger();}

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource =
                new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
