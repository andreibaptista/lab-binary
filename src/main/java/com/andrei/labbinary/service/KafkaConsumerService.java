package com.andrei.labbinary.service;

import com.andrei.labbinary.avro.Person;
import com.andrei.labbinary.repository.PersonRepository;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class KafkaConsumerService {

    @Autowired
    PersonRepository personRepository;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "person", groupId = "person-group")
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            DatumReader<Person> datumReader = new SpecificDatumReader<>(Person.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(record.value(), null);
            Person personAvro = datumReader.read(null, decoder);
            System.out.println("Consumed message: " + personAvro.toString());

            com.andrei.labbinary.model.Person person = new com.andrei.labbinary.model.Person(personAvro.getName().toString());
            personRepository.save(person);

        } catch (Exception e) {
            logger.error("Error while consuming message", e);
        }
    }
}
