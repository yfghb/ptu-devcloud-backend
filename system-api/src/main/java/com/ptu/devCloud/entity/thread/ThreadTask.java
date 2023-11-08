package com.ptu.devCloud.entity.thread;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Slf4j
public abstract class ThreadTask implements Runnable, Serializable {

    /** 唯一id */
    protected String id;

    /** 线程任务名 */
    protected String name;

    /** 执行状态 (null:执行中, true:成功, false:失败) */
    protected Boolean status = null;

    /** 执行结果 */
    protected Object result;

    protected abstract Object doTask();

    @Override
    public void run() {
        log.info("[" + this.name + "] " + this.id + " is running.");
        try {
            this.setResult(this.doTask());
            this.setStatus(true);
        }
        catch (Exception e){
            this.setStatus(false);
            log.error("[" + this.name + "] " + this.id + " run fail\n" + e);
        }
    }
}
