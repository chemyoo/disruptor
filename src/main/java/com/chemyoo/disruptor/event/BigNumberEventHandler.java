package com.chemyoo.disruptor.event;

import com.chemyoo.disruptor.BigNumber;
import com.lmax.disruptor.EventHandler;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年11月22日 下午4:31:52 
 * @since since from 2018年11月22日 下午4:31:52 to now.
 * @description class description
 */
public class BigNumberEventHandler implements EventHandler<BigNumber> {

	public void onEvent(BigNumber event, long sequence, boolean endOfBatch) throws Exception {
		System.err.println(event.getValue());
	}

}
