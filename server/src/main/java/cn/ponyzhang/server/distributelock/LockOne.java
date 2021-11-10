package cn.ponyzhang.server.distributelock;

import cn.ponyzhang.server.controller.BookController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockOne {
    private static final Logger logger = LoggerFactory.getLogger(LockOne.class);

    public static void main(String[] args) {
        Thread thread1 = new Thread(new LockThread(100));
        Thread thread2 = new Thread(new LockThread(-100));
        thread1.start();
        thread2.start();
    }

    static class LockThread implements Runnable{
        private int count;

        public LockThread(int count){
            this.count = count;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                SysConstant.amount += count;
                logger.info("此时账户余额：{}",SysConstant.amount);
            }
        }
    }
}

