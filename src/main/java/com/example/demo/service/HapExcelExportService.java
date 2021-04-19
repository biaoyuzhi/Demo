package com.example.demo.service;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.demo.excel.util.ResponseData;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jialong.zuo@hand-china.com on 2017/11/20.
 */
@Component
public class HapExcelExportService {
    private static final Logger logger = LoggerFactory.getLogger(HapExcelExportService.class);

    /**
     * 入参样例：
     * _HAP_EXCEL_EXPORT_COLUMNS = [{"prompt":"行号","column":"lineNumber"},{"prompt":"描述","column":"description"}]
     * fileName = demo
     */
    public void exportAndDownloadExcel(ResponseData rd,
                                       HttpServletRequest request,
                                       HttpServletResponse response,
                                       List<String> columns,
                                       List<String> prompts,
                                       boolean isHSSF,
                                       boolean resultIsMap,
                                       String fileName) {
        try {
            //获得entityList，表列名和列变量名的对应关系
            List<ExcelExportEntity> entityList = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                entityList.add(new ExcelExportEntity(prompts.get(i), columns.get(i)));
            }

            //获得数据集合，map为列变量名和值的对应关系
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Object item : rd.getRows()) {
                Map<String, Object> valMap = new HashMap<>();
                if (resultIsMap) {
                    columns.forEach(column -> {
                        if (((Map) item).containsKey(column)) {
                            valMap.put(column, ((Map) item).get(column));
                        }
                    });
                } else {
                    Map dtoMap = BeanUtils.describe(item);
                    columns.forEach(column -> {
                        if (dtoMap.containsKey(column)) {
                            valMap.put(column, dtoMap.get(column));
                        }
                    });
                }
                mapList.add(valMap);
            }

            //使用easyPoi的easypoiMapExcelView实现导出
            ExportParams params = new ExportParams();
            if (isHSSF) {
                params.setType(ExcelType.HSSF);                 //.xls
            } else {
                params.setType(ExcelType.XSSF);                 //.xlsx
            }
            ModelMap model = new ModelMap();
            model.put(NormalExcelConstants.PARAMS, params);
            model.put(MapExcelConstants.ENTITY_LIST, entityList);
            model.put(MapExcelConstants.MAP_LIST, mapList);
            model.put(NormalExcelConstants.FILE_NAME, fileName);
            PoiBaseView.render(model, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
        } catch (Exception ex) {
            logger.error("excel文件导出失败", ex);
            throw new RuntimeException(fileName + "，excel文件导出失败");
        }

    }

}
