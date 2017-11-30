package com.stockemotion.search.service;

import com.stockemotion.search.config.AnalyzerConfig;
import com.stockemotion.search.config.AnalyzerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

/**
 * Created by pigaunghua on 2016/12/6.
 */
@Slf4j
@Component
public class ForbidWordService {

    @Resource
    AnalyzerService analyzerService;

    public String fillForbidWord(String sequence)
    {
        Analyzer analyzer = analyzerService.getAnalyzerService();
        Set<String> set = AnalyzerConfig.getStopSet();
        TokenStream token = null;
        try
        {
            //token = analyzer.tokenStream("a", new StringReader(sequence));
            Reader reader = new StringReader("sequence");
            token = analyzer.tokenStream("", reader);

            token.reset();
            CharTermAttribute term = token.addAttribute(CharTermAttribute.class);//term信息

            //	Set<String> tokenString = new HashSet<>();
            while (token.incrementToken())
            {
                log.info("term="+term.toString());
                //tokenString.add(term.toString());
                if (set.contains(term.toString()))
                {
                    sequence = sequence.replace(term.toString(), dynamicString(term.toString().length()));
                }
            }


        }
        catch (IOException e)
        {
            log.error("ForbidWordService fillForbidWord", e);
        }
        finally
        {
            try
            {
                if (token != null)
                {
                    token.end();
                    token.close();
                }
            }
            catch (IOException e)
            {
                log.error("ForbidWordService fillForbidWord reset", e);
            }
            return sequence;
        }
    }


  /*  public boolean findForbidWord(String sequence)
    {

        Set<String> set = AnalyzerConfig.getStopSet();
        TokenStream token = null;
        try
        {
            token = analyzer.tokenStream("a", new StringReader(sequence));
            token.reset();
            CharTermAttribute term = token.addAttribute(CharTermAttribute.class);//term信息

            //	Set<String> tokenString = new HashSet<>();
            while (token.incrementToken())
            {
                //tokenString.add(term.toString());
                if (set.contains(term.toString()))
                {
                    return true;
                 }
            }


        }
        catch (IOException e)
        {
            log.error("[api err, /Tokenization]", e);
        }
        finally
        {
            try
            {
                if (token != null)
                {
                    token.end();
                    token.close();
                }
            }
            catch (IOException e)
            {
                log.error("[api err, /Tokenization]", e);
            }
        }
        return false;
    }*/

    private String dynamicString(int length){
        StringBuilder stringBuilder = new StringBuilder("*");
        for (int i = 1; i < length; i++){
            stringBuilder.append("*");
        }
        return  stringBuilder.toString();
    }

}
