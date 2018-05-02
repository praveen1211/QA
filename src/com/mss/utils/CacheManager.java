/*******************************************************************************
 *
 * Project : Miracle Supply Chain Visibility portal v2.0
 *
 * Package : com.mss.mscvp.util
 *
 * Date    : Mar 11, 2013 1:43:29 PM
 *
 * Author  : Nagireddy seerapu <nseerapu@miraclesoft.com>
 *
 * File    : CacheManager.java
 *

 * *****************************************************************************
 */
package com.mss.utils;

import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

public class CacheManager {

	/*
	 * Cache to store the datasource objects for improving performance by
	 * avoided repetative calls to the JNDI registry
	 */
	private static Map<String, Object> cache;

	/** Creates a new instance of ApplicationCacheManager */
	private CacheManager() {

	}

	/**
	 * @return An instance of the Cache Map
	 * @throws ServiceLocatorException
	 */
	public static Map<String, Object> getCache() {
		try {
			if (cache == null) {
				cache = Collections
						.synchronizedMap(new HashMap<String, Object>());
			}
		} catch (Exception ex) {

		}
		return cache;
	}
}
