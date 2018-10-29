package com.pq.api.web.context;

/**
 * 隐身模式
 * <p/>
 * 这个是用于内部日志打印时，是否会日志隐身，因为我们不能够暴露用户的 账户信息在
 * 日志中，跟 {@link java.io.Serializable} 一样，只是一个标记性API。
 */
public interface Invisiable {

}
