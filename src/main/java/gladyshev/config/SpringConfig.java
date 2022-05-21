package gladyshev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("gladyshev")
public class SpringConfig {
    private final ApplicationContext context;
    @Autowired
    public SpringConfig(ApplicationContext context)
    {
        this.context = context;
    }

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dmds= new DriverManagerDataSource();
        dmds.setDriverClassName("org.postgresql.Driver");
        dmds.setUrl("jdbc:postgresql://localhost:5432/threatBytes");
        dmds.setUsername("postgres");
        dmds.setPassword("123456");
        return dmds;
    }
    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate(dataSource());
    }
}
