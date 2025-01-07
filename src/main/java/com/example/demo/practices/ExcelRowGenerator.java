package com.example.demo.practices;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;


public class ExcelRowGenerator implements Iterator<String[]> {
    private final Sheet sheet;
    private final Iterator<Row> rowIterator;

    public ExcelRowGenerator(String filePath) throws Exception {
        InputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        this.sheet = workbook.getSheetAt(0);
        this.rowIterator = sheet.iterator();
    }

    @Override
    public boolean hasNext() {
        return rowIterator.hasNext();
    }

    @Override
    public String[] next() {
        Row row = rowIterator.next();
        int columnCount = row.getLastCellNum();
        String[] data = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            data[i] = cell.toString();
        }
        return data;
    }
}
