package com.stockemotion.search.timer;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by piguanghua on 8/28/17.
 */
@Component
@Configurable
@EnableScheduling
public class Scheduler {
    //每天3：05执行
    @Scheduled(cron = "* */1 * * * *")
    public void testTasks() {
    }


}
