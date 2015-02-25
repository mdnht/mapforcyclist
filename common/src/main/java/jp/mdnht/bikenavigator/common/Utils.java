package jp.mdnht.bikenavigator.common;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by maeda on 2015/02/25.
 */
public class Utils {
    /**
     * As simple wrapper around Log.d
     */
    public static void LOGD(String tag, String message) {
        //Log.d("aaaa", (String)getBuildConfigValue("BUILD_TYPE"));
        //if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            Log.d(tag, message);
        //}
    }

    public static Object getBuildConfigValue(String fieldName) {
        try {
            Class<?> clazz = Class.forName(Utils.class.getPackage().getName() + ".BuildConfig");
            Field field = clazz.getField(fieldName);
            return field.get(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
