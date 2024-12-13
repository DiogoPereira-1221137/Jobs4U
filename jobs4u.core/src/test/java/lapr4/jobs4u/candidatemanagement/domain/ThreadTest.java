package lapr4.jobs4u.candidatemanagement.domain;

import lapr4.jobs4u.candidatemanagement.application.WordCountTask;
import lapr4.jobs4u.jobApplication.domain.ApplicationFile;
import lapr4.jobs4u.jobApplication.domain.Types;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadTest {

    @Test
    public void ensureThreadResultNotNull() throws Exception {
        List<ApplicationFile> files = new ArrayList<>();
        files.add(new ApplicationFile(Types.INFO, "file1", "ab cd ab dd ad cd aba ab ab"));
        List<Thread> threads = new ArrayList<>();
        List<WordCountTask> tasks = new ArrayList<>();
        Map<String, Integer> counter = new HashMap<>();
        Map<String, List<String>> fileAppearances = new HashMap<>();
        for (ApplicationFile file : files) {
            WordCountTask task = new WordCountTask(file, counter, fileAppearances);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
        }
        assertNotNull(counter);
    }
    @Test
    public void ensureThreadIsValid() throws Exception {
        List<ApplicationFile> files = new ArrayList<>();
        files.add(new ApplicationFile(Types.INFO, "file1", "ab cd ab dd ad cd aba ab ab"));
        files.add(new ApplicationFile(Types.INFO, "file2", "ab cd ab dd ad cd aba ab ab xyz xasd asdf"));
        files.add(new ApplicationFile(Types.INFO, "file3", "1 2 3 4 2 12 54 13 040404 5 19 20 21 22"));
        List<Thread> threads = new ArrayList<>();
        List<WordCountTask> tasks = new ArrayList<>();
        Map<String, Integer> counter = new HashMap<>();
        Map<String, Integer> counterExpected = new HashMap<>();
        counterExpected.put("dd",2);
        counterExpected.put("12",1);
        counterExpected.put("ab",8);
        counterExpected.put("cd",4);
        counterExpected.put("aba",2);
        counterExpected.put("13",1);
        counterExpected.put("xasd",1);
        counterExpected.put("ad",2);
        counterExpected.put("1",1);
        counterExpected.put("2",2);
        counterExpected.put("3",1);
        counterExpected.put("4",1);
        counterExpected.put("5",1);
        counterExpected.put("040404",1);
        counterExpected.put("xyz",1);
        counterExpected.put("asdf",1);
        counterExpected.put("54",1);
        counterExpected.put("19",1);
        counterExpected.put("20",1);
        counterExpected.put("21",1);
        counterExpected.put("22",1);
        Map<String, List<String>> fileAppearances = new HashMap<>();
        for (ApplicationFile file : files) {
            WordCountTask task = new WordCountTask(file, counter, fileAppearances);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
        }
        System.out.println(counter);
        boolean counterEqual = true;
        for (String s : counter.keySet()) {
            if (!Objects.equals(counter.get(s), counterExpected.get(s))) counterEqual = false;
        }
        assertTrue(counterEqual);
    }

}
