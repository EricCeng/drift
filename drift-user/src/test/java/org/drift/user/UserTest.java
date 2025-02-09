package org.drift.user;

import org.drift.common.util.BatchMapperUtils;
import org.drift.user.bean.User;
import org.drift.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author jiakui_zeng
 * @date 2025/2/9 16:44
 */
@SpringBootTest
public class UserTest {

    @Test
    void test() {
        // 插入 100 条记录
        Random random = new Random();
        List<String> genders = List.of("male", "female");
        List<String> regions = generateRandomRegions(random);
        List<String> occupations = generateRandomOccupations(random);
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new User()
                    .setUsername("用户" + (1000 + random.nextInt(9000)))
                    .setPhoneNumber(generateRandomChinesePhoneNumber(random))
                    .setPassword("123456")
                    .setBio("这是我的简历" +(10 + random.nextInt(90)))
                    .setBirthday(generateRandomBirthday(random))
                    .setGender(genders.get(random.nextInt(genders.size())))
                    .setRegion(regions.get(random.nextInt(regions.size())))
                    .setOccupation(occupations.get(random.nextInt(occupations.size()))));
        }
        BatchMapperUtils.insertBatch(list, UserMapper.class);
    }

    private String generateRandomChinesePhoneNumber(Random random) {
        // 中国的手机号码以1开头，第二位通常是3、4、5、6、7、8或9
        String[] prefixes = {"13", "14", "15", "16", "17", "18", "19"};
        String prefix = prefixes[random.nextInt(prefixes.length)];
        StringBuilder phoneNumber = new StringBuilder(prefix);

        // 生成剩余的9位数字
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(random.nextInt(10));
        }

        return phoneNumber.toString();
    }

    private String generateRandomBirthday(Random random) {
        // 生成18到60岁之间的随机生日
        Calendar calendar = Calendar.getInstance();
        int year = 1963 + random.nextInt(42); // 1963 到 2004 之间
        int month = random.nextInt(12) + 1; // 1 到 12 之间
        int day = random.nextInt(28) + 1; // 1 到 28 之间，避免闰年和月份天数问题

        calendar.set(year, month - 1, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    private List<String> generateRandomRegions(Random random) {
        // 示例城市列表，可以根据需要扩展
        List<String> regions = new ArrayList<>();
        regions.add("中国北京");
        regions.add("中国上海");
        regions.add("中国广州");
        regions.add("中国深圳");
        regions.add("美国纽约");
        regions.add("美国洛杉矶");
        regions.add("美国芝加哥");
        regions.add("日本东京");
        regions.add("日本大阪");
        regions.add("英国伦敦");
        regions.add("英国曼彻斯特");
        regions.add("法国巴黎");
        regions.add("法国里昂");
        regions.add("德国柏林");
        regions.add("德国慕尼黑");
        regions.add("印度孟买");
        regions.add("印度新德里");
        regions.add("巴西圣保罗");
        regions.add("巴西里约热内卢");
        regions.add("俄罗斯莫斯科");
        regions.add("俄罗斯圣彼得堡");
        regions.add("澳大利亚悉尼");
        regions.add("澳大利亚墨尔本");
        regions.add("加拿大温哥华");
        regions.add("加拿大多伦多");
        regions.add("韩国首尔");
        regions.add("韩国釜山");

        return regions;
    }

    private List<String> generateRandomOccupations(Random random) {
        // 示例职业列表，可以根据需要扩展
        List<String> occupations = new ArrayList<>();
        occupations.add("工程师");
        occupations.add("医生");
        occupations.add("教师");
        occupations.add("律师");
        occupations.add("程序员");
        occupations.add("设计师");
        occupations.add("销售");
        occupations.add("经理");
        occupations.add("作家");
        occupations.add("艺术家");
        occupations.add("会计师");
        occupations.add("建筑师");
        occupations.add("厨师");
        occupations.add("护士");
        occupations.add("记者");
        occupations.add("警察");
        occupations.add("消防员");
        occupations.add("飞行员");
        occupations.add("教师");
        occupations.add("科学家");

        return occupations;
    }
}
