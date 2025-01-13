import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableFeignClients
@Profile("prod")
@SpringBootApplication(scanBasePackages = ["org.mikehenry.kotlin_playground"])
@EntityScan("org.mikehenry.kotlin_playground.domain.entity")
@EnableJpaRepositories(basePackages=["org.mikehenry.kotlin_playground.domain.repository"])
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
    val app = SpringApplication(KotlinPlaygroundApplication::class.java)
    app.run(*args)
}