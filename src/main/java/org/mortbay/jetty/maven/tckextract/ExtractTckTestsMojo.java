package org.mortbay.jetty.maven.tckextract;


import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mojo( name = "extract-tck-tests", defaultPhase = LifecyclePhase.PROCESS_SOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class ExtractTckTestsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    private static final String TEST_NAME_TAG = "@testName:";

    /**
     * The target file with all the tests to rn.
     */
    @Parameter( defaultValue = "${project.build.directory}/tck-test.txt", required = true)
    private File tckTestsFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {
            Files.deleteIfExists(tckTestsFile.toPath());
            tckTestsFile.getParentFile().mkdirs();
            if (!tckTestsFile.exists()) {
                tckTestsFile.createNewFile();
            }
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        // TODO iterate for all source roots
        SourceRoot sourceRoot = new SourceRoot(Paths.get(project.getCompileSourceRoots().get(0)));
        sourceRoot.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_11);

        try {

//            sourceRoot.parseParallelized(new SourceRoot.Callback() {
//                @Override
//                public Result process(Path localPath, Path absolutePath, ParseResult<CompilationUnit> result) {
//                    return null;
//                }
//            });

            List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse( "");
            List<String> classNameMethods = new ArrayList<>();
            for (ParseResult<CompilationUnit> parseResult : parseResults) {
                CompilationUnit cu = parseResult.getResult().get();
                String fqcn = cu.getPackageDeclaration().get().getNameAsString() + "." + cu.getPrimaryType().get().getName();
                cu.getChildNodes().stream()
                        .filter(node -> !node.getOrphanComments().isEmpty())
                        .map(node -> node.getOrphanComments())
                        .forEach(comments -> comments.stream().forEach(comment -> {
                            // parsing comment block....
                            Arrays.asList(comment.getContent().split(System.lineSeparator()))
                                    .stream().filter(s -> StringUtils.contains(s, TEST_NAME_TAG))
                                    .map(s -> StringUtils.substringAfter(s, TEST_NAME_TAG))
                                    .forEach(s -> classNameMethods.add(fqcn + "#" + StringUtils.trim(s)));
                        }));

            }
            Collections.sort(classNameMethods);
            Files.write(tckTestsFile.toPath(), classNameMethods, StandardCharsets.UTF_8, StandardOpenOption.APPEND);


            getLog().debug("end");
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }
}
