#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <sys/stat.h>
#include <errno.h>
#include <time.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>


#define MAX_LINE_LENGTH 255
#define MAX_CANDIDATES 27


void testInputFolderEmpty(const char *folder_path){
  DIR *dir;
  struct dirent *entry;
//  printf("%s\n", folder_path);

  dir = opendir(folder_path);
  if (dir == NULL) {
      perror("opendir");
  }

  int isEmpty = 1;
  while ((entry = readdir(dir)) != NULL) {
      if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
          isEmpty = 0;
          break;
      }
  }

  closedir(dir);

  if (isEmpty) {
      printf("\033[0;36m<Tests Process> Successful - Input Folder is empty.\n");
  } else {
      printf("\033[0;36m<Tests Process> ERROR - Input Folder has files.\n");
  }
}



void testOutputFolders(const char *folder_path, const char prefixes[MAX_CANDIDATES][255], int length) {
    DIR *dir;
    struct dirent *entry;

    dir = opendir(folder_path);
    if (dir == NULL) {
        perror("opendir");
        return;
    }

    printf("Length: %d\n", length);
    int found = 0;


    while ((entry = readdir(dir)) != NULL) {

      if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
        if (entry->d_type == DT_DIR) {
          char subfolder_path[1024];
          snprintf(subfolder_path, sizeof(subfolder_path), "%s/%s", folder_path, entry->d_name);

          DIR *subdir = opendir(subfolder_path);

          if (subdir != NULL) {
            struct dirent *subentry;
            for (int i = 0; i < length; i++) {
             //found = 0;
              rewinddir(subdir);
              while ((subentry = readdir(subdir)) != NULL) {
                if (strcmp(subentry->d_name, ".") != 0 && strcmp(subentry->d_name, "..") != 0) {
                  if (subentry->d_type == DT_DIR && strcmp(subentry->d_name, prefixes[i]) == 0) {
                      found = 1;
                      printf("\033[0;36m<Tests Process> Found folder with prefix %s in %s\n", prefixes[i], subfolder_path);
                      break;
                    }
                  }
                }
                // if (!found) {
                //     printf("\033[0;36m<Tests Process> No folder found with prefix %s in %s\n", prefixes[i], subfolder_path);
                // }
              }
            closedir(subdir);
          }
        }
      }
    }

    if (!found) {
       printf("\033[0;36m<Tests Process> No matching folder found in any subdirectory of %s\n", folder_path);
    }


    closedir(dir);
}




int main() {
    DIR *diretorio;

    char fileName[255] = "../us_2001b_2/DataFile.txt";
    char fullPathInput[MAX_LINE_LENGTH];
    char fullPathOutput[MAX_LINE_LENGTH];
    char prefixes[MAX_CANDIDATES][255];
    int prefixCount = 0;

    printf("\033[0;31m<Test Process> Reading from DataFile...\n");
    FILE *arquivoData = fopen(fileName, "r");

    if (arquivoData == NULL) {
        printf("\033[0;31m<Tests Process> Error opening DataFile.\n");
        return 1;
    }



    char line[MAX_LINE_LENGTH];
    while (fgets(line, sizeof(line), arquivoData)) {
        if (strncmp(line, "InputPath: ", 11) == 0) {
            strcpy(fullPathInput, line + 11);
            fullPathInput[strcspn(fullPathInput, "\n")] = '\0';

        } else if (strncmp(line, "FullPathOutput: ", 16) == 0) {
            strcpy(fullPathOutput, line + 16);
            fullPathOutput[strcspn(fullPathOutput, "\n")] = '\0';
        } else if (strncmp(line, "Prefix: ", 8) == 0) {
            strcpy(prefixes[prefixCount], line + 8);
            prefixes[prefixCount][strcspn(prefixes[prefixCount], "\n")] = '\0';
            prefixCount++;
        }
    }

    fclose(arquivoData);

    printf("InputPath: %s\n", fullPathInput);
    printf("FullPathOutput: %s\n", fullPathOutput);
    // for (int i = 0; i < prefixCount; i++) {
    //     printf("Prefix: %s\n", prefixes[i]);
    // }
    printf("\033[0;31m<Tests Process> Getting info from DataFile successfully.\n");

    diretorio = opendir(fullPathOutput);
    if (diretorio == NULL) {
        printf("\033[0;31m<Tests Process> Error opening Folder.\n");
    }

    testInputFolderEmpty(fullPathInput);
    testOutputFolders(fullPathOutput, prefixes, prefixCount);

  return 0;
}
