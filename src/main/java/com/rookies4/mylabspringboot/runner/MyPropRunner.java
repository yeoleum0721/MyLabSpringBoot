package com.rookies4.mylabspringboot.runner;


import com.rookies4.mylabspringboot.config.env.MyEnvironment;
import com.rookies4.mylabspringboot.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String name;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private MyPropProperties myprop;

    @Autowired
    private MyEnvironment myEnvironment;
    //Logger 객체생성
    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println();
        System.out.println("1. application.properties 파일에 있는 환경변수를 Load");
        System.out.println("myprop.username = " + name);
        System.out.println("myprop.port = "+ port);
        System.out.println();

        System.out.println("2. 환경변수를 저장하고 조회하는 Properties 클래스 작성");
        System.out.println("myprop.username = "+ myprop.getUsername());
        System.out.println("myprop.port = "+ myprop.getPort());
        System.out.println();

        System.out.println("3.ProdConfig 클래스와 DevConfig 클래스 작성");
        System.out.println("현재 활성화된 MyEnvironment() : "+ myEnvironment);
        System.out.println();

        //---------------------log출력------------------

        logger.debug("1. application.properties 파일에 있는 환경변수를 Load");
        logger.debug("myprop.username ={} ", name);
        logger.debug("myprop.port ={} ", port);


        logger.info("2. 환경변수를 저장하고 조회하는 Properties 클래스 작성");
        logger.info("myprop.uername = {}", myprop.getUsername());
        logger.info("myprop.port ={} ", myprop.getPort());


        logger.info("3.ProdConfig 클래스와 DevConfig 클래스 작성");
        logger.info("현재 활성화된 MyEnvironment() : {}", myEnvironment);


    }
}
