package priv.wmc.pojo.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import priv.wmc.enums.GenderEnum;
import priv.wmc.pojo.entity.User;

/**
 * @author Wang Mincong
 * @date 2020-07-23 09:59:42
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResult {

    /** 主键id */
    private Long id;

    /** 姓名 */
    private String username;

    /** 性别 */
    private GenderEnum gender;

    /** 生日 */
    private LocalDateTime birthday;

    /** 地址 */
    private String address;

    /** 创建时间 */
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    private LocalDateTime gmtModified;

    public UserResult(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public UserResult(
        @Param("id") Long id,
        @Param("username") String username,
        @Param("gender") GenderEnum gender,
        @Param("birthday") LocalDateTime birthday,
        @Param("address") String address,
        @Param("gmtCreate") LocalDateTime gmtCreate,
        @Param("gmtModified") LocalDateTime gmtModified) {

        this.id = id;
        this.username = username;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

}
