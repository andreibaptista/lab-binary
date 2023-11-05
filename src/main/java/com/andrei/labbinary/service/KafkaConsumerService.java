package com.andrei.labbinary.service;

import com.andrei.labbinary.model.Person;
import com.andrei.labbinary.proto.PersonProto;
import com.andrei.labbinary.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    PersonRepository personRepository;

    @KafkaListener(topics = "person", groupId = "person-group")
    public void consume(PersonProto.Person personProto) {
        System.out.println("Consumed message: " + personProto.toString());
        Person person = new Person(personProto.getName());
        personRepository.save(person);
    }
}
