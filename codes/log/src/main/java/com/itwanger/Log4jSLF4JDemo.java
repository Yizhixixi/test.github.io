package com.itwanger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 微信搜「沉默王二」，回复关键字 PDF
 */
public class Log4jSLF4JDemo {
    private static final Logger logger = LoggerFactory.getLogger(Log4jSLF4JDemo.class);

    public static void main(String[] args) {
        logger.debug("{}是个非常不要脸的程序员","沉默王二");

        logger.debug("沉默王二，{}岁", 18);

        logger.debug("{}是个非常不要脸的程序员", getName());
    }

    public static String getName() {
        return "沉默王二";
    }
}
