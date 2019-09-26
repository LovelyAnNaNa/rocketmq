package com.wang.other;

import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import lombok.val;
import org.apache.poi.ss.formula.functions.T;
import org.assertj.core.util.Preconditions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * @Auther: wbh
 * @Date: 2019/9/25 13:54
 * @Description:
 */
public class GuavaTest {

    @Test
    public void testDemo() {
        List<String> list = Lists.newArrayList("wang", "hello", "world");
        //过滤集合中长度小于4的字符串
        Collection<String> filter = Collections2.filter(list, s -> s.length() > 4 );
        System.out.println(filter);


//        Collections2.transform()

//        HashBasedTable<String, String, Integer> table = HashBasedTable.create();
//        table.put("张三","语文",80);
//        table.put("张三","数学",90);
//        table.put("张三","英语",70);
//        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
//        cells.forEach(e -> System.out.println(e.getRowKey() + "  " + e.getColumnKey() + "  " + e.getValue()));

    }
}










































