package top.felixu.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author felixu
 * @Date 2018/6/14
 */
public interface IJwtCache {

    /**
     * 获取key的缓存集合
     *
     * @param key
     * @return
     */
    List<String> getSet(String key);

    boolean putSet(String key, List<String> values);

    boolean removeSet(String key, String value);

    boolean delete(String key);

    boolean delete(Collection<String> keys);

    Set<String> keys(String pattern);
}