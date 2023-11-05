package com.andrei.labbinary.controller;

import com.andrei.labbinary.dto.PersonDTO;
import com.andrei.labbinary.proto.PersonProto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class PersonController {

    @Autowired
    private KafkaTemplate<String, PersonProto.Person> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody PersonDTO personDTO) {
        PersonProto.Person person = PersonProto.Person.newBuilder()
                .setName(personDTO.getName())
                .build();
        kafkaTemplate.send("person", person);
        return ResponseEntity.ok("Message sent!");
    }
}
