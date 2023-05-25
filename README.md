# tck-extract-tests-maven-plugin

This Maven plugin will parse sources of a project to find the javadoc tag such `@testName: dispatchZeroArgTest` 
and will generate a txt file containing all test functions for a class (fqcn#method).
Such content:
```
com.sun.ts.tests.servlet.api.jakarta_servlet_http.httpservletrequest1.URLClient#getLocalNameTest
com.sun.ts.tests.servlet.api.jakarta_servlet.servletconfig.URLClient#getServletConfigInitParameterNamesTest
com.sun.ts.tests.servlet.api.jakarta_servlet.servletconfig.URLClient#getServletConfigInitParameterTest
com.sun.ts.tests.servlet.api.jakarta_servlet.servletconfig.URLClient#getServletConfigInitParameterTestNull
```

If activated via parameter `addTestMethod` this will generate missing methods in Java sources.
For a given source such 

```
public class Client extends secformClient {
......
  /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:SPEC:142; JavaEE:SPEC:21
   *
   * @test_Strategy: .....
   *
   */

  /*
   * @testName: test1_anno
   *
   * @assertion_ids: Servlet:SPEC:142; JavaEE:SPEC:21; Servlet:SPEC:290;
   * Servlet:SPEC:291; Servlet:SPEC:293; Servlet:SPEC:296; Servlet:SPEC:297;
   * Servlet:SPEC:298;
   *
   * @test_Strategy: ......
   *
   */
  public void test1_anno()  {
    // save off pageSec so that we can reuse it
    String tempPageSec = pageSec;
    pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";

    try {
      super.test1();
    } catch (Fault e) {
      throw e;
    } finally {
      // reset pageSec to orig value
      pageSec = tempPageSec;
    }
  }

```
The missing method will be added to the sources:

```
  @Test 
  public void test1()  {
      super.test1();
  }
```


How to use it:
```
        <plugin>
          <groupId>org.mortbay.jetty</groupId>
          <artifactId>tck-extract-tests-maven-plugin</artifactId>
          <version>1.0.0-SNAPSHOT</version>
          <configuration>
            <tckTestsFile>${project.build.outputDirectory}/META-INF/tck-tests.txt</tckTestsFile>
          </configuration>
          <executions>
            <execution>
              <id>generate-tck-tests-list</id>
              <phase>process-sources</phase>
              <goals>
                <goal>extract-tck-tests</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
```        
`tckTestsFile` configuration is the location of the generated file.

Or manually to modify sources to add missing methods: `mvn tck-extract-tests-maven-plugin:extract-tck-tests -DaddTestMethod=true`

