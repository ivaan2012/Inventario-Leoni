package com.example.inventarioleoni;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnExcel;

        btnExcel = (Button)findViewById(R.id.btnExcel);

        btnExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            guardar();

            }
        });
    }
    public void guardar() {
        Workbook wb = new HSSFWorkbook();
        Cell cell = null;
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet = null;
        sheet = wb.createSheet("Lista de usuarios");

        Row row = null;

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("USUARIO");
        cell.setCellStyle(cellStyle);

        sheet.createRow(1);
        cell = row.createCell(1);
        cell.setCellValue("NOMBRE");
        cell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("xcheko51x");

        cell = row.createCell(1);
        cell.setCellValue("Sergio Peralta");

        File file = new File(getExternalFilesDir(null),"Relacion_Usuarios.xls");
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(),"OK", Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}