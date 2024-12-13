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
#include <sys/wait.h>
#define BUFFER_SIZE 80
#define MAX_CANDIDATES 27
#define WORKER_NUMBER 10

#define SIGPARENT  SIGUSR1
#define SIGWORKER  SIGUSR1
#define SIGMONITOR SIGUSR2

int worker_status[WORKER_NUMBER];
pid_t c;
pid_t workers[WORKER_NUMBER];

typedef struct {
    char list[MAX_CANDIDATES][100];
    int size;
} c1;

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

int main() {
    printf("\033[0;31m<Parent Process>  Starting Up...\n");
    int up[2];
    if (pipe(up) == -1) {
        perror("Pipe failed");
        exit(1);
    }
    int status = 0;
    c = fork();
    if (c == -1) {
        perror("Fork failed");
        exit(1);
    } else if (c == 0) {
        printf("\033[0;33m<Monitor Process> No Process...\n");
        close(up[0]);
        printf("\033[0;33m<Monitor Process> No Process2...\n");
        dup2(up[1], STDOUT_FILENO);
        printf("\033[0;33m<Monitor Process> No Process3...\n");
        execl("./us2001_monitor", "./us2001_monitor", NULL);
        perror("Exec failed");
        exit(1);
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
            if (pipe(workerPipe[i]) == -1) {
                perror("Pipe failed");
                exit(1);
            }
            workers[i] = fork();
            worker_status[i] = 1;
            if (workers[i] == 0) {
                close(workerPipe[i][1]);
                dup2(workerPipe[i][0], STDIN_FILENO);
                close(workerPipe[i][0]);

                execl("./us2001_worker", "./us2001_worker", NULL);
                perror("Exec failed");
                exit(1);
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
//                while(!out) {
//                    if (index == 0) printf("\033[0;31m<Parent Process>  Looking For Available Worker...\n");
//                    if (worker_status[index]) {
//                        printf("\033[0;31m<Parent Process>  Sending File Name to Worker %d\n", index);
//                        worker_status[index] = 0;
//                        write(workerPipe[index][1], pref.list[i], strlen(pref.list[i])+1);
//                        out=1;
//                        kill(workers[index], SIGPARENT);
//                    }
//                    index=(index+1)%WORKER_NUMBER;
//                }
            }
//            while(working(worker_status)){
//            }

//            printf("\033[0;31m<Parent Process>  Generating Report...\n");
//            DIR *diretorio;
//            struct dirent *entrada;
//            FILE *arquivo;
//
//
//            char dirFilPref[255];
//            char importSource[255];
//            time_t current_time;
//            current_time = time(NULL);
//            char unixT[255];
//            sprintf(unixT, "%lld", current_time);
//            char rep[255] = "report_";
//            char txt[255] = ".txt";
//            strcat(rep, unixT);
//            strcat(rep, txt);
//            arquivo = fopen(rep, "w");
//
//
//            for (size_t i = 0; i < pref.size; i++) {
//
//                strcpy(dirFilPref, "../import/");
//                strcat(dirFilPref, pref.list[i]);
//
//                diretorio = opendir(dirFilPref);
//
//
//                if (diretorio == NULL) {
//                    printf("\033[0;31m<Parent Process>  Error opening Folder.\n");
//                    return 1;
//                }
//
//                if (arquivo == NULL) {
//                    printf("\033[0;31m<Parent Process>  Error opening File.\n");
//                    closedir(diretorio);
//                    return 1;
//                }
//
//                while ((entrada = readdir(diretorio)) != NULL) {
//                    if (strcmp(entrada->d_name, ".") != 0 && strcmp(entrada->d_name, "..") != 0) {
//                        fprintf(arquivo, "%s/%s\n", dirFilPref,entrada->d_name);
//                    }
//                }
//
//
//            }
//
//            printf("\033[0;31m<Parent Process>  Report successfully written.\n");
//            closedir(diretorio);
//            fclose(arquivo);
        }

    }
    return 0;
}
