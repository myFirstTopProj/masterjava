package ru.javaops.web;

import lombok.extern.slf4j.Slf4j;

/**
 * gkislin
 * 09.01.2017
 */
@Slf4j
public class Statistics {
    public enum RESULT {
        SUCCESS, FAIL
    }

    public static void count(String payload, long startTime, RESULT result) {
        long now = System.currentTimeMillis();
        int ms = (int) (now - startTime);
        log.info(payload + " " + result.name() + " execution time(ms): " + ms);
        // place for statistics staff

    }

}
