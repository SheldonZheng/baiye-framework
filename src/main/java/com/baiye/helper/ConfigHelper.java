package com.baiye.helper;

import com.baiye.utils.ConfigConstant;
import com.baiye.utils.PropsUtil;

import java.util.Properties;

/**
 * Created by Baiye on 2016/7/1.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJDBCDriver()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVE,"");
    }

    public static String getJDBCUrl()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL,"");
    }

    public static String getJDBCUsername()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME,"");
    }

    public static String getJDBCPassword()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD,"");
    }

    public static String getAppBasePackage()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE,"com.baiye.Test");
    }

    public static String getAppJspPath()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }

    public static String getAppAssetPath()
    {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH,"/asset/");
    }

    public static void init()
    {
        return;
    }

}
