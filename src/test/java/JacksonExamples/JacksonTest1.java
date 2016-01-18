package JacksonExamples;

import JacksonExamples.Entity.AccountBean;
import JacksonExamples.Entity.Birthday;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class JacksonTest1 {
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;

    @Before
    public void init() throws IOException {
        objectMapper = new ObjectMapper();
        jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
    }

    //将java对象转换成json字符串
    @Test
    public void writeEntityJSON() throws IOException {
        AccountBean bean = new AccountBean(1, "liang", "a@a.com", "abc street", new Birthday("1985-01-01"));

        //jsonGenerator.writeObject可以转换java对象，eg: JavaBean / Map / List / Array等
        //objectMapper.writeValue有相同的功能
        objectMapper.writeValue(System.out, bean);
        jsonGenerator.writeObject(bean);
    }

    @Test
    public void readJson2Entity() {
        String json = "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}";
        try {
            AccountBean acc = objectMapper.readValue(json, AccountBean.class);
            System.out.println(acc.getName());
            System.out.println(acc);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void destory() {
        try {
            if (jsonGenerator != null) {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed()) {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}