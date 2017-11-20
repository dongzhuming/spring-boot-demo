package org.molecule.demo.bootifulreactive;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.util.Random;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@SpringBootApplication
@EnableWebFlux
@RestController
public class BootifulReactiveApplication {

    @Bean
    RouterFunction<ServerResponse> router(PersonHandler personHandler) {
        return route(GET("/persons").and(accept(APPLICATION_JSON)), request->personHandler.all())
                .andRoute(GET("/spersons/{id}").and(accept(APPLICATION_JSON)), request->personHandler.byId(request));
    }

//    @Bean
//    HttpServer server(RouterFunction<?> route) {
//        HttpHandler httpHandler = RouterFunctions.toHttpHandler(route);
//        HttpServer httpServer = HttpServer.create(8080);
//        httpServer.start(new ReactorHttpHandlerAdapter(httpHandler));
//        return httpServer;
//    }



    public static void main(String[] args) {
        SpringApplication.run(BootifulReactiveApplication.class, args);

    }
}

@Component
class PersonHandler {
    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> all() {
        Flux<Person> people = personRepository.all();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, Person.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        Mono<Person> person = personRepository.findById(request.pathVariable("id"));
        return ServerResponse.ok().body(BodyInserters.fromPublisher(person, Person.class));
    }
}

@Component
class SampleDataCLR implements CommandLineRunner {
    private final PersonRepository personRepository;

    @Autowired
    public SampleDataCLR(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... Strings) throws Exception {
        Stream.of("a", "b", "c").forEach((name ->
                personRepository.save(new Person(name, new Random().nextInt(100)))));
        personRepository.findAll().toStream().forEach(System.out::println);
    }
}

@Repository
interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    @Query("{}")
    Flux<Person> all();
}

@Document
class Person {
    @Id
    private String id;
    private String name;
    private int age;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

