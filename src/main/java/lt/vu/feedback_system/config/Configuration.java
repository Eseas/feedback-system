package lt.vu.feedback_system.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.cdi.Startup;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;


@Slf4j
@Startup
@ApplicationScoped
@Named("config")
public class Configuration {

    @Getter
    private final Properties props = new Properties();

    @PostConstruct
    private void init() {
        final String resourceName = "config.properties";
        final URL configURL = getClass().getClassLoader().getResource(resourceName);
        final String configPath = configURL == null ? null : configURL.getPath();
        if (configPath != null) {
            try {
                props.load(new FileReader(configPath));
                log.info(String.format("Properties loaded from file '%s': '%s'", configPath, props));
            } catch (IOException e) {
                log.error(String.format("Failed to load configuration properties: %s", e.getMessage()));
            }
        } else log.error(String.format("Resource '%s' was not found", resourceName));
    }

}
