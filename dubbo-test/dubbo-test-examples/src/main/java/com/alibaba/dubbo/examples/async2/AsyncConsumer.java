/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.examples.async2;

import com.alibaba.dubbo.examples.async2.api.Echo;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


/**
 * CallbackConsumer
 * 
 * @author william.liangf
 */
public class AsyncConsumer {

    public static void main(String[] args) throws Exception {
        String config = AsyncConsumer.class.getPackage().getName().replace('.', '/') + "/async-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();


        final Echo echo = (Echo)context.getBean("echoService");
        //warn
        System.out.println(echo.syncEcho("hello world"));
        System.out.println(echo.syncEcho("hello world"));
        System.out.println(echo.syncEcho("hello world"));

        Future<String> future = echo.asyncEcho("async hello world");
        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));

         future = echo.asyncEcho("async hello world");
        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
        future = echo.asyncEcho("async hello world");
        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));

        long start = System.currentTimeMillis();
        System.out.println(echo.syncEcho("hello world"));
        long mid = System.currentTimeMillis();
//        System.out.println(echo.syncEcho("hello world"));
//        System.out.println(echo.syncEcho("hello world"));

        future = echo.asyncEcho("async hello world");
        System.out.println(Await.result(future, Duration.create(10, TimeUnit.DAYS)));
        long end = System.currentTimeMillis();

        System.out.println("sync:" + (mid - start));
        System.out.println("async:" + (end - mid));
//        Future<String> f = RpcContext.getContext().asyncCall(new Callable<String>() {
//            public String call() throws Exception {
//                return asyncService.sayHello("async call request");
//            }
//        });
//
//        System.out.println("async call ret :" + f.get());
//
//        RpcContext.getContext().asyncCall(new Runnable() {
//            public void run() {
//                asyncService.sayHello("oneway call request1");
//                asyncService.sayHello("oneway call request2");
//            }
//        });
//

        Thread.sleep(3000);
        System.exit(0);

    }

}
