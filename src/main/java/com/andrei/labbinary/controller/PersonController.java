package com.andrei.labbinary.controller;

import com.andrei.labbinary.avro.Person;
import com.andrei.labbinary.dto.PersonDTO;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/send")
public class PersonController {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private static final String TOPIC = "person";

    @PostMapping
    public String sendMessage(@RequestBody PersonDTO personDTO) {
        Person person = Person.newBuilder().setName(personDTO.getName()).build();

        byte[] avroBytes = serializeAvroPerson(person);

        kafkaTemplate.send(TOPIC, avroBytes);
        return "Message sent successfully!";
    }

    private byte[] serializeAvroPerson(Person person) {
        byte[] result = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
        DatumWriter<Person> datumWriter = new SpecificDatumWriter<>(Person.class);

        try {
            datumWriter.write(person, binaryEncoder);
            binaryEncoder.flush();
            byteArrayOutputStream.close();
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
