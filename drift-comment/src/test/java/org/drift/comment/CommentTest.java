package org.drift.comment;

import lombok.extern.slf4j.Slf4j;
import org.drift.comment.bean.Comment;
import org.drift.comment.mapper.CommentMapper;
import org.drift.common.util.BatchMapperUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Jiakui_Zeng
 * @date 2025/2/20 22:05
 */
@SpringBootTest
@Slf4j
public class CommentTest {
    @Autowired
    private CommentMapper commentMapper;

    private static final Random random = new Random();

    @Test
    void test() {
        List<String> postIds = generatePostIds();
        List<String> userIds = generateUserIds();

        // Step 2: 插入评论数据
        for (String postId : postIds) {
            // 每个 post 的 parent_comment_id 为 null 的评论数在 1 到 100 之间
            int parentCommentCount = random.nextInt(100) + 1;

            List<Comment> firstLevelComments = new ArrayList<>();
            for (int i = 0; i < parentCommentCount; i++) {
                String userId = userIds.get(random.nextInt(userIds.size()));
                log.info("first level comment >>> post id: {}, user id: {}", postId, userId);
                Comment comment = generateComment(postId, userId, null, null);
                firstLevelComments.add(comment);
            }
            log.info("first level comment size: {}", firstLevelComments.size());
            BatchMapperUtils.insertBatch(firstLevelComments, CommentMapper.class);

            // reply_to_user_id 为 null 的评论数在 1 到 50 之间
            int secondLevelCommentCount = random.nextInt(50) + 1;
            // reply_to_user_id 不为 null 的评论数在 1 到 50 之间
            int thirdLevelCommentCount = random.nextInt(50) + 1;

            // 生成 reply_to_user_id 为 null 的评论
            List<Comment> secondLevelComments = new ArrayList<>();
            for (int j = 0; j < secondLevelCommentCount; j++) {
                // parent_comment_id 不为 null
                Comment firstLevelComment = firstLevelComments.get(random.nextInt(firstLevelComments.size()));
                Long parentCommentId = firstLevelComment.getId();
                String userId = userIds.get(random.nextInt(userIds.size()));
                log.info("second level comment >>> post id: {}, user id: {}, parent comment id: {}", postId, userId, parentCommentId);
                Comment comment = generateComment(postId, userId, parentCommentId, null);
                secondLevelComments.add(comment);
            }
            log.info("second level comment size: {}", secondLevelComments.size());
            BatchMapperUtils.insertBatch(secondLevelComments, CommentMapper.class);

            // 生成 reply_to_user_id 不为 null 的评论
            List<Comment> thirdLevelComments = new ArrayList<>();
            for (int j = 0; j < thirdLevelCommentCount; j++) {
                Comment secondLevelComment = secondLevelComments.get(random.nextInt(secondLevelComments.size()));
                // parent_comment_id 不为 null
                Long parentCommentId = secondLevelComment.getId();
                String userId = userIds.get(random.nextInt(userIds.size()));
                // reply_to_user_id 应该是 parent_comment_id 对应的 user_id
                Long replyToUserId = secondLevelComment.getUserId();
                log.info("third level comment >>> post id: {}, user id: {}, parent comment id: {}, reply to user id: {}",
                        postId, userId, parentCommentId, replyToUserId);
                Comment comment = generateComment(postId, userId, parentCommentId, replyToUserId);
                thirdLevelComments.add(comment);
            }
            log.info("third level comment size: {}", thirdLevelComments.size());
            BatchMapperUtils.insertBatch(thirdLevelComments, CommentMapper.class);
        }
    }

    private Comment generateComment(String postId, String userId, Long parentCommentId, Long replyToUserId) {
        String content = generateRandomContent();
        String randomOrder = generateRandomString();
        Boolean firstComment = parentCommentId == null;
        Boolean topped = random.nextBoolean();
        Instant createTime = generateRandomCreateTime();

        Comment comment = new Comment();
        comment.setPostId(Long.valueOf(postId));
        comment.setUserId(Long.valueOf(userId));
        comment.setParentCommentId(parentCommentId);
        comment.setReplyToUserId(replyToUserId);
        comment.setContent(content);
        comment.setRandomOrder(randomOrder);
        comment.setFirstComment(firstComment);
        comment.setTopped(topped);
        comment.setCreateTime(createTime);

        return comment;
    }

