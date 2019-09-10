package com.wang.rocketmq.demo;

import io.vavr.*;
import io.vavr.collection.List;
import org.junit.Test;

import java.math.BigInteger;

import static io.vavr.API.*;


public class TupleDemo {

    @Test
    public void test6(){
        int value = 1;
        Match(value).of(
          Case($(v -> v>0),o -> run(() -> System.out.println(">0"))),
          Case($(0),o -> run(() -> System.out.println("0"))),
          Case($(v -> v < 0),o -> run(() -> System.out.println("<0")))
        );
    }

    @Test
    public void test5(){
        String input = "g";
        String result = Match(input).of(
                Case($("g"), "good"),
                Case($("b"), "bad"),
                Case($(""), "unknown")
        );
        System.out.println("result = " + result);
    }

    @Test
    public void test4(){
        Function2<BigInteger,Integer,BigInteger> pow = BigInteger::pow;
        Function2<BigInteger, Integer, BigInteger> memoized = pow.memoized();
        long start = System.currentTimeMillis();
        memoized.apply(BigInteger.valueOf(1024),1024);
        long end1 = System.currentTimeMillis();
        memoized.apply(BigInteger.valueOf(1024),1024);
        long end2 = System.currentTimeMillis();
        System.out.printf("%d ms -> %d ms",end1 - start, end2 - end1);
    }

    @Test
    public void test3(){
        Function3<Integer,Integer,Integer,Integer> function3 = (v1,v2,v3) -> (v1 + v2) * v3;
        Integer result = function3.curried().apply(1).apply(2).curried().apply(3);
        System.out.println("result = " + result);
    }
    
    @Test
    public void test2(){
        Function3<Integer,Integer,Integer,Integer> function3 = (v1,v2,v3) -> (v1 + v2 ) * v3;
        Function3<Integer, Integer, Integer, Integer> composed = function3.andThen(v -> v * 100);
        Integer apply = composed.apply(1, 2, 3);
        System.out.println("apply = " + apply);

        Function1<String,String> function1 = String :: toUpperCase;
        Function1<Object, String> toUpperCase = function1.compose(Object::toString);
        String str = toUpperCase.apply(List.of("a", "b"));
        System.out.println("str = " + str);
    }

    @Test
    public void test1(){
        Tuple2<String, Integer> tuple1 = Tuple.of("Hello", 100);
        Tuple2<String, Integer> updateTuple = tuple1.map(String::toUpperCase, v -> v * 5);
        String result = updateTuple.apply((str, number) -> String.join(",", str, number.toString()));
        System.out.println("result = " + result);
    }
}
