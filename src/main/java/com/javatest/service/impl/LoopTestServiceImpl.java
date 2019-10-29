package com.javatest.service.impl;

import com.javatest.dao.StudentScoreMapper;
import com.javatest.po.StudentScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Transactional(rollbackFor = Exception.class)
public class LoopTestServiceImpl {
    @Autowired
    private StudentScoreMapper studentScoreMapper;

    /**
     * 结论：没有开启事务，每次循环都会插入一条数据
     * try catch在for循环里面，如果其中一次报错，只会将报错的那次跳过，然后继续下一个循环，得出的结果就只是跳过了报错那次，其余都正常插入到数据库中
     * 原因：没有开启事务，且报错当次已经catch了错误，没有触发回滚
     */
    public void loopTest1() {
        int count;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setId(10L + i);
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (3 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结论：org.springframework.transaction.NoTransactionException: No transaction aspect-managed TransactionStatus in scope，会插入报错前的数据
     * 原因：TransactionAspectSupport需要开启事务
     */
    public void loopTest2() {
        int count;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setId(10L + i);
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            }
        }
    }

    /**
     * 结论：事务已经开启，全部回滚，但是即使报错了，还是会继续循环直至循环结束，不会因为报错而结束循环
     * 注意事项，使用TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();时需要在本类加上@Transactional(rollbackFor = Exception.class)
     */
    @Transactional
    public void loopTest3() {
        int count;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setId(10L + i);
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
    }

    /**
     * 结论：事务已经开启，直到循环结束时才一并提交。但效果同loopTest1没有开启事务一样，只是跳过报错的那次，其余的都正常插入
     * 原因：try catch在循环体中，将报错的那次的错误处理了
     */
    @Transactional
    public void loopTest4() {
        int count;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setId(10L + i);
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结论：能够正常开启事务，且直到循环结束后才一起插入
     */
    @Transactional
    public void loopTest5() {
        int count;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
//                count = 1/(2-i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 结论：只有整个方法结束后才会插入数据库，期间对事务中的数据是可以修改的
     */
    @Transactional
    public void loopTest6() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
//                count = 1/(2-i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10128L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10128L));
    }

    /**
     * 结论：同loopTest6,只是跳过了报错的那次
     */
    @Transactional
    public void loopTest7() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10132L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10132L));
    }

    /**
     * 结论：事务开启，报错后便不再循环，但是报错前插入的数据会被提交，从而入库。没有全部回滚
     */
    @Transactional
    public void loopTest8() {
        int count;
        try {
            for (int i = 0; i < 5; i++) {
                StudentScore studentScore = new StudentScore();
                studentScore.setId(10L + i);
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结论：同loopTest8
     */
    @Transactional
    public void loopTest9() {
        int count = 0;
        try {
            for (int i = 0; i < 5; i++) {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10096L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10096L));
    }

    /**
     * 结论：事务开启，当将错误扔到controller，全部回滚
     */
    @Transactional
    public void loopTest10() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.setName("小小" + i);
            studentScore.setScore(80 + i);
            count = 1 / (2 - i);
            studentScoreMapper.insertSelective(studentScore);
            count++;
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10088L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10088L));
    }

    /**
     * 结论同loopTest3，全部回滚，但仍旧会继续循环直至循环结束，循环后面的代码仍会执行
     */
    @Transactional
    public void loopTest11() {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            try {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
            } catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            count++;
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10088L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10088L));
    }

    /**
     * 全部回滚，报错跳出循环，报错后仍会继续循环体后面的代码
     */
    @Transactional
    public void loopTest12() {
        int count = 0;
        try {
            for (int i = 0; i < 5; i++) {
                StudentScore studentScore = new StudentScore();
                studentScore.setName("小小" + i);
                studentScore.setScore(80 + i);
                count = 1 / (2 - i);
                studentScoreMapper.insertSelective(studentScore);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        System.out.println("count:" + count);
        StudentScore s = new StudentScore();
        s.setId(10088L);
        s.setScore(99);
        studentScoreMapper.updateByPrimaryKeySelective(s);
        System.out.println(studentScoreMapper.selectByPrimaryKey(10088L));
    }
}