    private String generateRandomContent() {
        // 随机生成中文内容
        String[] words = {"你好", "很好", "不错", "加油", "谢谢", "真棒", "有趣", "期待"};
        int length = random.nextInt(49) + 2; // 2到50个字符
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < length; i++) {
            content.append(words[random.nextInt(words.length)]);
        }
        return content.toString();
    }

    private String generateRandomString() {
        // 生成随机字符串
        return Long.toHexString(random.nextLong());
    }

    private Instant generateRandomCreateTime() {
        // 随机生成时间，范围从 2024 年 12 月 1 日到现在
        Instant start = LocalDate.of(2024, 12, 15).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = Instant.now();
        return start.plusSeconds(random.nextLong(end.getEpochSecond() - start.getEpochSecond()));
    }

    private List<String> generatePostIds() {
        String s = "1889671407708889099," +
                "1889671407708889105," +
                "1889671407700500483," +
                "1889671407704694797," +
                "1889671407704694850," +
                "1889671407708889097," +
                "1889671407700500487," +
                "1889671407708889104," +
                "1889671407704694825," +
                "1889671407704694832," +
                "1889671407704694807," +
                "1889671407700500485," +
                "1889671407704694823," +
                "1889671407700500491," +
                "1889671407708889093," +
                "1889671407704694800," +
                "1889671407708889101," +
                "1889671407708889100," +
                "1889671407704694835," +
                "1889671407708889094," +
                "1889671407708889107," +
                "1889671407704694834," +
                "1889671407704694851," +
                "1889671407704694828," +
                "1889671407704694818," +
                "1889671407704694840," +
                "1889671407700500490," +
                "1889671407704694794," +
                "1889671407708889102," +
                "1889671407704694809," +
                "1889671407704694841," +
                "1889671407704694799," +
                "1889671407704694798," +
                "1889671407704694795," +
                "1889671407704694812," +
                "1889671407704694847," +
                "1889671407700500486," +
                "1889671407704694787," +
                "1889671407704694845," +
                "1889671407704694815," +
                "1889671407704694827," +
                "1889671407704694838," +
                "1889671407708889091," +
                "1889671407708889095," +
                "1889671407704694801," +
                "1889671407704694811," +
                "1889671407704694837," +
                "1889671407704694791," +
                "1889671407704694803," +
                "1889671407704694822," +
                "1889671407704694839," +
                "1889671407708889108," +
                "1889671407704694796," +
                "1889671407704694820," +
                "1889671407708889098," +
                "1889671407704694802," +
                "1889671407704694816," +
                "1889671407704694789," +
                "1889671407704694846," +
                "1889671407700500488," +
                "1889671407704694830," +
                "1889671407700500484," +
                "1889671407708889096," +
                "1889671407704694814," +
                "1889671407704694805," +
                "1889671407704694792," +
                "1889671407704694849," +
                "1889671407704694844," +
                "1889671407708889092," +
                "1889671407704694808," +
                "1889671407704694829," +
                "1889671407704694842," +
                "1889671407704694813," +
                "1889671407708889106," +
                "1889671407704694806," +
                "1889671407704694817," +
                "1889671407704694821," +
                "1889671407700500489," +
                "1889671407704694810," +
                "1889671407704694819," +
                "1889671407700500492," +
                "1889671407704694836," +
                "1889671407704694785," +
                "1889671407704694788," +
                "1889671407704694824," +
                "1889671407704694804," +
                "1889671407704694793," +
                "1889671407708889090," +
                "1889671407704694826," +
                "1889671407700500482," +
                "1889671407704694786," +
                "1889671407704694848," +
                "1889671407704694833," +
                "1889671407704694843," +
                "1889671407704694790," +
                "1889671407708889103," +
                "1889671407704694831";
        return List.of(s.split(","));
    }

    private List<String> generateUserIds() {
        String s = "1889670476523081729," +
                "1889670476565024770," +
                "1889670476565024771," +
                "1889670476565024772," +
                "1889670476565024773," +
                "1889670476569219074," +
                "1889670476569219075," +
                "1889670476569219076," +
                "1889670476569219077," +
                "1889670476569219078," +
                "1889670476573413377," +
                "1889670476573413378," +
                "1889670476573413379," +
                "1889670476577607682," +
                "1889670476577607683," +
                "1889670476577607684," +
                "1889670476577607685," +
                "1889670476577607686," +
                "1889670476577607687," +
                "1889670476581801986," +
                "1889670476581801987," +
                "1889670476581801988," +
                "1889670476581801989," +
                "1889670476581801990," +
                "1889670476581801991," +
                "1889670476581801992," +
                "1889670476585996290," +
                "1889670476585996291," +
                "1889670476585996292," +
                "1889670476585996293," +
                "1889670476585996294," +
                "1889670476585996295," +
                "1889670476585996296," +
                "1889670476590190593," +
                "1889670476590190594," +
                "1889670476590190595," +
                "1889670476590190596," +
                "1889670476590190597," +
                "1889670476590190598," +
                "1889670476590190599," +
                "1889670476590190600," +
                "1889670476594384898," +
                "1889670476594384899," +
                "1889670476594384900," +
                "1889670476598579201," +
                "1889670476598579202," +
                "1889670476598579203," +
                "1889670476598579204," +
                "1889670476598579205," +
                "1889670476598579206," +
                "1889670476598579207," +
                "1889670476598579208," +
                "1889670476598579209," +
                "1889670476602773505," +
                "1889670476602773506," +
                "1889670476602773507," +
                "1889670476602773508," +
                "1889670476602773509," +
                "1889670476602773510," +
                "1889670476602773511," +
                "1889670476606967810," +
                "1889670476606967811," +
                "1889670476606967812," +
                "1889670476606967813," +
                "1889670476606967814," +
                "1889670476606967815," +
                "1889670476606967816," +
                "1889670476606967817," +
                "1889670476606967818," +
                "1889670476606967819," +
                "1889670476606967820," +
                "1889670476611162114," +
                "1889670476611162115," +
                "1889670476611162116," +
                "1889670476611162117," +
                "1889670476611162118," +
                "1889670476611162119," +
                "1889670476611162120," +
                "1889670476611162121," +
                "1889670476611162122," +
                "1889670476611162123," +
                "1889670476611162124," +
                "1889670476619550721," +
                "1889670476619550722," +
                "1889670476619550723," +
                "1889670476619550724," +
                "1889670476619550725," +
                "1889670476619550726," +
                "1889670476623745025," +
                "1889670476623745026," +
                "1889670476623745027," +
                "1889670476623745028," +
                "1889670476623745029," +
                "1889670476623745030," +
                "1889670476623745031," +
                "1889670476623745032," +
                "1889670476623745033," +
                "1889670476623745034," +
                "1889670476623745035," +
                "1889670476623745036";
        return List.of(s.split(","));
    }
}
