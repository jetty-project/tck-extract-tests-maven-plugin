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
