// ProtobufSerializer.java
package com.andrei.labbinary.config;

import com.andrei.labbinary.proto.PersonProto;
import org.apache.kafka.common.serialization.Serializer;

public class ProtobufSerializer implements Serializer<PersonProto.Person> {

    @Override
    public byte[] serialize(String topic, PersonProto.Person data) {
        return data.toByteArray();
    }
}
