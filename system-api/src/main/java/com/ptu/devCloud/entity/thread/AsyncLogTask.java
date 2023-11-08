package com.ptu.devCloud.entity.thread;

import com.ptu.devCloud.entity.MethodLog;
import com.ptu.devCloud.service.MethodLogService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AsyncLogTask extends ThreadTask{

    private MethodLogService methodLogService;

    private MethodLog methodLog;

    public AsyncLogTask(MethodLog methodLog, MethodLogService methodLogService){
        super.id = UUID.randomUUID().toString();
        super.name = "AsyncLogTask";
        this.methodLogService = methodLogService;
        this.methodLog = methodLog;
    }

    @Override
    public Object doTask() {
        if(this.methodLog != null) {
            methodLogService.insertIgnoreNull(this.methodLog);
        }
        return null;
    }
}
