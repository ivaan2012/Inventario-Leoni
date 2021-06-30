package com.example.inventarioleoni;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    Button btnExcel;
    Button btnBobinaChica;
    Button btnBobinaGrande;
    EditText ctBascula,ctNoParte,ctCantidad,ctFacturacion;


    int monthOfYear,dayOfMonth,year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Date _date = new Date();

        Date _date = new Date();

        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(_date);
        Calendar c1 = Calendar.getInstance();


        Calendar fecha = GregorianCalendar.getInstance();

        mYearIni=fecha.get(Calendar.YEAR);

        mMonthIni=_date.getMonth();
        mDayIni=_date.getDate();


        ctBascula = (EditText) findViewById(R.id.ctBascula);

        ctFacturacion = (EditText) findViewById(R.id.ctFabricacion);

        ctCantidad = (EditText) findViewById(R.id.ctCantidad);

        ctNoParte = (EditText) findViewById(R.id.ctNoParte);

        ctFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });

        //ctBascula.setInputType(ctBascula.TYPE_NULL);


        btnExcel = (Button)findViewById(R.id.btnExcel);

        btnExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           guardar();
                Toast.makeText(MainActivity.this, leer()+"", Toast.LENGTH_SHORT).show();

            }
        });

        btnBobinaChica = (Button)findViewById(R.id.btnBobinaChica);

        btnBobinaChica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //escribir();
                    escribir(ctBascula.getText().equals("") ? "no capturado" : ctBascula.getText().toString(),
                            ctNoParte.getText().equals("") ? "no capturado" : ctNoParte.getText().toString(),
                            Integer.parseInt(ctNoParte.getText().toString())==0 ? 0 : Integer.parseInt(ctNoParte.getText().toString()),
                            ctFacturacion.getText().toString(),"BOBINA CHICA");
                }catch (Exception err){
                    Toast.makeText(MainActivity.this, err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBobinaGrande = (Button)findViewById(R.id.btnBobinaGrande);

        btnBobinaGrande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //escribir();
                    escribir(ctBascula.getText().equals("") ? "no capturado" : ctBascula.getText().toString(),
                            ctNoParte.getText().equals("") ? "no capturado" : ctNoParte.getText().toString(),
                            Integer.parseInt(ctNoParte.getText().toString())==0 ? 0 : Integer.parseInt(ctNoParte.getText().toString()),
                            ctFacturacion.getText().toString(),"BOBINA GRANDE");
                }catch (Exception err){
                    Toast.makeText(MainActivity.this, err.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void guardar() {

        Workbook wb = new HSSFWorkbook();
        Cell cell = null;
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet = null;
        sheet = wb.createSheet("Lista_de_usuarios");

        Row row = null;

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue("BASCULA");

        cell = row.createCell(1);
        cell.setCellValue("NO. PARTE");

        cell = row.createCell(2);
        cell.setCellValue("CANTIDAD");

        cell = row.createCell(3);
        cell.setCellValue("FABRICACION");

        cell = row.createCell(4);
        cell.setCellValue("BOBINA");




        File file = new File(getExternalFilesDir(null),"Inventario_Fisico.xls");
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public int leer() {
        File file = new File(this.getExternalFilesDir(null), "Inventario_Fisico.xls");
        FileInputStream inputStream = null;

        String datos = "";

        try {
            inputStream = new FileInputStream(file);

            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            int columna=0;
            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();

                   // datos = datos+" - "+cell.toString();
                  //  datos = cell.toString();
                  //  columna=cell.getColumnIndex();
                    columna=cell.getRowIndex();

                }

            }

              // Toast.makeText(this, columna, Toast.LENGTH_SHORT).show();
          //  tvDatos.setText(datos);
            return columna+2;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void escribir() {

        File file = new File(this.getExternalFilesDir(null), "Inventario_Fisico.xls");
        FileInputStream inputStream = null;

        String datos = "";
        int renglon=0;

        try {
            inputStream = new FileInputStream(file);

            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            int columna=0;
            Workbook wba = new HSSFWorkbook();
            Cell celle = null;
            CellStyle cellStyle = wba.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            Sheet sheeta = null;
            sheeta = wba.createSheet("Lista_de_usuarios");

            Row rowa = null;



            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();
                    HSSFCell cella = (HSSFCell) cellIterator.next();

                    rowa = sheeta.createRow(renglon);
                    celle = rowa.createCell(0);
                    celle.setCellValue(cell.toString());

                    celle = rowa.createCell(1);
                    celle.setCellValue(cella.toString());

                   renglon++;
                }//primer while
            }//segundo while

            File filea = new File(getExternalFilesDir(null),"Inventario_Fisico.xls");
            FileOutputStream outputStreama = null;

            try {
                outputStreama = new FileOutputStream(filea);
                wba.write(outputStreama);
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                try {
                    outputStreama.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }//ultimo try


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void escribir(String bascula, String noParte, int cantidad, String fabricacion, String bobina) {

        File file = new File(this.getExternalFilesDir(null), "Inventario_Fisico.xls");
        FileInputStream inputStream = null;

        String datos = "";
        int renglon=0;

        try {
            inputStream = new FileInputStream(file);

            POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);

            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);

            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            int columna=0;
            Workbook wba = new HSSFWorkbook();
            Cell celle = null;
            CellStyle cellStyle = wba.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            Sheet sheeta = null;
            sheeta = wba.createSheet("Lista_de_usuarios");

            Row rowa = null;



            while (rowIterator.hasNext()) {
                HSSFRow row = (HSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIterator.next();
                    HSSFCell cella = (HSSFCell) cellIterator.next();
                    HSSFCell celll = (HSSFCell) cellIterator.next();
                    HSSFCell cellla = (HSSFCell) cellIterator.next();
                    HSSFCell celllaa = (HSSFCell) cellIterator.next();


                    rowa = sheeta.createRow(renglon);
                    celle = rowa.createCell(0);
                    celle.setCellValue(cell.toString());

                    celle = rowa.createCell(1);
                    celle.setCellValue(cella.toString());

                    celle = rowa.createCell(2);
                    celle.setCellValue(celll.toString());

                    celle = rowa.createCell(3);
                    celle.setCellValue(cellla.toString());

                    celle = rowa.createCell(4);
                    celle.setCellValue(celllaa.toString());


                    renglon++;
                }//primer while
            }//segundo while


            rowa = sheeta.createRow(renglon);
            celle = rowa.createCell(0);
            celle.setCellValue(bascula);

            celle = rowa.createCell(1);
            celle.setCellValue(noParte);

            celle = rowa.createCell(2);
            celle.setCellValue(cantidad);

            celle = rowa.createCell(3);
            celle.setCellValue(fabricacion);

            celle = rowa.createCell(4);
            celle.setCellValue(bobina.toString());



            File filea = new File(getExternalFilesDir(null),"Inventario_Fisico.xls");
            FileOutputStream outputStreama = null;

            try {
                outputStreama = new FileOutputStream(filea);
                wba.write(outputStreama);
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                try {
                    outputStreama.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }//ultimo try


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int mYearIni, int mMonthIni, int mDayIni) {




                  //  mYearIni = year;
                  //  mMonthIni = monthOfYear;
                 //   mDayIni = dayOfMonth;

                    //Toast.makeText(MainActivity.this, mYearIni+" date picker", Toast.LENGTH_LONG).show();
                  //  Toast.makeText(MainActivity.this, mMonthIni+" date picker", Toast.LENGTH_LONG).show();
                  //  Toast.makeText(MainActivity.this, mDayIni+" date picker", Toast.LENGTH_LONG).show();
                    ctFacturacion.setText((mMonthIni + 1) + "-" + mDayIni + "-" + mYearIni+" ");

                    //colocar_fecha(mYearIni,mMonthIni,mDayIni);

                }

            };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, mYearIni, mMonthIni, mDayIni);
        }


        return null;
    }

}
