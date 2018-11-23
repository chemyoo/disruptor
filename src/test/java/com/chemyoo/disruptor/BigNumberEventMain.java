
package com.chemyoo.disruptor;
 
import com.chemyoo.disruptor.event.BigNumberEventFactory;
import com.chemyoo.disruptor.event.BigNumberEventHandler;
import com.chemyoo.disruptor.event.BigNumberEventProducer;
import com.chemyoo.monitor.TimeMonitor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
 
/**
 * Created by BaiTianShi on 2018/8/25.
 */
public class BigNumberEventMain {
	
	static Logger logger = Logger.getLogger(BigNumberEventMain.class.getName());
 
    public static void main(String[] args) {
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建longEvent工厂
        BigNumberEventFactory factory = new BigNumberEventFactory();
 
        //设置ringBuffer大小，必须为2的n指数被，这样才能通过位运算，保证速度
        int ringBufferSize = 1024 * 1024;
 
        //最后一个参数为指定声明disruptor的策略
        //BlockingWaitStrategy  最低效的策略，但其对cpu4de消耗最小，并且在各种环境下能提供更加一致的性能表现
        //SleepingWaitStrategy 性能和BlocklingWaitStrategy性能差不多，对cpu消耗也相同，但其对生产者现场影响最小，适合用于异步日志类似的场景
        //YieldingWaitStrategy 性能是最好的，适合用于递延次的系统中，在要求极高性能且事件处理线数小于cpu逻辑核心数的场景中，推荐使用。例如:cpu开启超线程的特性
 
        //创建disruptor， longEvent为指定的消费数据类型，即每个事件槽中存放的数据类型
        Disruptor<BigNumber> disruptor = new Disruptor<BigNumber>(factory,ringBufferSize,executorService,ProducerType.SINGLE, new YieldingWaitStrategy());
 
        //连接消费事件
        BigNumberEventHandler eventHandler = new BigNumberEventHandler();
        disruptor.handleEventsWith(eventHandler);
        //启动
        disruptor.start();
 
        //获取具体的存放数据的ringBuffer
        RingBuffer<BigNumber> ringBuffer = disruptor.getRingBuffer();
 
        BigNumberEventProducer producer = new BigNumberEventProducer(ringBuffer);
        long end = 1;
        ByteBuffer buf = ByteBuffer.allocate(8);
        TimeMonitor monitor = new TimeMonitor();
        monitor.timeStart("disruptor");
        for(long i = 1; i <= end; i++){
            buf.putLong(0,i);
            producer.onData(buf);
        }
        
        disruptor.shutdown();//关闭disruptor，方法会阻塞，直到所有事件都得到处理
        logger.info("disruptor = " + eventHandler.getSummary());
        monitor.timeEnd();
        executorService.shutdown();//关闭线程池，如果需要的话必须手动关闭，disruptor在shutdown时不会自动关闭线程池
    }

}