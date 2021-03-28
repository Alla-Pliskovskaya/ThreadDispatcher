package p1;

public abstract class ThreadedTask implements Runnable
{
    protected long id;
    protected String status;

    public String getName()
    {
        return this.getClass().getName();
    }

    public abstract void start();

    @Override
    public void run() {
        try {
            status = "running";
            id = Thread.currentThread().getId();
            ThreadDispatcher.getMonitor().update();
            start();
            ThreadDispatcher.threadedTask.remove(this);
            ThreadDispatcher.getMonitor().update();
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }
}