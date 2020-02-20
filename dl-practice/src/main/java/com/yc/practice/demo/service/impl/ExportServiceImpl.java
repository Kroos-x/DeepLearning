package com.yc.practice.demo.service.impl;

import com.yc.common.utils.WordUtils;
import com.yc.practice.demo.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：
 * <p>版权所有：</p>
 * 未经本公司许可，不得以任何方式复制或使用本程序任何部分
 *
 * @Company: 紫色年华
 * @Author: xieyc
 * @Datetime: 2020-02-16
 * @Version: 1.0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Override
    public void export(HttpServletResponse response) {
        Map<String,Object> map = this.dealExportData();
        try{
            WordUtils.exportWord(response,map,"企业信息","companyInfo.ftl");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理导出数据  ========== export 子方法 ==========
     * @return map
     */
    private Map<String,Object> dealExportData(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("comunityName","园区名称[蓝海软件园]");
        map.put("rentAddr","蓝海路1号蓝海软件园D座10层1001");
        map.put("rentTimeLimit","2019-01-01至2020-10-10");
        return map;
    }
}
