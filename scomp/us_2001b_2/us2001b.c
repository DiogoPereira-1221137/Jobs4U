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
#include <semaphore.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>


#define BUFFER_SIZE 80
#define MAX_CANDIDATES 27
#define WORKER_NUMBER 10
#define MAX_PREFIXES 256

typedef struct {
    int size;
    char list[MAX_CANDIDATES][255];
} c1;

sem_t *semParent;
sem_t *semMonitor;
sem_t *semWorker[WORKER_NUMBER];

int worker_status[WORKER_NUMBER];
pid_t c;
pid_t workers[WORKER_NUMBER];

int is_folder_empty(const char *folder_path) {
    DIR *dir;
    struct dirent *entry;
//    printf("%s\n", folder_path);
//
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

void extract_job_reference(const char *input,char *job_reference){
	const char *ptr =input;
	while(*ptr != '\0'){
		if(*ptr == 'I' && *(ptr+1) == 'B' && *(ptr+2) == 'M' && *(ptr+3) == '-'){
			ptr =ptr +4;
			while(*ptr != '\0' && *ptr>='0' && *ptr<='9'){
				*job_reference =*ptr;
				job_reference++;
				ptr++;
			}
		*job_reference = '\0';
		return;
		}
		ptr++;

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
int get_job_reference_from_file(const char *file_path, char *job_reference) {
    FILE *file = fopen(file_path, "r");
    if (file == NULL) {
        perror("fopen");
        return 0;
    }

    char line[256];
    if (fgets(line, sizeof(line), file) != NULL) {
        extract_job_reference(line, job_reference);
    }

    fclose(file);
    return (strlen(job_reference) > 0);
}

void **processFiles(const char *old_folder_path, const char *new_folder_path, const char *to_match) {
    int prefIndex = 0;
    DIR *dir;
    struct dirent *entry;

    dir = opendir(old_folder_path);
    if (dir == NULL) {
        perror("opendir");
        return NULL;
    }

    char processed_prefixes[MAX_CANDIDATES][255];
    char processed_job_refs[MAX_CANDIDATES][255];
    int num_processed = 0;

    while ((entry = readdir(dir)) != NULL && prefIndex < MAX_CANDIDATES) {
        if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
            char full_path[1024];
            snprintf(full_path, sizeof(full_path), "%s/%s", old_folder_path, entry->d_name);

            struct stat file_stat;
            if (stat(full_path, &file_stat) == 0 && S_ISREG(file_stat.st_mode)) {
                char prefix[255];
                char job_reference[255] = "";

                extract_prefix(entry->d_name, '-', prefix);


                if (strstr(entry->d_name, "-candidate-data.txt")) {
                    FILE *file = fopen(full_path, "r");
                    if (file == NULL) {
                        perror("fopen");
                        continue;
                    }

                    if (fgets(job_reference, sizeof(job_reference), file) == NULL) {
                        fclose(file);
                        continue;
                    }

                    fclose(file);
                    extract_job_reference(job_reference, job_reference);
                    if (strlen(job_reference) == 0) {
                        fprintf(stderr, "\033[0;36m<Worker Process>Error: Job reference not found in %s\n", full_path);
                        continue;
                    }


                    strcpy(processed_prefixes[num_processed], prefix);
                    strcpy(processed_job_refs[num_processed], job_reference);
                    num_processed++;
                }


                int found = 0;
                char *target_job_ref = NULL;
                for (int i = 0; i < num_processed; i++) {
                    if (strcmp(processed_prefixes[i], prefix) == 0) {
                        found = 1;
                        target_job_ref = processed_job_refs[i];
                        break;
                    }
                }

                if (found || strcmp(prefix, to_match) == 0) {
                    if (target_job_ref == NULL) {
                        target_job_ref = job_reference;
                    }

                    char old_full[1024];
                    char new_full[1024];
                    char new_subfolder[1024];

                    strcpy(old_full, old_folder_path);
                    strcat(old_full, "/");
                    strcat(old_full, entry->d_name);

                    strcpy(new_full, new_folder_path);
                    strcat(new_full, "/");
                    strcat(new_full, target_job_ref);

                    if (mkdir(new_full, 0777) == -1 && errno != EEXIST) {
                        perror("mkdir");
                        continue;
                    }

                    strcpy(new_subfolder, new_full);
                    strcat(new_subfolder, "/");
                    strcat(new_subfolder, prefix);

                    if (mkdir(new_subfolder, 0777) == -1 && errno != EEXIST) {
                        perror("mkdir");
                        continue;
                    }


                    strcpy(new_full, new_subfolder);
                    strcat(new_full, "/");
                    strcat(new_full, entry->d_name);

                    if (rename(old_full, new_full) == 0) {
                        printf("\033[0;36m<Worker Process>File processed successfully. - %s >>> %s\n", old_full, new_full);
                    } else {
                        perror("\033[0;36m<Worker Process>Error processing file");
                    }
                }
            }
        }
    }
    closedir(dir);
    return (void **)EXIT_SUCCESS;
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
            char full_path[512];
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

void handle_SIGINT(){
        printf("\033[0;31m<Parent Process>  Killing all processes...\n");
        kill(c, SIGKILL);
        for (int i = 0; i < WORKER_NUMBER; ++i) {
            kill(workers[i], SIGKILL);
        }
        for (int i = 0; i < WORKER_NUMBER; ++i) {
            waitpid(workers[i], NULL, 0);
        }

        sem_close(semParent);
        sem_unlink("/semaphoreParent");
        sem_close(semMonitor);
        sem_unlink("/semaphoreMonitor");

        for (int i = 0; i < WORKER_NUMBER; ++i) {
            sem_close(semWorker[i]);
            sem_unlink("/semaphoreWorker");
        }
        shm_unlink("/shmtest");

        exit(0);

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
    char inputFolder[256];
    printf("Name of the input folder:");
    scanf("%255s", inputFolder);
    char fullPathInput[512];
    snprintf(fullPathInput, sizeof(fullPathInput), "../../%s", inputFolder);

    char outputFolder[256];
    printf("Name of the output folder:");
    scanf("%255s", outputFolder);
    char fullPathOutput[512];
    snprintf(fullPathOutput, sizeof(fullPathOutput), "../../%s", outputFolder);


    int check_interval;
    printf("Time Interval for periodic checking of new files(in seconds):");
    scanf("%d", &check_interval);


    semParent = sem_open("semaphoreParent", O_CREAT, 0644, 1);
    semMonitor = sem_open("semaphoreMonitor", O_CREAT, 0644, 0);

    for (int i = 0; i < WORKER_NUMBER; i++) {
        char semName[20];
        sprintf(semName, "semaphoreWorker%d", i);
        semWorker[i] = sem_open(semName, O_CREAT, 0644, 1);
    }

    int fd;
    int data_size = sizeof(c1);
    c1 *shared_data;

    fd = shm_open("/shmtest", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1) {
       perror("shm_open");
       exit(EXIT_FAILURE);
    }

    ftruncate(fd, data_size);
    shared_data = (c1 *)mmap(NULL, data_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);


    c = fork();
    if(c==0) {
        sem_wait(semParent);
        struct sigaction act;
        memset(&act, 0, sizeof(struct sigaction));
        printf("\033[0;33m<Monitor Process> Starting Up...\n");
        while (1) {
            if(!is_folder_empty(fullPathInput)) {

            char **prefixes =  get_file_prefixes_in_folder(fullPathInput);

            DIR *diretorio;

            char data[255] = "DataFile";
            char txt[255] = ".txt";

            printf("\033[0;31m<Parent Process>  Generating DataFile...\n");
            FILE *arquivoData;
            strcat(data, txt);
            arquivoData = fopen(data, "w");
            diretorio = opendir(fullPathOutput);

            if (diretorio == NULL) {
                printf("\033[0;31m<Parent Process>  Error opening Folder.\n");
                return 1;
            }

            if (arquivoData == NULL) {
                printf("\033[0;31m<Parent Process>  Error opening File.\n");
                closedir(diretorio);
                return 1;
            }

            fprintf(arquivoData, "InputPath: %s\n", fullPathInput);
            fprintf(arquivoData, "FullPathOutput: %s\n", fullPathOutput);
            if (prefixes != NULL) {
                for (int i = 0; i < MAX_CANDIDATES && prefixes[i][0] != '\0'; i++) {
                    fprintf(arquivoData, "Prefix: %s\n", prefixes[i]);
                }
            }
            fclose(arquivoData);

            printf("\033[0;31m<Parent Process>  Data File was successfully written.\n");


            shared_data->size = 0;

            for (int i = 0; prefixes[i][0] != '\0'; i++) {
               printf("\033[0;33m<Monitor Process:%d> File Name Found: %s\n", i, prefixes[i]);
               strcpy(shared_data->list[shared_data->size], prefixes[i]);
               shared_data->size++;
             }
             for (int i = 0; i < MAX_CANDIDATES; i++) {
                 free(prefixes[i]);
             }
             free(prefixes);
             sem_post(semMonitor);
          }
          sleep(check_interval);
        }
    } else {
        for (int i = 0; i < WORKER_NUMBER; ++i) {
          workers[i] = fork();
          worker_status[i] = 1;
          if (workers[i] == 0) {
             printf("\033[0;36m<Worker Process> Starting Up...\n");
             while (1) {
                 sleep(check_interval);
                 //sem_wait(semWorker[i]);
                 processFiles(fullPathInput, fullPathOutput, shared_data->list[i]);
                 worker_status[i] = 0;
             }
             exit(0);
           }
          }
        }

        while (1) {
          sem_post(semParent);
          sem_wait(semMonitor);
          for (int i = 0; i < shared_data->size; ++i) {
            printf("\033[0;31m<Parent Process> File Name Received: %s\n", shared_data->list[i]);
            int assigned = 0;
            while (!assigned) {
              for (int j = 0; j < WORKER_NUMBER; j++) {
                if (worker_status[j] == 1) {
                    worker_status[j] = 0;
                    sem_post(semWorker[j]);
                    assigned = 1;
                    break;
                }
              }
            }
          }


            while(working(worker_status)){
            }

            printf("\033[0;31m<Parent Process>  Generating Report...\n");

            DIR *diretorio;
            FILE *arquivo;
            struct dirent *entry;

            char dirFilPref[255];
            time_t current_time;
            current_time = time(NULL);
            char unixT[255];
            sprintf(unixT, "%lld", (long long int) current_time);
            char rep[255] = "report_";
            char txt[255] = ".txt";
            strcat(rep, unixT);
            strcat(rep, txt);
            arquivo = fopen(rep, "w");


            for (int i = 0; i < shared_data->size; i++) {
              strcpy(dirFilPref, fullPathOutput);
              strcat(dirFilPref, "/");
              strcat(dirFilPref, shared_data->list[i]);

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

              while ((entry = readdir(diretorio)) != NULL) {
                  if (strcmp(entry->d_name, ".") != 0 && strcmp(entry->d_name, "..") != 0) {
                      fprintf(arquivo, "%s/%s\n", dirFilPref,entry->d_name);
                  }
              }
            }
            printf("\033[0;31m<Parent Process>  Report successfully written.\n");


            closedir(diretorio);
            fclose(arquivo);

            sem_close(semParent);
            sem_unlink("/semaphoreParent");
            sem_close(semMonitor);
            sem_unlink("/semaphoreMonitor");

            for (int i = 0; i < WORKER_NUMBER; ++i) {
                sem_close(semWorker[i]);
                sem_unlink("/semaphoreWorker");
            }
            shm_unlink("/shmtest");

    }
    return EXIT_SUCCESS;
}
