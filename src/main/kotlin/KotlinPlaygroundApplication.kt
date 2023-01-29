import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Profile

@Profile("prod")
@SpringBootApplication(scanBasePackages = ["org.mikehenry.kotlin_playground"])
@EntityScan("org.mikehenry.kotlin_playground")
class KotlinPlaygroundApplication

fun main(args: Array<String>) {
    val app = SpringApplication(KotlinPlaygroundApplication::class.java)
    app.run(*args)
}