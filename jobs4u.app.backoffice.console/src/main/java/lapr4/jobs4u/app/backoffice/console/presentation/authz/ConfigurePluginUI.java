/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.app.backoffice.console.presentation.authz;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import lapr4.jobs4u.app.candidate.console.presentation.myuser.UserDataWidget;
import lapr4.jobs4u.candidatemanagement.application.ConfigurePluginController;
import lapr4.jobs4u.candidatemanagement.application.RegisterCandidateController;
import lapr4.jobs4u.candidatemanagement.domain.Description;
import lapr4.jobs4u.candidatemanagement.domain.JarFileName;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.usermanagement.domain.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static java.util.zip.ZipFile.OPEN_READ;

/**
 * UI for adding a user to the application.
 * <p>
 * Created by nuno on 22/03/16.
 */
public class ConfigurePluginUI extends AbstractUI {


    private final ConfigurePluginController controller = new ConfigurePluginController();



    private JarFileName jarFileName;
    private PluginType pluginType;
    private Description description;
//    public static void main(final String[] args) {
//        ConfigurePluginUI a = new ConfigurePluginUI();
//        a.doShow();
//
//    }
    @Override
    protected boolean doShow() {
        List<PluginType> pluginTypeList = controller.fetchPluginTypes();
        List<String> jarFileList = controller.fetchJarFiles();
        int plugType = 0;
        if (pluginTypeList.isEmpty()) {
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
            return false;
        }
        if (jarFileList.isEmpty()) {
            System.out.println(
                    "There are no Jar Files in the Folder.");
            return false;
        }
        while (plugType <= 0 | plugType > pluginTypeList.size()){
          controller.displayPluginTypes(pluginTypeList);
        plugType = Console.readInteger("Plugin Type >");
        }
        this.pluginType = pluginTypeList.get(plugType-1);
        boolean invalid = true;
        int opt = -1;
        while (invalid) {
            controller.displayOptions(jarFileList);
            opt = Console.readInteger("Jar File >");
            if (opt <= 0 | opt > jarFileList.size()) {
                System.out.println("Invalid option");
            } else {
                invalid = false;
            }
        }
        this.jarFileName = new JarFileName(jarFileList.get(opt-1));
        this.description = new Description(Console.readNonEmptyLine("Description of the Plugin > ","Description cannot be empty"));
        try {
               controller.createPlugin(pluginType, jarFileName, description);
        } catch (final IntegrityViolationException | ConcurrencyException | IOException e) {
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system administrator.");
            return false;
        }

        return true;
    }

    @Override
    public String headline() {
        return "Add Candidate";
    }
}
