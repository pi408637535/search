/**
 * Created by piguanghua on 7/27/17.
 */


import com.stockemotion.search.SearchApplication;
import com.stockemotion.search.dto.innner.ForbiddenNicknameDTO;
import com.stockemotion.search.es.dao.ForbiddenNicknameRepository;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SearchApplication.class)
@SpringBootTest
public class SpringTest {

    @Resource
    ForbiddenNicknameRepository forbiddenNicknameRepository;

    @Test
    public void test() {
        /*UserNicknameDTO userNicknameDTO = new UserNicknameDTO();
        userNicknameDTO.setId(1L);
        userNicknameDTO.setNickName("天使");
        nicknameRepository.save(userNicknameDTO);*/

        QueryBuilder queryBuilder = new SimpleQueryStringBuilder("小沃机器人");
        Iterable<ForbiddenNicknameDTO> forbiddenNicknameDTOS = forbiddenNicknameRepository.search(queryBuilder);
        System.out.println(forbiddenNicknameDTOS);
    }
}
