package com.stockemotion.search.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.mockito.internal.util.collections.Sets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pigaunghua on 2016/12/6.
 * 该配置文件主要是用于加载非法禁用词
 * IKAnalyzer stopWord在这个项目中分三部分：stopword.dir是停用词  mydic.dic是禁用词  ext.dir扩展的股票
 */
@Slf4j
public class AnalyzerConfig {
    private static Set<String> stopSet = null;

    private static Lock instanceLock = new ReentrantLock();

    private static InputStream in = null;

    /**
     * 加载analyzer 文件流
     */
    static
    {
        in = AnalyzerConfig.class.getResourceAsStream("/mydic.dic");
    }

    public static Set<String> getStopSet()
    {
        if (stopSet == null)
        {
            instanceLock.lock();
            try
            {

                if (stopSet == null)
                {
                    AnalyzerConfig.createStopSet();
                    List<String> list = IOUtils.readLines(in);
                    stopSet.addAll(list);
                }

            }
            catch (IOException e)
            {
                log.error("[api err, /readFileByLines]", e);
            }
            finally
            {
                IOUtils.closeQuietly(in);
                instanceLock.unlock();
            }
        }
        return stopSet;
    }


    public static void createStopSet()
    {
        stopSet = Sets.newSet();
    }

}
