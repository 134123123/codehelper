package codehelper.model.dto;

import lombok.Data;

/**
 * 备忘录更新数据传输对象
 * 用于接收前端传递的备忘录更新参数，支持部分字段更新
 */
@Data
public class MemoUpdateDTO {
    /**
     * 备忘录ID（必传，用于定位待更新的备忘录）
     */
    private Long id;

    /**
     * 新标题（可选，若传递则更新标题）
     */
    private String title;

    /**
     * 新内容（可选，若传递则更新内容）
     */
    private String content;

    /**
     * 完成状态（可选，0-未完成，1-已完成）
     */
    private Integer isCompleted;

    /**
     * 优先级（可选，1-高，2-中，3-低）
     */
    private Integer priority;

    /**
     * 提醒时间（可选，格式：yyyy-MM-dd HH:mm:ss）
     */
    private String reminderTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }
}