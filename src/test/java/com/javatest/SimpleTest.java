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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        String str = "http://localhost:88/api/business/test?APP_ID=10010&cc=dd&ee=fffQ27A9832";
        System.out.println(DigestUtils.md5DigestAsHex(str.getBytes()));

    }

    @Test
    public void test19() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2020-11-30 09:55:30");
        System.out.println(date.getTime());
    }

    @Test
    public void test20() {
        System.out.println(Instant.now().getEpochSecond());;
    }

    @Test
    public void test21() {
        String user = "10010";
        String timestamp = "1606727129";
        String password = "1234567";
        String enStr = user + timestamp + password;

        System.out.println(Instant.now().getEpochSecond());;
    }

    @Test
    public void test22() {
        String a = (String)null;
        System.out.println("ok");
    }

    @Test
    public void test23() {
        int count = 0;
        foo(count);
    }

    private void foo(int count) {
        try {
            if (count != 4) {
                int i = 1/0;
            }
            System.out.println("111");
        } catch (Exception e) {
            if (count < 3) {
                count ++;
                foo(count);
                System.out.println(count);
            } else {
                System.out.println("报错了");
                throw e;
            }
        }
    }


    @Test
    public void test24() {
        Integer a = 128;
        Integer b = new Integer(128);
        Integer c = 128;
        System.out.println(a == b);
        System.out.println(a==c);

        System.out.println("---------");

        Integer d = 127;
        Integer e = new Integer(127);
        Integer f = 127;
        System.out.println(d == e);
        System.out.println(d == f);

    }

    @Test
    public void test25(){
        String param = "深圳 福田无线网格 1 0.9 0.38 0.88 1.5 0.5 0.8 1 6.96 良\n" +
                "清远 东区建维优支撑团队 1 0.9 0.46 0.72 1.5 0.5 0.8 0.95 6.83 良\n" +
                "汕头 濠江划小团队 1 0.9 0.59 0.54 1.5 0.5 0.8 1 6.83 良\n" +
                "汕头 潮阳划小团队 1 0.9 0.59 0.54 1.5 0.5 0.8 1 6.83 良\n" +
                "汕头 潮南划小团队 1 0.9 0.59 0.54 1.5 0.5 0.8 1 6.83 良\n" +
                "珠海 斗门网络划小团队 1 0.9 0.22 0.74 1.5 0.5 0.79 1 6.65 良\n" +
                "湛江 南部划小团队 1 0.88 0.69 0.84 1.5 0.5 0.74 0.5 6.65 良\n" +
                "湛江 北部划小团队 1 0.9 0.75 0.89 1.5 0.5 0.8 0.3 6.64 良\n" +
                "佛山 狮山云网划小支撑室 0.2 0.9 1.01 0.76 1.5 0.5 0.76 1 6.6 良\n" +
                "佛山 新城云网划小支撑室 0.2 0.9 1.01 0.76 1.5 0.5 0.76 1 6.6 良\n" +
                "佛山 高明云网划小支撑室 0 0.9 1.15 0.82 1.5 0.5 0.74 1 6.61 良\n" +
                "河源 南部网格 0 0.9 0.99 0.9 1.5 0.5 0.8 1 6.59 良\n" +
                "深圳 龙岗网格 0 0.9 1.05 0.68 1.5 0.5 0.8 1 6.43 良\n" +
                "江门 开平-恩平网络支撑中心 0 0.9 0.84 0.89 1.5 0.5 0.8 1 6.43 良\n" +
                "佛山 南海云网划小支撑室 0 0.9 1.08 0.68 1.5 0.5 0.76 1 6.42 良\n" +
                "佛山 顺德云网划小支撑室 0 0.9 1.02 0.72 1.5 0.5 0.76 1 6.40 良\n" +
                "清远 南区建维优支撑团队 0 0.9 0.83 0.85 1.5 0.5 0.8 1 6.38 良\n" +
                "广州 荔湾运营支撑团队 0 0.9 1.07 0.59 1.5 0.5 0.8 1 6.36 良\n" +
                "江门 城区-鹤山网络支撑中心 0 0.9 0.96 0.74 1.5 0.5 0.8 0.95 6.35 良\n" +
                "东莞 城区网格 1 0.87 0.23 0.45 1.5 0.5 0.78 1 6.33 良\n" +
                "江门 新会-台山网络支撑中心 0 0.9 0.87 0.76 1.5 0.5 0.8 1 6.33 良\n" +
                "揭阳 城区划小支撑团队 1 0.9 0.8 0.60 1.5 0.5 0 1 6.3 良\n" +
                "韶关 网西区建维优（乐昌/坪石/乳源） 1 0.9 0 0.57 1.5 0.5 0.8 1 6.27 良\n" +
                "珠海 金湾网络划小团队 1 0.9 0 0.54 1.5 0.5 0.8 1 6.24 良";
        StringBuilder sb = new StringBuilder();
        // 获取列表
        String[] list = param.split("\n");
        for (int i = 0; i < list.length; i++) {
            String[] str = list[i].split(" ");
            sb.append("{\n");
            // 获取每行的内容
            for (int j = 0; j < str.length; j++) {
                if (j == 0) {
                    sb.append("name:'").append(str[j]).append("',\n");
                }
                if (j == 1) {
                    sb.append("pname:'").append(str[j]).append("',\n");
                }
                if (j == 2) {
                    sb.append("score1:").append(str[j]).append(",\n");
                }
                if (j == 3) {
                    sb.append("score2:").append(str[j]).append(",\n");
                }
                if (j == 4) {
                    sb.append("score3:").append(str[j]).append(",\n");
                }
                if (j == 5) {
                    sb.append("score4:").append(str[j]).append(",\n");
                }
                if (j == 6) {
                    sb.append("score5:").append(str[j]).append(",\n");
                }
                if (j == 7) {
                    sb.append("score6:").append(str[j]).append(",\n");
                }
                if (j == 8) {
                    sb.append("score7:").append(str[j]).append(",\n");
                }
                if (j == 9) {
                    sb.append("score8:").append(str[j]).append(",\n");
                }
                if (j == 10) {
                    sb.append("score9:{\nvalue:").append(str[j]).append(",\nclassName: 'black'\n},\n");
                }
                if (j == 11) {
                    sb.append("score10: {\nvalue: '").append(str[j]).append("',\nclassName: 'black'\n}\n");
                }

            }
            sb.append("}");
            if(i<list.length-1) {
                sb.append(",\n");
            }
        }
        System.out.println(sb.toString());
    }
}