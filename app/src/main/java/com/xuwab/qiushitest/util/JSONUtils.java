package com.xuwab.qiushitest.util;

import com.xuwab.qiushitest.bean.SpecialInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuwab on 2016/10/18.
 */
public class JSONUtils {

    public static List<SpecialInfo> jsonStringToList(String string) {
        List<SpecialInfo> list=new ArrayList<>();

        try {
            JSONObject jsonObject=new JSONObject(string);
            JSONArray jsonArray_items=jsonObject.getJSONArray("items");

            for(int i=0;i<jsonArray_items.length();i++){
                JSONObject object=jsonArray_items.getJSONObject(i);
                SpecialInfo specialInfo1 = new SpecialInfo();
                JSONObject userObject=object.optJSONObject("user");
                if(userObject!=null){
                    specialInfo1.setUser_name(userObject.optString("login"));
                    String id=userObject.optString("id");
                    String icon=userObject.optString("icon");
                    specialInfo1.setUserHeaderUrl(getImageIconUrl(id,icon));
                }
                specialInfo1.setContent(object.getString("content"));
                specialInfo1.setContent_image(getImageUrl(object.getString("image")));
                list.add(specialInfo1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String getImageUrl(String imageName) {//穿入的参数:"app113563076.jpg"
        if (imageName.indexOf('.') > 0) {
            StringBuilder sb = new StringBuilder();
            // imageName:app113563076.jpg
            String urlSecond = imageName.substring(3, imageName.indexOf('.')); // 113563076
            String urlFirst = "";
            switch (urlSecond.length()) {
                case 8:
                    urlFirst = imageName.substring(3, 7); // 1135
                    break;
                case 9:
                    urlFirst = imageName.substring(3, 8); // 11356
                    break;
            }
            // http://pic.qiushibaike.com/system/pictures/11356/113563076/small/app113563076.jpg
            sb.append("http://pic.qiushibaike.com/system/pictures/");
            sb.append(urlFirst);
            sb.append("/");
            sb.append(urlSecond);
            sb.append("/");
            sb.append("small/");
            sb.append(imageName);
            return sb.toString();
        } else {
            return "";
        }
    }

    private static String getImageIconUrl(String id,String imageName) {
        String headImageUrl = "http://pic.qiushibaike.com/system/avtnew/" +
                id.substring(0, id.length() / 2) + "/" + id + "/medium/" + imageName;
        return headImageUrl;
    }
}
