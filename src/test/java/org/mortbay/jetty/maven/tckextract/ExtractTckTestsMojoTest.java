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

public class ExtractTckTestsMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule();

    @Test
    public void testCounting()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        ExtractTckTestsMojo myMojo = (ExtractTckTestsMojo) rule.lookupConfiguredMojo( pom, "extract-tck-tests" );
        assertNotNull( myMojo );
        myMojo.execute();

        Path tckTests = Paths.get("target/test-classes/project-to-test/target/tck-test.txt");
        assertTrue(Files.exists(tckTests));
        List<String> lines = Files.readAllLines(tckTests);
        assertEquals(9, lines.size());
    }


    @Test
    public void testModify()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        ExtractTckTestsMojo myMojo = (ExtractTckTestsMojo) rule.lookupConfiguredMojo( pom, "extract-tck-tests" );
        myMojo.addTestMethod = true;
        assertNotNull( myMojo );
        myMojo.execute();

    }

}

