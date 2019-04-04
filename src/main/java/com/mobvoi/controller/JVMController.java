package com.mobvoi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.windows.ThemeReader;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Ruibiao Zhang, <rbzhang@mobvoi.com>
 * @Date 2019/3/31
 */
@RestController
public class JVMController{


    public static final List<String[]> list = new ArrayList<>();
    public static String[] generate(){
        return new String[524288];
    }

    @GetMapping("/jvm-cpu")
    public void jvmCpu(){
        while (true){

        }
    }

    @GetMapping("/jvm-error")
    public void jvmError(){
        for (;;){
            try {
                Thread.sleep(4000L);
            }catch (Exception e){}
            list.add(generate());
        }
    }

    @GetMapping("/jvm-info")
    public Object jvmInfo() {
        List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
        StringBuffer sb = new StringBuffer();
        for (GarbageCollectorMXBean b :l){
            sb.append(b.getName() + "\n");
        }
        return sb.toString();
    }



    @GetMapping("jvm-lock")
    public void jvmLock(){
        DeadLock dl = new DeadLock();
        Thread0 thread0 = new Thread0(dl);
        Thread1 thread1 = new Thread1(dl);
        thread0.start();
        thread1.start();
    }
    public class Thread0 extends Thread{
        private DeadLock dl;
        public Thread0(DeadLock dl){
            this.dl = dl;
        }

        public void run(){
            try {
                dl.lock1();
            }catch (Exception e){}
        }
    }


    public class Thread1 extends Thread{
        private DeadLock dl;
        public Thread1(DeadLock dl){
            this.dl = dl;
        }

        public void run(){
            try {
                dl.lock2();
            }catch (Exception e){}
        }
    }


    class DeadLock {
        Object obj1 = new Object();
        Object obj2 = new Object();
        public void lock1(){
            synchronized (obj1){
                try{
                    Thread.sleep(2000l);
                }catch (Exception e){}
                System.out.println("--------obj1-------");
                synchronized (obj2){
                    System.out.println("-------get obj2------");
                }
            }
        }

        public void lock2(){
            synchronized (obj2){
                try{
                    Thread.sleep(2000l);
                }catch (Exception e){}
                System.out.println("--------obj2-------");
                synchronized (obj1){
                    System.out.println("-------get obj1------");
                }
            }
        }
    }


}