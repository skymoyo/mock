package work.skymoyo.mock.core.admin.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import work.skymoyo.mock.core.admin.model.AjaxResult;
import work.skymoyo.mock.core.admin.service.CacheService;
import work.skymoyo.mock.core.admin.service.CaptchaImageService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Service
public class CaptchaImageServiceImpl implements CaptchaImageService {

    @Autowired
    private CacheService cacheService;

    @Override
    public AjaxResult captchaImage(HttpServletResponse response) {
        AjaxResult ajax = new AjaxResult();

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50);

        try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
            ImageIO.write(lineCaptcha.getImage(), "jpg", os);
            ajax.put("img", Base64.encode(os.toByteArray()));
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }


        String uuid = UUID.randomUUID().toString();
//        cacheService.setString("CaptchaImage:" + uuid, lineCaptcha.getCode());
        ajax.put("uuid", uuid);

        return ajax;
    }
}
