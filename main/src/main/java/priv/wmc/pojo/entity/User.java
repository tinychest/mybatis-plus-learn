package priv.wmc.pojo.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import priv.wmc.base.BaseEntity;
import priv.wmc.enums.GenderEnum;

/**
 * 用户实体
 *
 * @author Wang Mincong
 * @date 2020-07-20 11:43:09
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {

    /** 姓名 */
    private String username;

    /** 性别 */
    private GenderEnum gender;

    /** 生日 */
    private LocalDateTime birthday;

    /** 地址 */
    private String address;

}
