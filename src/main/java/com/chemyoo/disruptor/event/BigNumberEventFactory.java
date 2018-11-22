package com.chemyoo.disruptor.event;

import com.chemyoo.disruptor.BigNumber;
import com.lmax.disruptor.EventFactory;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年11月22日 下午4:24:18 
 * @since since from 2018年11月22日 下午4:24:18 to now.
 * @description class description
 */
public class BigNumberEventFactory implements EventFactory<BigNumber>{

	public BigNumber newInstance() {
		return new BigNumber();
	}

}
