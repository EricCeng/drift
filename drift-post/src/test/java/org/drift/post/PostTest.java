package org.drift.post;

import org.drift.common.util.BatchMapperUtils;
import org.drift.post.bean.Post;
import org.drift.post.mapper.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 17:32
 */
@SpringBootTest
public class PostTest {
    @Test
    void test() {
        String s = "1888521120593707009," +
                "1888521120681787393," +
                "1888521120681787394," +
                "1888521120681787395," +
                "1888521120690176002," +
                "1888521120694370305," +
                "1888521120694370306," +
                "1888521120698564609," +
                "1888521120702758913," +
                "1888521120702758914," +
                "1888521120711147522," +
                "1888521120715341825," +
                "1888521120715341826," +
                "1888521120719536129," +
                "1888521120719536130," +
                "1888521120723730434," +
                "1888521120723730435," +
                "1888521120723730436," +
                "1888521120723730437," +
                "1888521120732119041," +
                "1888521120732119042," +
                "1888521120740507649," +
                "1888521120740507650," +
                "1888521120740507651," +
                "1888521120744701953," +
                "1888521120748896257," +
                "1888521120748896258," +
                "1888521120748896259," +
                "1888521120748896260," +
                "1888521120757284866," +
                "1888521120757284867," +
                "1888521120757284868," +
                "1888521120761479170," +
                "1888521120761479171," +
                "1888521120765673473," +
                "1888521120765673474," +
                "1888521120769867778," +
                "1888521120769867779," +
                "1888521120769867780," +
                "1888521120769867781," +
                "1888521120769867782," +
                "1888521120778256385," +
                "1888521120795033602," +
                "1888521120795033603," +
                "1888521120803422210," +
                "1888521120803422211," +
                "1888521120803422212," +
                "1888521120803422213," +
                "1888521120803422214," +
                "1888521120811810817," +
                "1888521120816005122," +
                "1888521120816005123," +
                "1888521120816005124," +
                "1888521120816005125," +
                "1888521120816005126," +
                "1888521120824393730," +
                "1888521120824393731," +
                "1888521120824393732," +
                "1888521120824393733," +
                "1888521120832782337," +
                "1888521120832782338," +
                "1888521120832782339," +
                "1888521120832782340," +
                "1888521120845365250," +
                "1888521120849559554," +
                "1888521120849559555," +
                "1888521120849559556," +
                "1888521120853753858," +
                "1888521120857948162," +
                "1888521120857948163," +
                "1888521120857948164," +
                "1888521120866336770," +
                "1888521120866336771," +
                "1888521120866336772," +
                "1888521120874725378," +
                "1888521120874725379," +
                "1888521120883113986," +
                "1888521120883113987," +
                "1888521120883113988," +
                "1888521120883113989," +
                "1888521120883113990," +
                "1888521120891502594," +
                "1888521120891502595," +
                "1888521120895696898," +
                "1888521120895696899," +
                "1888521120895696900," +
                "1888521120895696901," +
                "1888521120899891202," +
                "1888521120899891203," +
                "1888521120904085505," +
                "1888521120904085506," +
                "1888521120904085507," +
                "1888521120908279809," +
                "1888521120908279810," +
                "1888521120912474113," +
                "1888521120912474114," +
                "1888521120912474115," +
                "1888521120920862721," +
                "1888521120920862722," +
                "1888521120925057025";
        String[] userIdArray = s.split(",");
        Random random = new Random();
        for (String userId : userIdArray) {
            int randomNumber = random.nextInt(100);
            List<Post> list = new ArrayList<>();
            for (int i = 0; i < randomNumber; i++) {
                list.add(new Post()
                        .setUserId(Long.valueOf(userId))
                        .setTitle("这是标题" + (1000 + random.nextInt(9000)))
                        .setContent("这是动态内容" + (10000 + random.nextInt(90000)))
                        .setRandomOrder(UUID.randomUUID().toString()));
            }
            BatchMapperUtils.insertBatch(list, PostMapper.class);
        }
    }
}
