package com.chemyoo.disruptor.event;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import com.chemyoo.disruptor.BigNumber;
import com.lmax.disruptor.RingBuffer;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年11月22日 下午4:27:50 
 * @since since from 2018年11月22日 下午4:27:50 to now.
 * @description class description
 */
public class BigNumberEventProducer {
	
	private final RingBuffer<BigNumber> ringBuffer;
	 
    public BigNumberEventProducer(RingBuffer<BigNumber> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    
    public void onData(ByteBuffer buffer){
        //1.可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
 
        try {
            //2.用上面的索引取出一个空事件用于填充（获取该序号对应的事件对象）
        	BigNumber event = ringBuffer.get(sequence);
            //3.获取要通过对象传递的业务数据
            event.setIndex(buffer.getLong(0));
        } catch (Exception e) {
            // none
        } finally {
            //4.发布事件
            //注意，最后的ringBuffer.publisth一定要包含在finally中，以确保必须得到调用
            //某个请求的sequence未被提交，将会阻塞后续的发布操作或者其他的producer
            ringBuffer.publish(sequence);
        }
    }


}
