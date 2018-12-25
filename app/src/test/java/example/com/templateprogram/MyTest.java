package example.com.templateprogram;

import android.test.InstrumentationTestCase;

/**
 * Created by beijixiong on 2018/12/16.
 */

public class MyTest extends InstrumentationTestCase {
    public void testAdd() {
        Calculator service = new Calculator();
        int result = service.add(1, 1);
        assertEquals(2, result);
    }


}
