us2001b: us2001b.o
	gcc us2001b.o -o us2001b -lpthread -lrt


us2001b.o: us2001b.c
	gcc -g -Wall -Wextra -c us2001b.c -o us2001b.o

# run: us2001b clean
# 	./us2001b
# 	rm -f us2001b

battery: battery.o
	gcc battery.o -o battery

battery.o: battery.c
	gcc -g -Wall -Wextra -fanalyzer -c battery.c -o battery.o

bat: battery us2001b clean
	./battery
	#rm -f us2001b
#
# tests: tests.o
# 	gcc tests.o -o tests
#
# tests.o: tests.c
# 	gcc -g -Wall -Wextra -c tests.c -o tests.o

run: us2001b
	./us2001b
	rm -f us2001b


#
#
#us2001b_main.o: us2001b_main.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001b_main.c -o us2001b_main.o
#
#us2001b_worker.o: us2001b_worker.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001b_worker.c -o us2001b_worker.o
#
#us2001b_monitor.o: us2001b_monitor.c
#	gcc -g -Wall -Wextra -fanalyzer -c us2001b_monitor.c -o us2001b_monitor.o
#
#us2001b_main: us2001b_main.o
#	gcc us2001b_main.o -o us2001b_main
#
#us2001b_worker: us2001b_worker.o
#	gcc us2001b_worker.o -o us2001b_worker
#
#us2001b_monitor: us2001b_monitor.o
#	gcc us2001b_monitor.o -o us2001b_monitor
#
#run_s: us2001b_monitor us2001b_worker us2001b_main clean
#	./us2001b_main
#	rm -f us2001b_main
#	rm -f us2001b_monitor
#	rm -f us2001b_worker
#
#run_mon: us2001b_monitor clean
#	./us2001b_monitor
#	rm -f us2001b_monitor

clean:
	rm -f *.o
