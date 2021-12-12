package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberCounting {
    private static final Logger logger = LoggerFactory.getLogger(NumberCounting.class);
    // номер текущего потока
    private int currentThreadCount= 1;

    // общее количество потоков
    private static final int TOTAL_THREAD_COUNT= 2;
    // Число, до которого нужно поссчитать
    private static final int MAX_COUNTING_VALUE = 10;

    private synchronized void numberCount(int priority) {
        int currentNum = 0;
        int lastNum = 0;
        int higherNum = 0;
        // проверка флага isInterrupted
        while(!Thread.currentThread().isInterrupted()) {
            try {
                // приоритет должен быть равен номеру текущего потока(while - защита от spurious wakeup)
                while (priority != currentThreadCount) {
                    this.wait();
                }
                if (lastNum < MAX_COUNTING_VALUE && higherNum < MAX_COUNTING_VALUE) {
                    // Инкрементируем значение
                    currentNum++;
                    higherNum++;
                } else {
                    // Декриментируем значение
                    currentNum--;
                }
                logger.info(String.valueOf(currentNum));
                lastNum = currentNum;
                notifyAll();
                // если полный цикл сделали - остановим поток(выставим флаг для отсановки потока)
                if (currentNum <= 1 && higherNum >= MAX_COUNTING_VALUE) {
                    Thread.currentThread().interrupt();
                }
                // если запустился первый в приоритете поток - сбрасываем количество текущих потоков
                currentThreadCount = priority == TOTAL_THREAD_COUNT ? 1 : currentThreadCount+1;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args){
        NumberCounting numberCounting = new NumberCounting();
        new Thread(() -> numberCounting.numberCount(1)).start();
        new Thread(() -> numberCounting.numberCount(2)).start();
    }
}
