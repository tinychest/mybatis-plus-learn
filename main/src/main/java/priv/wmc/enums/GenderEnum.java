package priv.wmc.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import priv.wmc.base.EnumDefine;

/**
 * @author Wang Mincong
 * @date 2020-08-06 15:29:39
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements EnumDefine {

    /** 未知 */
    UNKNOWN(0, "未知"),
    /** 男 */
    MALE(1, "男"),
    /** 女 */
    FEMALE(2, "女");

    int value;

    String verbose;
}
