us2001: us2001.o
	gcc us2001.o -o us2001


us2001.o: us2001.c
	gcc -g -Wall -Wextra -c us2001.c -o us2001.o

run: us2001 clean
	./us2001
	rm -f us2001

battery: battery.o
	gcc battery.o -o battery

battery.o: battery.c
	gcc -g -Wall -Wextra -fanalyzer -c battery.c -o battery.o

bat: battery us2001 clean
	./battery
	#rm -f us2001

#
#
#us2001_main.o: us2001_main.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001_main.c -o us2001_main.o
#
#us2001_worker.o: us2001_worker.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001_worker.c -o us2001_worker.o
#
#us2001_monitor.o: us2001_monitor.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001_monitor.c -o us2001_monitor.o
#
#us2001_main: us2001_main.o
#	gcc us2001_main.o -o us2001_main
#
#us2001_worker: us2001_worker.o
#	gcc us2001_worker.o -o us2001_worker
#
#us2001_monitor: us2001_monitor.o
#	gcc us2001_monitor.o -o us2001_monitor
#
#run_s: us2001_monitor us2001_worker us2001_main clean
#	./us2001_main
#	rm -f us2001_main
#	rm -f us2001_monitor
#	rm -f us2001_worker
#
#run_mon: us2001_monitor clean
#	./us2001_monitor
#	rm -f us2001_monitor

clean:
	rm -f *.o

