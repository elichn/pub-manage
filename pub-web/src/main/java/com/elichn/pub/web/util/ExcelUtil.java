package com.elichn.pub.web.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Map;

/**
 * <p>Title: ExcelUtil</p>
 * <p>Description: Excel工具类</p>
 *
 * @author elichn
 * @version 1.0
 * @date 2017/11/3
 */
public class ExcelUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * buildExcelByTemplate buildExcelByTemplate
     *
     * @param template     template
     * @param data         data
     * @param outputStream outputStream
     */
    public static void buildExcelByTemplate(String template, Map data, OutputStream outputStream) {
        Workbook workbook = null;
        try {
            workbook = getWorkBook(template);
        } catch (InvalidFormatException e) {
            LOG.warn("template format error", e);
        } catch (IOException e) {
            LOG.warn("template io error", e);
        }
        if (null == workbook) {
            return;
        }
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformWorkbook(workbook, data);
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            LOG.warn("out excel io error", e);
        }
    }

    /**
     * getWorkBook getWorkBook
     *
     * @param template template
     * @return Workbook
     * @throws IOException            IOException
     * @throws InvalidFormatException InvalidFormatException
     */
    public static Workbook getWorkBook(String template) throws IOException, InvalidFormatException {
        URL url = ExcelUtil.class.getResource("/" + template);
        if (null == url) {
            LOG.info("not found excel template");
            return null;
        }
        File templateFile = new File(url.getPath());
        if (!templateFile.exists()) {
            LOG.info("jxls  template not exist:" + templateFile.getPath());
            return null;
        }
        InputStream is = new BufferedInputStream(new FileInputStream(templateFile));
        Workbook wb;
        try {
            wb = WorkbookFactory.create(is);
        } catch (InvalidFormatException e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return wb;
    }
}
