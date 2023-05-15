package uk.ncl.CSC8016.jackbergus.coursework.project2.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ncl.CSC8016.jackbergus.coursework.project2.processes.RainforestShop;

import java.util.concurrent.atomic.AtomicBoolean;

public class RainforestShopTest {
    private RainforestShop shop;

    @BeforeEach
    public void setup() {
        // 在每个测试方法运行之前初始化 RainforestShop
        shop = new RainforestShop(null, null, false);
    }

    @Test
    public void testGetNextMissingItem() throws InterruptedException {
        // 测试 getNextMissingItem() 方法

        // 创建一个线程来调用 getNextMissingItem() 方法
        Thread thread = new Thread(() -> {
            String item = shop.getNextMissingItem();
            // 确保成功从 currentEmptyItem 队列中取出了一个元素
            Assertions.assertEquals("@stop!", item);
        });

        // 启动线程
        thread.start();

        // 等待一段时间以确保线程进入阻塞状态
        Thread.sleep(1000);

        // 停止供应商并通知等待的线程
        shop.stopSupplier();
        shop.supplierStopped(new AtomicBoolean(false));

        // 等待线程执行完毕
        thread.join();
    }
}
