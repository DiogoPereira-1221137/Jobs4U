package lapr4.jobs4u.jobopening.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import lapr4.jobs4u.candidatemanagement.domain.Plugin;
import lapr4.jobs4u.candidatemanagement.domain.PluginType;
import lapr4.jobs4u.candidatemanagement.repositories.PluginRepository;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.jobopening.domain.JobOpening;
import lapr4.jobs4u.jobopening.repositories.JobOpeningRepository;
import lapr4.jobs4u.usermanagement.domain.Roles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * The type Generate interview model controller.
 */
@UseCaseController
public class GenerateInterviewModelController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    private final PluginRepository repoPlugin = PersistenceContext.repositories().plugins();

    /**
     * All job openings iterable.
     *
     * @return the iterable
     */
    public Iterable<JobOpening> allJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        return this.repo.jobOpenings();
    }

    /**
     * All interview plugin iterable.
     *
     * @return the iterable
     */
    public Iterable<Plugin> allInterviewPlugin() {

        authz.ensureAuthenticatedUserHasAnyOf(Roles.OPERATOR, Roles.CUSTOMER_MANAGER, Roles.ADMIN);

        return this.repoPlugin.findPluginByType(PluginType.INTERVIEW);


    }

    /**
     * Generate interview template.
     *
     * @param jobOpening the job opening
     * @throws IOException the io exception
     */
    public void generateInterviewTemplate(JobOpening jobOpening) throws Exception {


        Plugin plugin = jobOpening.interviewPlugin();
        if (plugin==null){ throw new Exception("This JobOpening doesn't have an Interview Model Plugin");}

            try {
                String jarPath = "plugins/" + plugin.jarFileName().toString(); //<-Repositório, jarFileName
                File filePlugin = new File(jarPath);
                URL url = filePlugin.toURI().toURL();

                URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
                Class<?> loadedClass = classLoader.loadClass(plugin.pluginMainClassName().name()); //<-Repositorio, PluginMainClass
                Method method = loadedClass.getMethod("exportText"); //<-Convenção, sempre o mesmo
                Object result = method.invoke(null);
                List<String> list = (List<String>) result;
                //for (String s: list) {System.out.println(s);} //<-Substituir por escrita no ficheiro


                String filePath = "template/" + jobOpening.companyName().getId() + "_ITV_template.txt";

                // Crie um objeto File com o caminho do arquivo
                File file = new File(filePath);

                // Crie o arquivo
                if (file.createNewFile()) {
                    System.out.println("\033[32mFile created: \033[0m" + file.getName());
                } else {
                    System.out.println("\033[33mThe file already exists.\033[0m");
                }

                FileWriter writer = new FileWriter(file);

                for (String s : list) {
                    writer.write(s + "\n");
                }

                writer.close();

                System.out.println("\033[34mData successfully saved in the file.\033[0m");


            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }




}
