package com.pq.api.web.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author lich
 * @date 15/3/5
 */
public class Jackson2FactoryBean extends Jackson2ObjectMapperFactoryBean {

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        ObjectMapper objectMapper = getObject();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(jsonDateFormat());
    }

    private DateFormat jsonDateFormat(){
        DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dataFormat;
    }
}
