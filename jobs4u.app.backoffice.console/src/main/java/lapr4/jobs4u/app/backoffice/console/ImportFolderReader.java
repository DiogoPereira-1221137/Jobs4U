package lapr4.jobs4u.app.backoffice.console;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImportFolderReader {


    public List<String> getFilesFromImportFolder() {
        List<String> list = new ArrayList<>();
        File folder = new File("import");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".txt")) {
                    list.add(listOfFiles[i].getName());
                }
            }
        }
        return list;
    }


}
