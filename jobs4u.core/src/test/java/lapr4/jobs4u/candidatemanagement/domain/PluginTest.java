package lapr4.jobs4u.candidatemanagement.domain;

import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.Types;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.util.zip.ZipFile.OPEN_READ;
import static org.junit.jupiter.api.Assertions.*;

public class PluginTest {
    @Test(expected = java.lang.NullPointerException.class)
    public void ensureValidationExceptionForNull() throws Exception {
        PluginIdentifier.validateIdentifier(null);
    }
    @Test
    public void ensureValidationFailsForEmptyString() throws Exception {
        final boolean actual = PluginIdentifier.validateIdentifier("");

        assertFalse(actual);
    }
    @Test
    public void ensureValidationFailsForWrongFormat() throws Exception {
        final boolean actual = PluginIdentifier.validateIdentifier("123_ADHMALKDJA");

        assertFalse(actual);
    }
    @Test
    public void ensureValidationSuccessForCorrectFormat() throws Exception {
        final boolean actual = PluginIdentifier.validateIdentifier("REQ_123456789_jarfileName");

        assertTrue(actual);
    }

    @Test
    public void generatePluginIdentifierSuccess() throws Exception{
        final PluginIdentifier actual = PluginIdentifier.generatePluginIdentifier(new PluginType("Test", "TES"), new JarFileName("jarFileName"), 123456789L);

        assertNotNull(actual);
    }

    @Test
    public void ensureEqualPluginIdentifiers() throws Exception{
        final PluginIdentifier expected = new PluginIdentifier("TES_123456789_jarFileName");
        final PluginIdentifier actual = PluginIdentifier.generatePluginIdentifier(new PluginType("Test", "TES"), new JarFileName("jarFileName"), 123456789L);

        assertEquals(expected,actual);
    }

    @Test
    public void ensureDifferentPluginIdentifiers() throws Exception{
        final PluginIdentifier id1 = PluginIdentifier.generatePluginIdentifier(new PluginType("Test", "TES"), new JarFileName("jarFileName"), 987654321L);
        final PluginIdentifier id2 = PluginIdentifier.generatePluginIdentifier(new PluginType("Test", "TES"), new JarFileName("jarFileName"), 123456789L);

        assertNotEquals(id1,id2);
    }

    @Test
    public void testPluginCreation() {
        PluginType pluginType = PluginType.INTERVIEW;
        Description description = new Description("Description");
        PluginMainClassName mainClassName = new PluginMainClassName("MainClassName");
        JarFileName jarFileName = new JarFileName("FileName.jar");

        Plugin plugin = new Plugin(pluginType, description, mainClassName, jarFileName);

        assertNotNull(plugin);
        assertEquals(pluginType, plugin.pluginType());
        assertEquals(description, plugin.description());
        assertEquals(mainClassName, plugin.pluginMainClassName());
        assertEquals(jarFileName, plugin.jarFileName());
    }

    @Test
    public void testPluginEquality() {
        PluginType pluginType = PluginType.REQUIREMENTS;
        Description description = new Description("Description");
        PluginMainClassName mainClassName = new PluginMainClassName("MainClassName");
        JarFileName jarFileName = new JarFileName("FileName.jar");
        PluginIdentifier pluginIdentifier = PluginIdentifier.generatePluginIdentifier(pluginType, jarFileName, 123456789L);


        Plugin plugin1 = new Plugin(pluginIdentifier, pluginType, description, mainClassName, jarFileName);
        Plugin plugin2 = new Plugin(pluginIdentifier, pluginType, description, mainClassName, jarFileName);

        assertEquals(plugin1, plugin2);
        assertEquals(plugin1.pluginMainClassName().name(),plugin2.pluginMainClassName().name());
        assertEquals(plugin1.pluginType().getPluginTypeName(),plugin2.pluginType().getPluginTypeName());
    }

    @Test
    public void testPluginInequality() {
        PluginType pluginType1 = PluginType.REQUIREMENTS;
        PluginType pluginType2 = PluginType.INTERVIEW;
        Description description1 = new Description("Description1");
        Description description2 = new Description("Description2");
        PluginMainClassName mainClassName1 = new PluginMainClassName("MainClassName1");
        PluginMainClassName mainClassName2 = new PluginMainClassName("MainClassName2");
        JarFileName jarFileName1 = new JarFileName("FileName1.jar");
        JarFileName jarFileName2 = new JarFileName("FileName2.jar");

        Plugin plugin1 = new Plugin(pluginType1, description1, mainClassName1, jarFileName1);
        Plugin plugin2 = new Plugin(pluginType2, description2, mainClassName2, jarFileName2);

        assertNotEquals(plugin1, plugin2);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testPluginError(){
        Plugin plugin1 = new Plugin(null, null, null, null);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void ensurePluginTypeErrorWhenNull(){
        PluginType pt = new PluginType(null, null);
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void ensurePluginTypeErrorWhenInvalid(){
        PluginType pt = new PluginType("Null", "Null");
    }

    @Test
    public void ensurePluginTypeNotNullWhenValid(){
        PluginType pt = new PluginType("Null", "NIL");
        assertNotNull(pt);
    }

    @Test
    public void ensurePluginTypeEqual(){
        PluginType pt = new PluginType("Null", "NIL");
        PluginType pt2 = new PluginType("Null", "NIL");
        assertEquals(pt,pt2);
    }

    @Test
    public void ensurePluginTypeNotEqual(){
        PluginType pt = new PluginType("Null", "NIL");
        PluginType pt2 = new PluginType("False", "FAL");
        assertNotEquals(pt,pt2);
    }

    @Test
    public void ensureValidSyntax() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testPlugin.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.requirements.RequirementsA");
        Method method = jarFile.getMethod("antlr", String.class);

        assertTrue((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/textValid.txt"));

    }

    @Test
    public void ensureInvalidSyntax() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testPlugin.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.requirements.RequirementsA");
        Method method = jarFile.getMethod("antlr", String.class);
        assertFalse((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/textInvalid.txt"));
    }

    @Test
    public void ensureValidRequirements() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testRequirementsPlugin.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.requirements.RequirementsA");
        Method method = jarFile.getMethod("verifyRequirements", String.class);

        assertTrue((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/requirementTxtValid.txt"));

    }

    @Test
    public void ensureInvalidRequirements() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testRequirementsPlugin.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.requirements.RequirementsA");
        Method method = jarFile.getMethod("verifyRequirements", String.class);
        assertFalse((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/requirementTxtInvalid.txt"));
    }






















    @Test
    public void ensureValidSyntaxInterview() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testPlugin2Int.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.interview.Interview");
        Method method = jarFile.getMethod("antlr", String.class);

        assertTrue((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/InterviewModel.txt"));

    }

    @Test
    public void ensureInvalidSyntaxInterview() throws Exception {
        String jarPath = "src/test/java/lapr4/jobs4u/candidatemanagement/domain/plugin/testPlugin2Int.jar";
        File filePlugin = new File(jarPath);
        URL url = filePlugin.toURI().toURL();

        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> jarFile = classLoader.loadClass("com.interview.Interview");
        Method method = jarFile.getMethod("antlr", String.class);
        assertFalse((Boolean) method.invoke(null, "src/test/java/lapr4/jobs4u/candidatemanagement/domain/txt/textInvalid.txt"));
    }


























}
