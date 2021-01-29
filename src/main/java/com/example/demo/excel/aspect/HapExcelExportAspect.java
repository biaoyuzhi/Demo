package com.example.demo.excel.aspect;

import com.example.demo.excel.annotation.ExcelExport;
import com.example.demo.excel.util.ResponseData;
import com.example.demo.service.HapExcelExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
public class HapExcelExportAspect {
    @Autowired
    private HapExcelExportService hapExcelExportService;

    /**
     * @param excelExport   捕获指定的注解
     * @param responseData  捕获指定的返回值类型
     */
    @AfterReturning(value = "@annotation(excelExport)", returning = "responseData")
    public void afterController(JoinPoint joinpoint, ExcelExport excelExport, ResponseData responseData) throws Exception {
        //下面对请求体和响应体来处理，所以接口处入参一定要添加HttpServletRequest request, HttpServletResponse response，否则会抛空指针
        HttpServletRequest httpServletRequest = null;
        HttpServletResponse httpServletResponse = null;
        //拿到接口处的请求体和响应体
        for (int i = 0; i < joinpoint.getArgs().length; i++) {
            if (joinpoint.getArgs()[i] instanceof HttpServletRequest) {
                httpServletRequest = (HttpServletRequest) joinpoint.getArgs()[i];
            } else if (joinpoint.getArgs()[i] instanceof HttpServletResponse) {
                httpServletResponse = (HttpServletResponse) joinpoint.getArgs()[i];
            }
        }
        /**
         * 入参样例：
         * _HAP_EXCEL_EXPORT_COLUMNS = [{"prompt":"行号","column":"lineNumber"},{"prompt":"描述","column":"description"}]
         * fileName = demo
         */
        //拿到请求体中的指定key
        if (Objects.requireNonNull(httpServletRequest).getParameterMap().containsKey("_HAP_EXCEL_EXPORT_COLUMNS")) {
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            String columnsJson = parameterMap.get("_HAP_EXCEL_EXPORT_COLUMNS")[0];

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode node = mapper.readValue(columnsJson, ArrayNode.class);
            List<String> columns = new ArrayList<>();
            List<String> prompts = new ArrayList<>();
            node.forEach(v -> {
                columns.add(v.get("column").asText());
                prompts.add(v.get("prompt").asText());
            });
            boolean isHSSF;         //true 为.xls，false 为.xlsx
            if (parameterMap.containsKey("way")) {
                isHSSF = "HSSF".equalsIgnoreCase(parameterMap.get("way")[0]);
            } else {
                isHSSF = false;
            }

            String fileName;
            if (parameterMap.containsKey("fileName")) {
                fileName = parameterMap.get("fileName")[0];
            } else {
                fileName = System.currentTimeMillis() + "";
            }
            //下面调用excel导出方法
            boolean isMap = !responseData.getRows().isEmpty() && responseData.getRows().get(0) instanceof Map;
            hapExcelExportService.exportAndDownloadExcel(responseData, httpServletRequest, httpServletResponse,
                    columns, prompts, isHSSF, isMap, fileName);
        }
    }

}
