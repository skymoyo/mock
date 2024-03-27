package work.skymoyo.mock.core.admin.service;

import work.skymoyo.mock.core.admin.model.AjaxResult;

import javax.servlet.http.HttpServletResponse;

public interface CaptchaImageService {

    /**
     * 获取图像验证吗
     *
     * @return
     * @param response
     */
    AjaxResult captchaImage(HttpServletResponse response);


}
