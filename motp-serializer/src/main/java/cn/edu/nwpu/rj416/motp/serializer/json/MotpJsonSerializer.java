package cn.edu.nwpu.rj416.motp.serializer.json;



import cn.edu.nwpu.rj416.motp.serializer.motp.MSerializeException;
import cn.edu.nwpu.rj416.type.util.TypeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;


public class MotpJsonSerializer {

    private static final MotpJsonSerializer INSTANCE = new MotpJsonSerializer();

    public static MotpJsonSerializer getInstance() {
        return INSTANCE;
    }


    public byte[] serialize(Object o) {
        String json;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new MSerializeException("序列化失败", e);
        }
        return json.getBytes(Charset.forName("utf-8"));
    }


    public Object deserialize(byte[] buffer, Type type) throws MSerializeException {
        String json = new String(buffer, Charset.forName("utf-8"));
        if (type == null) {
            switch (json.trim().charAt(0)) {
                case '{':
                    type = HashMap.class;
                    break;
                case '[':
                    type = ArrayList.class;
                    break;
                default:
                    break;
            }
        }
        try {
            Class<?> clazz = TypeUtil.getRawType(type);
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new MSerializeException("反序列化失败", e);
        }
    }


    public Object deserialize(byte[] buffer) throws MSerializeException {
        return this.deserialize(buffer, null);
    }


    public String toJson(Object o) throws MSerializeException {
        String json;
        try {
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new MSerializeException("序列化失败", e);
        }
        return json;
    }

    public <T> T toObject(String json, Class<T> clazz) throws MSerializeException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new MSerializeException("反序列化失败", e);
        }
    }
}
