package work.skymoyo.mock.core.admin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import work.skymoyo.mock.common.model.MockDataBo;
import work.skymoyo.mock.common.model.MockReq;
import work.skymoyo.mock.core.admin.model.*;
import work.skymoyo.mock.core.admin.service.CaptchaImageService;
import work.skymoyo.mock.core.resource.dao.MockConfigDao;
import work.skymoyo.mock.core.resource.dao.MockRecordDao;
import work.skymoyo.mock.core.resource.entity.MockConfig;
import work.skymoyo.mock.core.resource.entity.MockRecord;
import work.skymoyo.mock.core.service.MockService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class MockAdminController extends BaseController {

    @Autowired
    private CaptchaImageService captchaImageService;
    @Autowired
    private MockService mockService;
    @Autowired
    private MockRecordDao recordDao;
    @Autowired
    private MockConfigDao mockConfigDao;


    @GetMapping(value = "/server/index")
    public String serverIndex(ModelMap mmap) throws Exception {
        Server server = new Server();
        server.copyTo();
        mmap.put("server", server);
        return "mock/server";
    }

    @ResponseBody
    @GetMapping(value = "/captchaImage", produces = {"application/json"})
    public AjaxResult captchaImage(HttpServletRequest request, HttpServletResponse response) {
        return captchaImageService.captchaImage(response);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginIndex() {
        return "login";
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public AjaxResult login(@RequestParam(value = "username", required = false) String username,
                            @RequestParam(value = "password", required = false) String password,
                            ModelMap mmap) {

        try {
            MockReq req = new MockReq();
            req.setRoute("/admin/login");

            HashMap<String, Object> map = new HashMap<>(2);
            map.put("username", username);
            map.put("password", password);
            req.setData(map);
//            MockDataBo mock = mockService.mock(req);
//
//            mmap.put("token", "todo");

            MockReq userReq = new MockReq();
            userReq.setRoute("/admin/getInfo");
            JSONObject user = JSON.parseObject(mockService.mock(userReq).getData()).getJSONObject("user");
            mmap.put("user", JSON.parseObject(user.toJSONString(), SysUser.class));

            return AjaxResult.success("成功", "todo token");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, ModelMap mmap) {
//        String token = request.getHeader("token");
//        if (!StringUtils.hasLength(token)) {
//            mmap.put("captchaEnabled", false);
//            return "login";
//        }

        // 取身份信息
        MockReq req = new MockReq();
        req.setRoute("/admin/getInfo");
        MockReq router = new MockReq();
        router.setRoute("/admin/getRouters");
        // 根据用户id取出菜单
        JSONArray data = JSON.parseObject(mockService.mock(router).getData()).getJSONArray("data");
        List<SysMenu> menus = JSON.parseArray(data.toJSONString(), SysMenu.class);
        mmap.put("menus", menus);
        JSONObject user = JSON.parseObject(mockService.mock(req).getData()).getJSONObject("user");
        mmap.put("user", JSON.parseObject(user.toJSONString(), SysUser.class));
        return "index";
    }

    @GetMapping(value = "/system/main")
    public String main(ModelMap mmap) {
        mmap.put("version", "0.1.1");
        return "main";
    }


    @GetMapping(value = "/record/index")
    public String recordIndex(ModelMap mmap) {
        mmap.put("version", "0.1.1");
        return "/mock/record";
    }

    @GetMapping(value = "/config/index")
    public String configIndex(ModelMap mmap) {
        mmap.put("version", "0.1.1");
        return "/mock/mockConfig";
    }


    @ResponseBody
    @PostMapping("/record/list")
    public TableDataInfo list(MockRecord mockRecord) throws Exception {
        startPage();
        List<MockRecord> list = recordDao.queryAll(mockRecord);
        return getDataTable(list);
    }

    @ResponseBody
    @PostMapping("/config/list")
    public TableDataInfo configList(MockConfig config) throws Exception {
        startPage();
        List<MockConfig> list = mockConfigDao.queryAll(config);
        return getDataTable(list);
    }

    @GetMapping(value = "/config/detail/{id}")
    public String configIndex(@PathVariable("id") long id, ModelMap mmap) {
        MockConfigVO mockConfig = mockConfigDao.selectVO(id);

        mmap.put("mockConfig", mockConfig);
        return "mock/mockConfigEdit";
    }

    @GetMapping(value = "/config/test/{id}")
    public String configTest(@PathVariable("id") long id, ModelMap mmap) {
        MockConfigVO mockConfig = mockConfigDao.selectVO(id);
        mmap.put("mockConfig", mockConfig);

        return "mock/mockConfigTest";
    }


    @ResponseBody
    @RequestMapping(value = "/mockTest", method = RequestMethod.POST)
    public AjaxResult mockTest(@RequestBody AdminMockReq adminMockReq) {
        try {
            MockReq req = new MockReq();
            req.setClient("admin");
            req.setRoute(adminMockReq.getRoute());
            String head = adminMockReq.getHead();
            if (StringUtils.hasLength(head)) {
                Map<String, Object> map = JSON.parseArray(head, Map.class)
                        .stream()
                        .collect(Collectors.toMap(d -> String.valueOf(d.get("dataKey")), d -> d.get("dataValue")));
                req.setHead(map);
            }
            String data = adminMockReq.getData();
            if (StringUtils.hasLength(data)) {
                Map<String, Object> map = JSON.parseArray(data, Map.class)
                        .stream()
                        .collect(Collectors.toMap(d -> String.valueOf(d.get("dataKey")), d -> d.get("dataValue")));
                req.setData(map);
            }
            MockDataBo mock = mockService.mock(req, JSON.parseArray(adminMockReq.getRuleListStr(), MockRuleVO.class));
            return AjaxResult.success("成功", mock.getData());
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }


}
