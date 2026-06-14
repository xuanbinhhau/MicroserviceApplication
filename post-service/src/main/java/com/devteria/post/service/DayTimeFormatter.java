package com.devteria.post.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DayTimeFormatter {

    Map<Long, Function<Instant,String>> strategyMap = new LinkedHashMap<>();

    public DayTimeFormatter() {
        strategyMap.put(60L,this::formatInSecond);
        strategyMap.put(3600L,this::formatInMinutes);
        strategyMap.put(86400L,this::formatInHours);
        strategyMap.put(Long.MAX_VALUE,this::formatInDays);
    }

    private String formatInSecond(Instant instant){
        long elapseSeconds = ChronoUnit.SECONDS.between(instant,Instant.now());
        return elapseSeconds + "Seconds";
    }
    private String formatInMinutes(Instant instant){
        long elapseMinutes = ChronoUnit.MINUTES.between(instant,Instant.now());
        return elapseMinutes + "Minutes";
    }

    private String formatInHours(Instant instant){
        long elapHours = ChronoUnit.HOURS.between(instant,Instant.now());
        return elapHours + "Hourss";
    }

    private String formatInDays(Instant instant){
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;

        return localDateTime.format(dateTimeFormatter);
    }

    public String format(Instant instant){
        long elapseSeconds = ChronoUnit.SECONDS.between(instant,Instant.now());

        var statege = strategyMap.entrySet().stream().filter(longFunctionEntry -> {
            return elapseSeconds < longFunctionEntry.getKey();
        }).findFirst().get();

       return statege.getValue().apply(instant);
    }

}
