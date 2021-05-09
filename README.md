# ThreadDispatcher
Задача №2 - ООП весна

Цель данного упражнения познакомить студента с особенностями многопоточного программирования в Java\С#, а также сконструировать модуль для дальнейшей работы. В рамках выполнения упражнения необходимо учесть механизмы синхронизации и обспечение монопольного доступа. Далее задача формулируется для Java.

1. Необходимо написать класс-singleton ThreadDispatcher. Данный класс представляет собой диспетчер потоков, который способен ставить задачи на параллельное выполнение. Класс содержит:

		public void Add(ThreadedTask task) - данный метод запускает на выполнение задачу в обход очереди задач.
		public void AddInQueue(ThreadedTask task) - данный метод либо немедленно запускает задачу на выполнение, либо, если нет свободных потоков, ставит в очередь. 		
		void setMaxPoolSize(int maxSize) - максимальное количество потоков, который могут обрабатывать очередь задач.
		GlobalQueue - внутренний объект-singleton диспетчера, организует работу по принципу FIFO, накапливает в себе задачи для предоставления их ThreadWorker'ам.
		ThreadWorker - обработчик очереди. Количество: 1 и более. Опрашивает очередь задач и выполняет эти задачи.	
		ThreadedTask - абстрактный базовый класс, реализующий интерфейс Runnable. Runnable - интерфейс Java, позволяющий выполнять код многопоточно. Соотвественно, чтобы создать определенную 	задачу, необходимо отнаследоваться от ThreadedTask и переопределить метод run:

	   class SleepWorker extends ThreadedTask 
	   {
		public void run()
		{
			Thread.sleep(10000);
		}
	   }

    Класс SleepWorker представляет собой пример задачи параллельного выполнения. В данном случае в отдельном потоке будет выполено тело метода run() - поток заснет на 10 секунд. Затем завершится. Таким образом, порождая	наследников ThreadedTask и переопределяя метод run() возможно создание произвольных задач для параллельного выполнения.

2. При создании объекта ThreadDispatcher необходимо в конструкторе вызвать метод:
		
		void Add(ThreadedTask task) - где аргументом task должен быть экземпляр класс ThreadMonitor, являющийся наследником ThreadedTask. То есть при создании диспетчера потоков по умолчанию сразу же создается фоновый поток. 

	ThreadMonitor - класс, реализующий функциональность монитора потоков. Необходимо с его помощью в методе run() реализовать вывод списка запущенных потоков, их идентификаторов, статусов (выполняется, в очереди и т.д). Соотвественно, как только поток завершился, список монитора потоков должен обновиться, и данный поток должен исчезнуть. При запуске потока - поток должен добавиться в вывод. Подобное поведение можно наблюдать в диспетчере задач в Windows. Либо в top linux. Монитор потоков работает столько, сколько существует singleton ThreadDispatcher. Не может отключаться или ставиться на паузу. Монитор всегда показывает актуальное состояние диспетчера потоков.

	ВАЖНО: необходимо реализовать именно отображение в реальном времени списка запущенных потоков, а не бесконечный вывод актуального состояния. 

	ВАЖНО: внешний мир взаимодействует только с диспетчером, прямого доступа к каким либо функциональным компонентам диспетчера быть не должно.
	
	ОБЯЗАТЕЛЬНО: реализовать нагрузочное тестирование. Необходимо запустить много длительных задач (не sleep). Часть мгновенно, часть через очередь, при этом размер пула должен быть достаточно большим, чтобы именно была продемонстрирована параллельная работа задач и потокобезопасность самого диспетчера.  

	*. Реализовать планировщик задач. Необходимо снабдить задачу расписанием в формате cron. Следовательно, планировщик может отдавать просто bool canStart. В противном случае задача остается в очереди, а ThreadWorker переходит к следующей задаче. Необходимо подумать о более оптимальном подходе.
