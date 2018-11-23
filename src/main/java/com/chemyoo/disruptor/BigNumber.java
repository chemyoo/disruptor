package com.chemyoo.disruptor;

import java.math.BigInteger;

/** 
 * @author Author : jianqing.liu
 * @version version : created time：2018年11月22日 下午4:22:17 
 * @since since from 2018年11月22日 下午4:22:17 to now.
 * @description class description
 */
public class BigNumber {
	
	private BigInteger value = BigInteger.ZERO;
	
	private long index;
	
	private long base = 1000 * 1000 * 100L;
	
	/**
	 * @return the value
	 */
	public BigInteger getValue() {
		long start = (index - 1) * base + 1;
		long end = start + base;
		for(long i = start; i < end; i ++) {
			value = value.add(BigInteger.valueOf(i));
		}
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setIndex(long index) {
		this.index = index;
	}
	
}
