package com.itmuch.usercenter.domain.entity.bonus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "bonus_event_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BonusEventLog {
    /**
     * Id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * user.id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * ���ֲ���ֵ
     */
    private Integer value;

    /**
     * �������¼�
     */
    private String event;

    /**
     * ����ʱ��
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * ����
     */
    private String description;

    /**
     * ��ȡId
     *
     * @return id - Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * ����Id
     *
     * @param id Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ��ȡuser.id
     *
     * @return user_id - user.id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * ����user.id
     *
     * @param userId user.id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * ��ȡ���ֲ���ֵ
     *
     * @return value - ���ֲ���ֵ
     */
    public Integer getValue() {
        return value;
    }

    /**
     * ���û��ֲ���ֵ
     *
     * @param value ���ֲ���ֵ
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * ��ȡ�������¼�
     *
     * @return event - �������¼�
     */
    public String getEvent() {
        return event;
    }

    /**
     * ���÷������¼�
     *
     * @param event �������¼�
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * ��ȡ����ʱ��
     *
     * @return create_time - ����ʱ��
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * ���ô���ʱ��
     *
     * @param createTime ����ʱ��
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * ��ȡ����
     *
     * @return description - ����
     */
    public String getDescription() {
        return description;
    }

    /**
     * ��������
     *
     * @param description ����
     */
    public void setDescription(String description) {
        this.description = description;
    }
}