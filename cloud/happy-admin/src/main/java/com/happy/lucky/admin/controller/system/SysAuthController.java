package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.happy.lucky.common.dto.response.LoginSuccessDto;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.common.utils.RedisUtil;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.system.dto.RequestAuthAdminLoginDto;
import com.happy.lucky.framework.service.ISysAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author songyangpeng
 */
@Api(tags = "后台验证模块")
@RestController
@RequestMapping("/sys-auth")
public class SysAuthController {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysAuthService sysAuthService;

    /**
     * 图片验证码
     */
    @ApiOperation(value = "获取图片验证码", notes = "图片验证码格式为base64位")
    @GetMapping("/captcha")
    public R<Map<Object, Object>> captcha() throws IOException {
        String text = producer.createText();

        //个位数字相加
        String s1 = text.substring(0, 1);
        String s2 = text.substring(1, 2);
        int code = Integer.parseInt(s1) + Integer.parseInt(s2);

        //生成图片验证码
        BufferedImage image = producer.createImage(s1 + "+" + s2 + "=?");

        String key = UUID.randomUUID().toString();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());
        // 存储到redis中
        redisUtil.hset(Const.CAPTCHA_KEY, key, code, 120);
        logger.info("验证码 -- {} - {}", key, code);

        return R.success(MapUtil.builder().put("token", key).put("base64Img", base64Img).build());
    }

    @OperLog(operModul = "验证模块 - 管理员后台登录", operType = Const.ADMIN_LOGIN, operDesc = "管理员登录系统")
    @ApiOperation(value = "后台管理员登录系统", notes = "后台管理员进行管理系统登录")
    @PostMapping("/admin/doLogin")
    public R<LoginSuccessDto> doLogin(@Validated @RequestBody RequestAuthAdminLoginDto dto) {
        return R.success(sysAuthService.doAdminLogin(dto));
    }

    @OperLog(operModul = "验证模块 - 管理员后台退出", operType = Const.ADMIN_LOGOUT, operDesc = "管理员推出系统")
    @ApiOperation(value = "后台管理员推出系统", notes = "后台管理员进行管理系统推出")
    @SaCheckLogin
    @GetMapping("/admin/logout")
    public R doLogout() {
        sysAuthService.doAdminLogout();
        return R.success();
    }

    public R<Object> test() {
        System.out.println("hello,world");
        return R.success("hello,world");
    }
}
