#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <sys/stat.h>
#include <errno.h>
#include <time.h>
#define BUFFER_SIZE 80
#define MAX_CANDIDATES 27
#define WORKER_NUMBER 10

#define SIGPARENT SIGUSR1
#define SIGWORKER SIGUSR1

void extract_prefix(const char *input, char delimiter, char *prefix) {
    char *delimiter_pos = strchr(input, delimiter);

    if (delimiter_pos != NULL) {
        strncpy(prefix, input, delimiter_pos - input);
        prefix[delimiter_pos - input] = '\0';
    } else {
        strcpy(prefix, input);
    }
}
void processFiles(const char *old_folder_path, const char *new_folder_path, const char *to_match) {
    int prefIndex = 0;
    DIR *dir;
    struct dirent *entry;

    dir = opendir(old_folder_path);
    if (dir == NULL) {

        perror("opendir");
        return '\0';
    }

    while ((entry = readdir(dir)) != NULL && prefIndex < MAX_CANDIDATES) {

        if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {

            char full_path[255];
            snprintf(full_path, sizeof(full_path), "%s/%s", old_folder_path, entry->d_name);

            struct stat file_stat;
            if (stat(full_path, &file_stat) == 0 && S_ISREG(file_stat.st_mode)) {
                char prefix[255];
                extract_prefix(entry->d_name, '-', prefix);
//                    printf(">>%s\n",prefix);
//                    printf(">>%s\n",to_match);
                if(strcmp(prefix, to_match) == 0){
                    //rename;
                    char old_full[255];
                    char new_full[255];
                    strcpy(old_full, old_folder_path);
                    strcat(old_full, "/");
                    strcat(old_full, entry->d_name);

                    strcpy(new_full, new_folder_path);
                    strcat(new_full, to_match);
                    if (mkdir(new_full, 0777) == 0) {
//                        printf("Folder '%s' created successfully.\n", folder_path);
                    }
                    strcat(new_full, "/");
                    strcat(new_full, entry->d_name);
                    if (rename(old_full, new_full) == 0) {
                        printf("\033[0;36m<Worker Process>File processed successfully. - %s >>> %s\n",old_full, new_full);
                    } else {
                        perror("\033[0;36m<Worker Process>Error processing file");
                    }
                }
            }
        }
    }
    closedir(dir);
}
void handle_SIGPARENT(){
    printf("\033[0;31m<Parent Process>  Requesting File Names\n");
}

int main() {
    char fileName[255];
    struct sigaction act;
    memset(&act, 0, sizeof(struct sigaction));
    act.sa_handler = handle_SIGPARENT;
    sigaction(SIGPARENT, &act, NULL);

    printf("\033[0;36m<Worker Process>  Starting Up...\n");

    while (1) {
        pause();
        if (read(STDIN_FILENO, fileName, sizeof(fileName)) == -1) {
            perror("\033[0;36m<Worker Process>  Read from pipe failed");
        }

        processFiles("../EmailBotOutput", "../import/", fileName);


        kill(getppid(), SIGWORKER);
    }
}