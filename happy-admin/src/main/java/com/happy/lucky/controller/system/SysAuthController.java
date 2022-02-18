package com.happy.lucky.controller.system;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.common.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Api(tags = "后台验证模块")
@RestController
@RequestMapping("/sys-auth")
public class SysAuthController {

    private static final Logger logger = LoggerFactory.getLogger(SysAuthController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 图片验证码
     */
    @ApiOperation(value = "获取图片验证码", notes = "图片验证码格式为base64位")
    @GetMapping("/captcha")
    public R<Map<Object, Object>> captcha() throws IOException {
        String code = producer.createText();
        String key = UUID.randomUUID().toString();
        BufferedImage image = producer.createImage(code);
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

    public R<Object> test() {
        System.out.println("hello,world");
        return R.success("hello,world");
    }
}
