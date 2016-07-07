package com.baiye.proxy;

/**
 * Created by Baiye on 2016/7/6.
 */
public interface Proxy {


    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
