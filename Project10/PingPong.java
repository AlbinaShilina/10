public class PingPong
{
    public static void main(String[] args)// главная функция
    {
        Object lock = new Object();  //  lock будет необходим для подачи доступа в метод потокам.
        Thread ping = new Thread(new PingPongThread(lock, "Ping"));
        Thread pong = new Thread(new PingPongThread(lock, "Pong"));
        ping.start();  // Запуск потока "ping".
        pong.start();  // Запуск потока "pong".
    }
}


class PingPongThread implements Runnable // implements - тоже самое,
// что extends - Указывает несколько интерфейсов, которые должны быть реализованы в определении класса или структуры,
// в котором они отображаются.
{
    private Object lock;  // Объявление поля lock.
    private String name;  // Объявление поля name.

    public PingPongThread(Object lock, String name)  // Конструктор
    {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run()  // Метод для потока, который запустится после запуска потока методом start().
    {
        synchronized (lock)  // Данный блок может выполняться только одним потоком одновременно.
        // Синхронизация(позволяет двум устройствам или программам работать совместно)
        {
            while(true)
            {
                System.out.println(name);  // Вывод текущего имени
                try  // Если нет исключений:
                // try – определяет блок кода, в котором может произойти исключение
                {
                    Thread.sleep(1000);  //Пауза потока на 1 секунду
                }
                catch (InterruptedException e)  // Если есть исключение:
                // catch – определяет блок кода, в котором происходит обработка исключения;
                {
                    e.printStackTrace();  // Указание той строки, в которой метод вызвал данное исключение (назавние метода)
                }
                lock.notify();  // Продолжение работы  потока, у которого был вызван метод wait().
                try  // Если нет исключений:
                {
                    lock.wait(1000);  // Пауза потока на 1 секунду или установка в режим ожидания,
                    // пока другим потоком не будет вызван метод notify().
                }
                catch (InterruptedException e)  // Если есть исключения:
                {
                    e.printStackTrace();  // Указание той строки, в которой метод вызвал данное исключение (название метода)
                }
            }
        }
    }
}