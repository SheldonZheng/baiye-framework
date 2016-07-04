package com.baiye.bean;

import com.baiye.utils.CastUtil;

import java.util.Map;

/**
 * Created by Baiye on 2016/7/4.
 */
public class Param {

    private Map<String,Object> paramMap;

    public Param(Map<String,Object> paramMap)
    {
        this.paramMap = paramMap;
    }

    public long getLong(String name)
    {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String,Object> getMap()
    {
        return paramMap;
    }



}
