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

int main()
{

    pid_t c = fork();
    if(c==0) {
        char* args[] = { "./us2001", NULL };
        if(execvp("./us2001", args) == -1) {
            perror("execvp");
            printf("\nfailed connection\n");
            return 1;
        }
    } else {
        sleep(2);
        kill(c, SIGINT);
    }
    return 0;
}