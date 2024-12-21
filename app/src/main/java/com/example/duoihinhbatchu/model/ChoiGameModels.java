package com.example.duoihinhbatchu.model;

import android.content.Context;
import com.example.duoihinhbatchu.object.CauDo;
import com.example.duoihinhbatchu.object.NguoiDung;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChoiGameModels {

    private Context context;
    private ArrayList<CauDo> arr;
    private int cauSo = -1;
    public NguoiDung nguoiDung;

    public ChoiGameModels(Context context) {
        this.context = context;
        nguoiDung = new NguoiDung();
        taoData();
    }


    private void taoData() {
        arr = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(com.example.duoihinhbatchu.R.raw.cau_do_data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String text = jsonObject.getString("text");
                String answer = jsonObject.getString("answer");
                String image = jsonObject.getString("image");
                arr.add(new CauDo(text, answer, image));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CauDo layCauDo() {
        cauSo++;
        if (cauSo >= arr.size()) {
            cauSo = arr.size() - 1;
        }

        return arr.get(cauSo);
    }

    public void layThongTin() {
        nguoiDung.getTT(context); // Pass Context
    }

    public void luuThongTin() {
        nguoiDung.saveTT(context); // Pass Context
    }
}