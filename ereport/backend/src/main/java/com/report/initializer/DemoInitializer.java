package com.report.initializer;

import com.google.common.collect.ImmutableBiMap;
import com.report.entity.Person;
import com.report.entity.classroom.ClassRoom;
import com.report.repository.ClassRoomRepository;
import com.report.repository.PersonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Log4j2
@Component
//@Profile("demo")
public class DemoInitializer implements ApplicationListener<ApplicationReadyEvent> {


    private final PersonRepository personRepository;
    private final ClassRoomRepository classRepository;


    public DemoInitializer(PersonRepository repository, ClassRoomRepository classRepository) {
        this.personRepository = repository;
        this.classRepository = classRepository;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        try {

            personRepository.deleteAll().thenMany(
                    Flux.just(new Person("123", "John"),
                            new Person("125", "Mary"),
                            new Person("124", "Peter"),
                            new Person("126", "John")).flatMap(personRepository::save)).subscribe();

            List<ClassRoom> classRooms = Arrays.asList(
                    new ClassRoom("small 1", "2019-01").setCapacity(15).setRoomNumber("3").setDescription("a lovely room for new alpacas!"),
                    new ClassRoom("small 2", "2019-01").setCapacity(15).setRoomNumber("4").setDescription("a lovely room for teddy bear !"),
                    new ClassRoom("small 3", "2019-01").setCapacity(15).setRoomNumber("5").setDescription("a lovely room for baby goats !"),

                    new ClassRoom("medium 1", "2019-01").setCapacity(17).setRoomNumber("6").setDescription("let your dreams bloom!"),
                    new ClassRoom("medium 2", "2019-01").setCapacity(17).setRoomNumber("7").setDescription("who's enter here always smile"),
                    new ClassRoom("medium 3", "2019-01").setCapacity(17).setRoomNumber("8").setDescription("a little spark of kindness can put a colossal burst of sunshine"),


                    new ClassRoom("large 1", "2019-01").setCapacity(20).setRoomNumber("7").setDescription("think big start small!"),
                    new ClassRoom("large 2", "2019-01").setCapacity(20).setRoomNumber("8").setDescription("you are going to be great , keep going !"),
                    new ClassRoom("large 3", "2019-01").setCapacity(20).setRoomNumber("9").setDescription("start each day with a grateful heart"));


            classRepository.deleteAll().
                    thenMany(
                            Flux.fromIterable(classRooms).flatMap(classRepository::save)
                    ).subscribe();


            WebClient webClient = WebClient.create();
            classRooms.stream().forEach(c -> uploadImage(buildPart(c.getRoomNumber(), c.getKey().getYear_month()), webClient));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private MultiValueMap<String, Object> buildPart(String number, String year_month) {

        MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
        try {
            data.add("number", number);
            data.add("year_month", year_month);
            data.add("file", new UrlResource(String.format("classpath:/image/room/00%s.jpg", number)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private void uploadImage(MultiValueMap<String, Object> data, WebClient webClient) {
        webClient.post()
                .uri("http://localhost:8080/image/room")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", basicAuth("user", "user"))
                .body(BodyInserters.fromMultipartData(data))
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class))
                .flux().subscribe();
    }

    private static String basicAuth(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }


}
