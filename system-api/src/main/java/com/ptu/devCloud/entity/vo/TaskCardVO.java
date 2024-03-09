package com.ptu.devCloud.entity.vo;

import com.ptu.devCloud.entity.Task;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TaskCardVO （任务看板的卡片对象）
 * @author Yang Fan
 * @since 2024/3/9 19:10
 */
@Data
public class TaskCardVO implements Serializable {
    private String title;
    private List<Task> tasks;
}
