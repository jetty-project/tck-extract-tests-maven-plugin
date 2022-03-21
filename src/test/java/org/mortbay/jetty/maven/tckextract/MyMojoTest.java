package org.mortbay.jetty.maven.tckextract;


import org.apache.maven.plugin.testing.MojoRule;

import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MyMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testSomething()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        ExtractTckTests myMojo = ( ExtractTckTests ) rule.lookupConfiguredMojo( pom, "extract-tck-tests" );
        assertNotNull( myMojo );
        myMojo.execute();

        Path tckTests = Paths.get("target/test-classes/project-to-test/target/tck-test.txt");
        assertTrue(Files.exists(tckTests));
        List<String> lines = Files.readAllLines(tckTests);
        assertEquals(4, lines.size());
    }

}

