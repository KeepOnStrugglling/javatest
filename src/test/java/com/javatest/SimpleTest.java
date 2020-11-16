package com.javatest;

import com.javatest.domain.Schedule;
import com.javatest.mapper.StudentScoreMapper;
import com.javatest.domain.StudentScore;
import com.javatest.service.ScheduleService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JavaTestApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleTest {

    @Autowired
    private StudentScoreMapper studentScoreMapper;
    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void test1() {
        StudentScore studentScore = new StudentScore();
        studentScore.setName("狗蛋");
        studentScore.setScore(88);

        int cn = studentScoreMapper.insert(studentScore);
        System.out.println(cn);
    }

    @Test
    public void test2() {
        StudentScore studentScore = new StudentScore();
        studentScore.setName("狗蛋2");

        int cn = studentScoreMapper.insertSelective(studentScore);
        System.out.println(cn);
    }

    @Test
    public void test3() {
        StudentScore studentScore = new StudentScore();
        studentScore.setId(10015L);
        studentScore.setName("狗蛋10086");
        studentScore.setScore(99);

        int cn = studentScoreMapper.updateByPrimaryKeySelective(studentScore);
        System.out.println(cn);
    }

    @Test
    public void test4() {
        StudentScore studentScore = new StudentScore();
        studentScore.setId(10016L);
        studentScore.setName("狗蛋10010");
        studentScore.setScore(77);

        int cn = studentScoreMapper.updateByPrimaryKey(studentScore);
        System.out.println(cn);
    }

    @Test
    public void test5() {
        StudentScore studentScore = studentScoreMapper.selectByPrimaryKey(10015L);
        System.out.println(studentScore.getId() + "=" + studentScore.getName() + "=" + studentScore.getScore());
    }

    @Test
    public void test6() {
        StudentScore studentScore = new StudentScore();
        List<StudentScore> studentScores = studentScoreMapper.queryStudentScore(studentScore);
        for (int i = 0; i < studentScores.size(); i++) {
            System.out.println(studentScores.get(i).getId() + "=" + studentScores.get(i).getName() + "=" + studentScores.get(i).getScore());
        }
    }

    @Test
    public void test7() {
        String[] logoutOrderArr = StringUtils.split(null, ",");
        System.out.println(logoutOrderArr);
    }

    @Test
    public void test8() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("定额为：");
        double e = scanner.nextDouble();
        System.out.println("利率：");
        double r = scanner.nextDouble();
        Double sum = (Math.pow(r, 1) + Math.pow(r, 2) + Math.pow(r, 3) + Math.pow(r, 4) + Math.pow(r, 5) + Math.pow(r, 6) + Math.pow(r, 7) +
                Math.pow(r, 8) + Math.pow(r, 9) + Math.pow(r, 10) + Math.pow(r, 11) + Math.pow(r, 12)) * e;
        BigDecimal result = new BigDecimal(sum);
        System.out.println("总数为：" + result.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Test
    public void test9() {
        StudentScore s = new StudentScore();
        System.out.println(s.toString());
        long id = 1L;
        String name = "JoJo";
        int score = 100;
        setStudentScore(s, id, name, score);
        System.out.println(s.toString());
    }

    private void setStudentScore(StudentScore s, long id, String name, int score) {
        s.setId(id);
        s.setName(name);
        s.setScore(score);
    }

    @Test
    public void test10() {
        String[] a = {"ssss", "aaaa", "bbbb", "cccc"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]).append(",");
            if (i == a.length - 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test11() {
        Map<String, Object> map = new HashMap<>();

        StringBuilder huawei = new StringBuilder();
//        map.put("vendor","华为");
//        map.put("netName","net111222");
//        if ("华为".equals(map.get("vendor"))) {
//            huawei.append(map.get("netName")).append(",");
//        }
        String[] arr = {""};
        for (String s : arr) {
            huawei.append(s).append(",");
        }
        if (huawei.length() > 0) {
            huawei.deleteCharAt(huawei.length() - 1);
        }
        System.out.println(huawei.toString());

    }

    @Test
    public void test12() {
        long filesize = 123456789L;
        double number = (double) filesize;
        String result = null;
        DecimalFormat decimalFormat = new DecimalFormat("#.000");
        if (number / 1073741824 > 1) {   // 1073741824=1024*1024*1024
            result = decimalFormat.format(number / 1073741824) + " gb";
        } else if (number / 1048576 > 1) {  //1048576=1024*1024
            result = decimalFormat.format(number / 1048576) + " mb";
        } else if (number / 1024 > 1) {
            result = decimalFormat.format(number / 1024) + " kb";
        } else {
            result = number + " b";
        }
        System.out.println(result);
    }

    @Test
    public void test13() {
        Schedule schedule = new Schedule();
        schedule.setId(2);
        schedule.setScheduleName("测试修改2");
        scheduleService.updateByPrimaryKey(schedule);
    }

    @Test
    public void test14() {
        BigDecimal a = new BigDecimal("-520000");
        System.out.println(a.abs().compareTo(new BigDecimal("10000")) > 0 ? (a.signum() < 0 ? "-" : "") + "10000" : a.toString());
    }

    @Test
    public void test15() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add(null);
        list.add("3");
        for (String s : list) {
            System.out.println(s + ",");
        }
        list.remove(null);
        System.out.println("++++++++++++++++++++++++++++");
        for (String s : list) {
            System.out.println(s + ",");
        }
    }

    @Test
    public void test16() {
        Map<String, Integer> auditMap = new HashMap();
        Integer auditNum = auditMap.get("hello");
        if (auditNum != null)
            System.out.println(auditNum);
    }

    @Test
    public void test17() {
        boolean[] array = new boolean[70];
        List<String> list = new ArrayList<>();
        int count = 0;

        Random random = new Random();
        while (count < 70) {
            int i = random.nextInt(70);
            if (!array[i]) {
                list.add(i + "");
                array[i] = true;
                count++;
            }
        }
        count = 0;
        for (String s : list) {
            if (count < 9) {
                System.out.print(s + ",");
                count++;
            } else {
                System.out.println("");
                System.out.print(s + ",");
                count = 1;
            }
        }

    }

    @Test
    public void test18() {
        String str = "APP_IDabcTIMESTAMP2016-04-12 15:06:06 100TRANS_ID20160412150606100335423B2732427";
        System.out.println(DigestUtils.md5DigestAsHex(str.getBytes()));

    }
}