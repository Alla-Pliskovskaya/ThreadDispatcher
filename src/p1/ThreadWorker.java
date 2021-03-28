package p1;

public class ThreadWorker implements Runnable
{
    public void run()
    {
        synchronized (ThreadDispatcher.getInstance())
        {
            ThreadDispatcher.workerCount++;
        }
        while (ThreadDispatcher.workerCount <= ThreadDispatcher.maxSize)
        {
            ThreadedTask task;
            synchronized (ThreadDispatcher.getInstance())
            {
                task = ThreadDispatcher.GlobalQueue.getInstance().remove();
            }
            if (task != null)
            {
                task.run();
            }
        }
        synchronized (ThreadDispatcher.getInstance())
        {
            ThreadDispatcher.workerCount--;
        }
    }
}
