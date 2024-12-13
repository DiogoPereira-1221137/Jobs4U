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
typedef struct {
    int size;
    char list[MAX_CANDIDATES][255];
} c1;

const int SIGPARENT = SIGUSR1;
const int SIGWORKER = SIGUSR1;
const int SIGMONITOR = SIGUSR2;
int worker_status[WORKER_NUMBER];
pid_t c;
pid_t workers[WORKER_NUMBER];
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
void **processFiles(const char *old_folder_path, const char *new_folder_path, const char *to_match) {
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
    return prefixes;
}
void handle_SIGWORKER(int signum, siginfo_t *info, void *context){
    printf("\033[0;36m<Worker Process>  File Processing Complete\n");
    int j;
//    printf("%d\n",info->si_pid);
    for(j=0;j<WORKER_NUMBER;j++) {
        if (workers[j] == info->si_pid){
         worker_status[j] = 1;
         };
    }
//    read(workerPipe[j][0], &j, sizeof(int));
}
void handle_SIGMONITOR(){
    printf("\033[0;33m<Monitor Process> Sending File Names to Parent Process\n");
}
void handle_SIGPARENT(){
    printf("\033[0;31m<Parent Process>  Requesting File Names\n");
}
void handle_SIGPARENT_2(){
    printf("\033[0;31m<Parent Process>  Requesting File Names\n");
}
void handle_SIGINT(){
printf("\033[0;31m<Parent Process>  Killing all processess...\n");
        kill(c, SIGKILL);
        for (int i = 0; i < WORKER_NUMBER; ++i) {
            kill(workers[i], SIGKILL);
        }
        for (int i = 0; i < WORKER_NUMBER; ++i) {
            waitpid(workers[i], NULL, 0);
        }
}
int working(int worker_status[]) {
    for(int k = 0; k < WORKER_NUMBER; k++) {
//printf("%d - %d\n", k, worker_status[k]);
            if(worker_status[k] == 0) return 1;
    }
    return 0;
}
int main()
{
    printf("\033[0;31m<Parent Process>  Starting Up...\n");
    int up[2];
    if(pipe(up) == -1){
        perror("Pipe failed");
        exit(1);
    }
    int status = 0;
    c = fork();
    pid_t wpid;
    int n;
    if(c==0) {
        struct sigaction act;
        memset(&act, 0, sizeof(struct sigaction));
        act.sa_handler = handle_SIGPARENT;
        sigaction(SIGPARENT, &act, NULL);
        close(up[0]);
        printf("\033[0;33m<Monitor Process> Starting Up...\n");
        while (1) {
            pause();
            while (is_folder_empty("../EmailBotOutput")) {
                sleep(2);
            }
            char **prefixes =  get_file_prefixes_in_folder("../EmailBotOutput");
            c1 pref;
            pref.size = 0;
            while (strcmp("\0",prefixes[pref.size])) {
                printf("\033[0;33m<Monitor Process> File Name Found: %s\n", prefixes[pref.size]);
                strcpy(pref.list[pref.size], prefixes[pref.size]);
                pref.size++;
            }
            write(up[1], &pref, sizeof(c1));
            kill(getppid(), SIGMONITOR);
        }
    } else {
        struct sigaction act;
        memset(&act, 0, sizeof(struct sigaction));
        act.sa_handler = handle_SIGWORKER;
        sigemptyset(&act.sa_mask);
        act.sa_flags = SA_SIGINFO;
        sigaction(SIGWORKER, &act, NULL);
        struct sigaction act2;
        memset(&act2, 0, sizeof(struct sigaction));
        act2.sa_handler = handle_SIGMONITOR;
        sigaction(SIGMONITOR, &act2, NULL);
        int workerPipe[WORKER_NUMBER][2];
        for (int i = 0; i < WORKER_NUMBER; ++i) {
            if(pipe(workerPipe[i]) == -1){
                perror("Pipe failed");
                exit(1);
            }
            workers[i] = fork();
            worker_status[i] = 1;
            if(workers[i]==0){
                struct sigaction act;
                memset(&act, 0, sizeof(struct sigaction));
                act.sa_handler = handle_SIGPARENT_2;
                sigaction(SIGPARENT, &act, NULL);
                for (int k = 0; k<i;k++){
                    close(workerPipe[k][0]);
                    close(workerPipe[k][1]);
                }
                int j = i;
                //worker(s)
                printf("\033[0;36m<Worker Process>  Starting Up...\n");
                while (1) {
                    pause();
                    char aa[255];
                    read(workerPipe[j][0], aa, 255);
                    processFiles("../EmailBotOutput","../import/",aa);
                    kill(getppid(), SIGWORKER);
                }
            }
        }
        struct sigaction act3;
        memset(&act3, 0, sizeof(struct sigaction));
        act2.sa_handler = handle_SIGINT;
        sigaction(SIGINT, &act3, NULL);
        while (1) {
            kill(c, SIGPARENT);
            pause();
            c1 pref;
            read(up[0], &pref, sizeof(c1));
            for (int i = 0; i < pref.size; ++i) {
                printf("\033[0;31m<Parent Process>  File Name Recieved: %s\n",pref.list[i]);
                int out = 0;
                int index = 0;
                while(!out) {
                    if (index == 0) printf("\033[0;31m<Parent Process>  Looking For Available Worker...\n");
                    if (worker_status[index]) {
                        printf("\033[0;31m<Parent Process>  Sending File Name to Worker %d\n", index);
                        worker_status[index] = 0;
                        write(workerPipe[index][1], pref.list[i], strlen(pref.list[i])+1);
                        out=1;
                        kill(workers[index], SIGPARENT);
                    }
                    index=(index+1)%WORKER_NUMBER;
                }
            }
            while(working(worker_status)){
            }

            printf("\033[0;31m<Parent Process>  Generating Report...\n");
            DIR *diretorio;
            struct dirent *entrada;
            FILE *arquivo;


            char dirFilPref[255];
            char importSource[255];
            time_t current_time;
            current_time = time(NULL);
            char unixT[255];
            sprintf(unixT, "%lld", current_time);
            char rep[255] = "report_";
            char txt[255] = ".txt";
            strcat(rep, unixT);
            strcat(rep, txt);
            arquivo = fopen(rep, "w");


            for (size_t i = 0; i < pref.size; i++) {

            strcpy(dirFilPref, "../import/");
            strcat(dirFilPref, pref.list[i]);

            diretorio = opendir(dirFilPref);


            if (diretorio == NULL) {
                printf("\033[0;31m<Parent Process>  Error opening Folder.\n");
                return 1;
            }

            if (arquivo == NULL) {
                printf("\033[0;31m<Parent Process>  Error opening File.\n");
                closedir(diretorio);
                return 1;
            }

            while ((entrada = readdir(diretorio)) != NULL) {
                if (strcmp(entrada->d_name, ".") != 0 && strcmp(entrada->d_name, "..") != 0) {
                    fprintf(arquivo, "%s/%s\n", dirFilPref,entrada->d_name);
                }
            }


          }

            printf("\033[0;31m<Parent Process>  Report successfully written.\n");
            closedir(diretorio);
            fclose(arquivo);
        }

    }
    return 0;
}
