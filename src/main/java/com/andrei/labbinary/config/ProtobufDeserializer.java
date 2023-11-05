package com.andrei.labbinary.config;

import com.andrei.labbinary.proto.PersonProto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class ProtobufDeserializer implements Deserializer<PersonProto.Person> {

    @Override
    public PersonProto.Person deserialize(String topic, byte[] data) {
        try {
            return PersonProto.Person.parseFrom(data);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing from Protobuf message", e);
        }
    }
}
