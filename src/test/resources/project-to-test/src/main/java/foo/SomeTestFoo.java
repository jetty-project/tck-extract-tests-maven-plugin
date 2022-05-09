package foo;

import org.junit.Test;

public class SomeTestFoo {

    /**
     * @testName: flushBufferTestFoo
     *
     * @assertion_ids: Servlet:JAVADOC:348
     *
     * @test_Strategy: Servlet wraps response. Servlet writes data in the buffer
     * and flushes it
     */

    /**
     * @testName: getBufferSizeTestFoo
     *
     * @assertion_ids: Servlet:JAVADOC:347
     *
     * @test_Strategy: Servlet wraps response. Servlet flushes buffer and verifies
     * the size of the buffer
     */

    /**
     * @testName: getLocaleTestFoo
     *
     * @assertion_ids: Servlet:JAVADOC:354
     *
     * @test_Strategy: Servlet wraps response. Servlet set Locale and then
     * verifies it
     *
     */

    /**
     * @testName: testItFoo
     *
     * @assertion_ids: Servlet:JAVADOC:485
     *
     * @test_Strategy: just do this
     *
     */

    @Test
    public void testIt() throws Exception {
        // not much really
    }

}