package com.chemyoo.disruptor.event;

import java.math.BigInteger;

import com.chemyoo.disruptor.BigNumber;
import com.lmax.disruptor.EventHandler;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年11月22日 下午4:31:52 
 * @since since from 2018年11月22日 下午4:31:52 to now.
 * @description class description
 */
public class BigNumberEventHandler implements EventHandler<BigNumber> {
	
	private BigInteger summary = BigInteger.ZERO;
	
	public void onEvent(BigNumber event, long sequence, boolean endOfBatch) throws Exception {
		summary = summary.add(event.getValue());
	}

	public BigInteger getSummary() {
		return this.summary;
	}
}
