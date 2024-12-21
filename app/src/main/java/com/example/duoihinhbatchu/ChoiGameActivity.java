package com.example.duoihinhbatchu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duoihinhbatchu.adapter.DapAnAdapter;
import com.example.duoihinhbatchu.model.ChoiGameModels;
import com.example.duoihinhbatchu.object.CauDo;

import java.util.ArrayList;
import java.util.Random;

public class ChoiGameActivity extends AppCompatActivity {

    ChoiGameModels models;
    CauDo cauDo;
    private String dapAn = "";
    private ArrayList<String> arrCauTraLoi;
    private GridView gdvCauTraLoi;
    int index = 0;

    private ArrayList<String> arrDapAn;
    private GridView gdvDapAn;

    ImageView imgAnhCauDo;
    TextView txvTienNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choi_game);

        init();
        anhXa();
        setOnClick();
        hienCauDo();
    }

    private void anhXa() {
        gdvCauTraLoi = findViewById(R.id.gdvCauTraLoi);
        gdvDapAn = findViewById(R.id.gdvDapAn);
        imgAnhCauDo = findViewById(R.id.imgAnhCauDo);
        txvTienNguoiDung = findViewById(R.id.txvTienNguoiDung);
    }

    private void init() {
        models = new ChoiGameModels((this));
        arrCauTraLoi = new ArrayList<>();
        arrDapAn = new ArrayList<>();
    }

    private void hienCauDo() {
        cauDo = models.layCauDo();
        dapAn = cauDo.dapAn;

        bamData();
        hienThiCauTraLoi();
        hienThiDapAn();
        String imageName = cauDo.anh;
        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResourceId != 0) {
            imgAnhCauDo.setImageResource(imageResourceId);
        }
        models.layThongTin();
        txvTienNguoiDung.setText(models.nguoiDung.tien + "$");
    }

    private void hienThiCauTraLoi() {
        gdvCauTraLoi.setNumColumns(arrCauTraLoi.size());
        gdvCauTraLoi.setAdapter(new DapAnAdapter(this, 0, arrCauTraLoi));
    }

    private void hienThiDapAn() {
        gdvDapAn.setNumColumns(arrDapAn.size()/2);
        gdvDapAn.setAdapter(new DapAnAdapter(this, 0, arrDapAn));
    }

    private void setOnClick() {
        gdvDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0 && index < arrCauTraLoi.size()) {
                    for (int i=0; i<arrCauTraLoi.size(); i++){
                        if(arrCauTraLoi.get(i).length() == 0){
                            index = i;
                            break;
                        }
                    }
                    arrDapAn.set(position,"");
                    arrCauTraLoi.set(index, s);
                    index++;
                    hienThiCauTraLoi();
                    hienThiDapAn();
                    checkWin();
                }
            }
        });

        gdvCauTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                if (s.length() != 0) {
                    index = position;
                    arrCauTraLoi.set(position, "");
                    for(int i=0; i < arrDapAn.size(); i++) {
                        if(arrDapAn.get(i).length() == 0) {
                            arrDapAn.set(i, s);
                            break;
                        }
                    }
                    hienThiCauTraLoi();
                    hienThiDapAn();
                }
            }
        });
    }

    private void bamData() {
        index = 0;
        arrCauTraLoi.clear();
        arrDapAn.clear();
        Random r = new Random();

        for (int i = 0; i < dapAn.length(); i++) {
            arrCauTraLoi.add("");
            String s = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s);
            String s1 = "" + (char) (r.nextInt(26) + 65);
            arrDapAn.add(s1);
        }

        for (int i = 0; i < dapAn.length(); i++) {
            String s = "" + dapAn.charAt(i);
            arrDapAn.set(i, s.toUpperCase());
        }

        for(int i = 0; i < arrDapAn.size(); i++) {
            String s = arrDapAn.get(i);
            int vt = r.nextInt(arrDapAn.size());
            arrDapAn.set(i, arrDapAn.get(vt));
            arrDapAn.set(vt, s); // C B A D
        }
    }

    private void checkWin(){
        String s="";
        for (String sl:arrCauTraLoi){
            s += sl;
        }
        s = s.toUpperCase();
        if (s.equals(dapAn.toUpperCase())){
            Toast.makeText(this, "Bạn đã chiến thắng", Toast.LENGTH_SHORT).show();
            models.layThongTin();
            models.nguoiDung.tien = models.nguoiDung.tien + 10;
            models.luuThongTin();
            hienCauDo();
        }
    }

    public void moGoiY(View view) {
        models.layThongTin();
        if (models.nguoiDung.tien < 5) {
            Toast.makeText(this, "Bạn đã hết tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = -1;
        for (int i = 0; i < arrCauTraLoi.size(); i++) {
            if (arrCauTraLoi.get(i).length() == 0) {
                id = i;
                break;
            }
        }

        if (id == -1) {
            for (int i = 0; i < arrCauTraLoi.size(); i++) {
                String s = dapAn.toUpperCase().charAt(i) + "";
                if (!arrCauTraLoi.get(i).toUpperCase().equals(s)) {
                    id = i;
                    break;
                }
            }
            for (int i = 0; i < arrDapAn.size(); i++) {
                if (arrDapAn.get(i).length() == 0) {
                    arrDapAn.set(i, arrCauTraLoi.get(id));
                    break;
                }
            }
        }

        String goiY = "" + dapAn.charAt(id);
        goiY = goiY.toUpperCase();
        for (int i = 0; i < arrCauTraLoi.size(); i++) {
            if (arrCauTraLoi.get(i).toUpperCase().equals(goiY)){
                arrCauTraLoi.set(i, "");
                break;
            }
        }
        for (int i = 0; i < arrDapAn.size(); i++) {
            if (goiY.equals(arrDapAn.get(i))) {
                arrDapAn.set(i, "");
                break;
            }
        }
        arrCauTraLoi.set(id, goiY);
        hienThiCauTraLoi();
        hienThiDapAn();
        models.layThongTin();
        models.nguoiDung.tien = models.nguoiDung.tien - 5;
        models.luuThongTin();
        txvTienNguoiDung.setText(models.nguoiDung.tien + "$");
    }

    public void doiCauHoi(View view) {
        models.layThongTin();
        if (models.nguoiDung.tien < 5) {
            Toast.makeText(this, "Ban Da Het Tien", Toast.LENGTH_SHORT).show();
            return;
        }

        models.nguoiDung.tien = models.nguoiDung.tien - 10;
        models.luuThongTin();
        txvTienNguoiDung.setText(models.nguoiDung.tien + "$");
        hienCauDo();
    }
}