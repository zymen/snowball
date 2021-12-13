package hello

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

class Solution {
    private var index = 1
    private val test = "LTFTFTFTRTRTRT"

    fun getNextStep() = test[(index++ % test.length) - 1].toString()
}

@SpringBootApplication
class KotlinApplication {

    @Bean
    fun solution() = Solution()

    @Bean
    fun routes(solution: Solution) = router {

        GET {
            ServerResponse.ok().body(Mono.just("Let the battle begin!"))
        }

        POST("/**", accept(APPLICATION_JSON)) { request ->
            // request.bodyToMono(ArenaUpdate::class.java).flatMap { arenaUpdate ->
            //  println(arenaUpdate)
            ServerResponse.ok().body(
                Mono.just(solution.getNextStep())
            )
            // }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<KotlinApplication>(*args)
}

data class ArenaUpdate(val _links: Links, val arena: Arena)
data class PlayerState(val x: Int, val y: Int, val direction: String, val score: Int, val wasHit: Boolean)
data class Links(val self: Self)
data class Self(val href: String)
data class Arena(val dims: List<Int>, val state: Map<String, PlayerState>)
