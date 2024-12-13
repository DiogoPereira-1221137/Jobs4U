/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.candidatemanagement.application;

import eapli.framework.application.UseCaseController;
import lapr4.jobs4u.candidatemanagement.domain.*;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.util.zip.ZipFile.OPEN_READ;

/**
 * Created by nuno on 21/03/16.
 */
@UseCaseController
public class ConfigurePluginController {

    private final PluginRepository repo = PersistenceContext.repositories().plugins();

    /**
     * Fetch plugin types list.
     *
     * @return the list
     */
    public List<PluginType> fetchPluginTypes() {
        return PluginType.pluginTypeList();
    }

    /**
     * Fetch jar files list.
     *
     * @return the list
     */
    public List<String> fetchJarFiles() {
        List<String> list = new ArrayList<>();
        File folder = new File("import");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".jar")) {
                    list.add(listOfFiles[i].getName());
                }
            }
        }
        return list;
    }

    /**
     * Display options.
     *
     * @param list the list
     */
    public void displayOptions(List<String> list) {
        int index = 1;
        for (String s: list){
            System.out.printf("%d - %s\n", index, s);
            index++;
        }
    }

    /**
     * Display plugin types.
     *
     * @param list the list
     */
    public void displayPluginTypes(List<PluginType> list) {
        int index = 1;
        for (PluginType s: list){
            System.out.printf("%d - %s\n", index, s.getPluginTypeName());
            index++;
        }
    }

    /**
     * Create plugin.
     *
     * @param pluginType  the plugin type
     * @param jarFileName the jar file name
     * @param description the description
     * @throws IOException the io exception
     */
    public void createPlugin(PluginType pluginType, JarFileName jarFileName, Description description) throws IOException {
        String path_to_Jar = "import/"+jarFileName.toString();
        boolean verify = true;
        JarFile jar;
        Manifest manifest;
        PluginMainClassName mainClassName;
        try {
            jar = new JarFile(new File(path_to_Jar), verify, OPEN_READ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            manifest = jar.getManifest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainClassName = new PluginMainClassName(manifest.getMainAttributes().getValue(Attributes.Name.MAIN_CLASS));
        Plugin newPlugin = new Plugin(pluginType, description, mainClassName, jarFileName);
        repo.save(newPlugin);
        jar.close();
        File jarF = new File(path_to_Jar);
        boolean f = jarF.renameTo(new File("plugins/" + jarFileName.toString()));
        System.out.println(f);

    }
}
