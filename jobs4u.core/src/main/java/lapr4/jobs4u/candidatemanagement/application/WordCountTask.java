package lapr4.jobs4u.candidatemanagement.application;

import lapr4.jobs4u.jobApplication.domain.ApplicationFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCountTask implements Runnable {
    private ApplicationFile file;
    private Map<String, Integer> wordCountsPrivate;
    private Map<String, Integer> counter;
    private final Map<String, List<String>> fileAppearances;

    public WordCountTask(ApplicationFile file, Map<String, Integer> counter, Map<String, List<String>> fileAppearances) {
        this.file = file;
        this.wordCountsPrivate = new HashMap<>();
        this.counter = counter;
        this.fileAppearances = fileAppearances;
    }

    @Override
    public void run() {
//        try {
//            String content = new String(Files.readAllBytes(file.toPath()));
            String content = file.contentAsString();
            String[] words = content.split("\\W+");

            for (String word : words) {
                word = word.toLowerCase();
                wordCountsPrivate.put(word, wordCountsPrivate.getOrDefault(word, 0) + 1);

                synchronized(counter){
                    counter.put(word, counter.getOrDefault(word, 0) + 1);
                    List<String> files = fileAppearances.getOrDefault(word, new ArrayList<>());
                    if (!files.contains(file.fileName())) {
                        files.add(file.fileName());
                        fileAppearances.put(word, files);
                    }
                }
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public Map<String, Integer> getWordCounts() {
//        return wordCounts;
//    }
}

