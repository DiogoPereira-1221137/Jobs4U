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
#define SIGMONITOR SIGUSR1

typedef struct {
    char list[MAX_CANDIDATES][100];
    int size;
} c1;

void handle_SIGPARENT(){
//    printf("\033[0;31m<Parent Process>  Requesting File Names\n");
}

int is_folder_empty(const char *folder_path) {
    DIR *dir;
    struct dirent *entry;

    dir = opendir(folder_path);
    if (dir == NULL) {
        perror("opendir");
        return -1;
    }

    while ((entry = readdir(dir)) != NULL) {
        if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            closedir(dir);
            return 0;
        }
    }

    closedir(dir);
    return 1;
}
void extract_prefix(const char *input, char delimiter, char *prefix) {
    char *delimiter_pos = strchr(input, delimiter);

    if (delimiter_pos != NULL) {
        strncpy(prefix, input, delimiter_pos - input);
        prefix[delimiter_pos - input] = '\0';
    } else {
        strcpy(prefix, input);
    }
}
int array_contains_string(char *array[], int size, const char *target) {
    for (int i = 0; i < size; i++) {
        if (strcmp(array[i], target) == 0) {
            return 1;
        }
    }
    return 0;
}
char **get_file_prefixes_in_folder(const char *folder_path) {
    char **prefixes = malloc(MAX_CANDIDATES * sizeof(char *));
    if (prefixes == NULL) {
        perror("\033[0;33m<Monitor Process>Memory allocation failed");
        return NULL;
    }

    for (int i = 0; i < MAX_CANDIDATES; i++) {
        prefixes[i] = malloc(255 * sizeof(char));
        if (prefixes[i] == NULL) {
            perror("\033[0;33m<Monitor Process>Memory allocation failed");
            for (int j = 0; j < i; j++) {
                free(prefixes[j]);
            }
            free(prefixes);
            return NULL;
        }
        prefixes[i][0] = '\0';
    }

    int prefIndex = 0;
    DIR *dir;
    struct dirent *entry;

    dir = opendir(folder_path);
    if (dir == NULL) {
        perror("opendir");
        return '\0';
    }

    while ((entry = readdir(dir)) != NULL && prefIndex < MAX_CANDIDATES) {
        if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            char full_path[255];
            snprintf(full_path, sizeof(full_path), "%s/%s", folder_path, entry->d_name);

            struct stat file_stat;
            if (stat(full_path, &file_stat) == 0 && S_ISREG(file_stat.st_mode)) {
                char prefix[255];
                extract_prefix(entry->d_name, '-', prefix);
                if(!array_contains_string(prefixes, MAX_CANDIDATES, prefix)){
                    strcpy(prefixes[prefIndex],prefix);
                    prefIndex++;
                }
            }
        }
    }
    closedir(dir);
//    strcpy(prefixes[0],folder_path);
    return prefixes;
}
int main() {
//    printf("\033[0;33m<Monitor Process> Entrad...\n");
    struct sigaction act;
    memset(&act, 0, sizeof(struct sigaction));
    act.sa_handler = handle_SIGPARENT;
    sigaction(SIGPARENT, &act, NULL);

    int up[2];
    if (dup2(STDIN_FILENO, up[0]) == -1) {
        perror("Dup2 failed");
        return 1;
    }
    close(up[1]);
//    printf("\033[0;33m<Monitor Process> Starting Up...\n");
    while (1) {
        pause();
        while (is_folder_empty("../EmailBotOutput")) {
            sleep(2);
        }
        char **prefixes =  get_file_prefixes_in_folder("../EmailBotOutput");
        c1 pref;
        pref.size = 0;
        while (strcmp("\0",prefixes[pref.size])) {
//            printf("\033[0;33m<Monitor Process> File Name Found: %s\n", prefixes[pref.size]);
            strcpy(pref.list[pref.size], prefixes[pref.size]);
            fprintf(stdout,"%d\n",prefixes[pref.size]);
            pref.size++;
        }
        write(up[0], &pref, sizeof(c1));
        kill(getppid(), SIGMONITOR);
    }
}