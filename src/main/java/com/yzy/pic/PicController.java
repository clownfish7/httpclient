package com.yzy.pic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzy
 * @classname PicController
 * @description TODO
 * @create 2019-07-15 9:43
 */
@RestController
public class PicController {

    @Value("${filePath}")
    private String filepath;

    @RequestMapping("/fileUploadController")
    public Map<String, Object> fileUpload(MultipartFile file, String deviceId, String alarmDate) throws Exception {
        File f = new File(filepath + "\\" + deviceId + "\\" + alarmDate + "\\" + file.getOriginalFilename());
        if (!f.getParentFile().getParentFile().exists()) {
            f.getParentFile().getParentFile().mkdir();
        }
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdir();
        }

        file.transferTo(f);//文件保存
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "ok");
        return map;
    }


}
