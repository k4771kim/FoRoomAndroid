package com.forroom.suhyemin.kimbogyun.songmin;

import android.util.Log;

import com.forroom.suhyemin.kimbogyun.songmin.valueobject.CommentValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineListInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.PictagValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.UserInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomValueObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-01-27.
 */
public class ParseDataParseHandler {
    public static ArrayList<WishRoomValueObject> getJSONWishRoomRequestAllList(StringBuilder buf){
        ArrayList<WishRoomValueObject> jsonAllList = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonAllList = new ArrayList<>();
           // jsonArray = new JSONArray((buf.toString()));
            jo = new JSONObject(buf.toString());

//            int jsonObjSize = jsonArray.length();
            jsonArray = jo.getJSONArray("wishroom_list");

            for(int i=0; i< jsonArray.length(); i++){
                WishRoomValueObject wvo = new WishRoomValueObject();
                wvo.id = jsonArray.getJSONObject(i).getString("wr_id");
                wvo.nickname = jsonArray.getJSONObject(i).getString("wr_usrname");
                wvo.comment_text = jsonArray.getJSONObject(i).getString("wr_reply_count");
                wvo.like_text = jsonArray.getJSONObject(i).getString("wr_scrap_count");
                wvo.title = jsonArray.getJSONObject(i).getString("wr_title");
                wvo.hash = new String[jsonArray.getJSONObject(i).getJSONArray("wr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("wr_tag").length(); j++){
                    wvo.hash[j] = jsonArray.getJSONObject(i).getJSONArray("wr_tag").getString(j);
                }
                wvo.wr_scrap_check = jsonArray.getJSONObject(i).getBoolean("wr_check");
                wvo.wishhouse = jsonArray.getJSONObject(i).getString("wr_sticker");

                jsonAllList.add(wvo);
            }
        }catch(Exception e){
            Log.e("getJSONWishRoom", "JSon 파싱중 에러", e);
        }
        return jsonAllList;
    }

    public static ArrayList<MagazineValueObject> getJSONMagazineRequestAllList(StringBuilder buf){
        ArrayList<MagazineValueObject> jsonAllList = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonAllList = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("mgz_list");

            for(int i=0; i<jsonArray.length(); i++){
                MagazineValueObject MagazineVO = new MagazineValueObject();
                MagazineVO.mg_id = jsonArray.getJSONObject(i).getString("mgz_id");
                MagazineVO.magazineImage = jsonArray.getJSONObject(i).getString("mgz_titlepic");
                MagazineVO.mg_seriesNo = jsonArray.getJSONObject(i).getString("mgz_series");
                MagazineVO.mg_title = jsonArray.getJSONObject(i).getString("mgz_title");
                MagazineVO.mg_subtitle = jsonArray.getJSONObject(i).getString("mgz_subtitle");

                jsonAllList.add(MagazineVO);
            }
        }catch(Exception e){
            Log.e("getJSONMagazine", "JSon 파싱중 에러", e);
        }
        return jsonAllList;
    }

    public static ArrayList<MyRoomValueObject> getJSONMyRoomRequestAllList(StringBuilder buf){
        ArrayList<MyRoomValueObject> jsonAllList = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonAllList = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("myroom_list");
            for(int i=0; i< jsonArray.length(); i++){
                MyRoomValueObject mvo = new MyRoomValueObject();
                mvo.id = jsonArray.getJSONObject(i).getString("mr_id");
                mvo.nickname = jsonArray.getJSONObject(i).getString("mr_usrname");
                mvo.title = jsonArray.getJSONObject(i).getString("mr_title");
                mvo.comment_text = jsonArray.getJSONObject(i).getString("mr_replycount");
                mvo.like_text = jsonArray.getJSONObject(i).getString("mr_scrapcount");
                mvo.mr_scrap_check = jsonArray.getJSONObject(i).getBoolean("mr_check");

                mvo.hash = new String[jsonArray.getJSONObject(i).getJSONArray("mr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("mr_tag").length(); j++){
                    mvo.hash[j] = jsonArray.getJSONObject(i).getJSONArray("mr_tag").getString(j);
                }

                mvo.myhouse = new String[jsonArray.getJSONObject(i).getJSONArray("mr_pic").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("mr_pic").length();j++){
                    mvo.myhouse[j] = jsonArray.getJSONObject(i).getJSONArray("mr_pic").getString(j);
                }


                jsonAllList.add(mvo);
            }

        }catch(Exception e){
            Log.e("getJSONWishRoom", "JSon 파싱중 에러", e);
        }
        return jsonAllList;
    }
    public static WishRoomInfoValueObject getJSONWishRoomInfoRequestAllList(StringBuilder buf){
        WishRoomInfoValueObject WRIVO = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        JSONObject jo2 = null;
        try{
            WRIVO = new WishRoomInfoValueObject();
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("wishroom_body");
//            WishRoomInfoValueObject WRIVO = new WishRoomInfoValueObject();

            WRIVO.wr_info_title = jo2.getString("wr_title");
//            Log.e("TT",WRIVO.wr_info_title);
            WRIVO.wr_info_nickname = jo2.getString("wr_usrname");
            WRIVO.wr_info_profile = jo2.getString("wr_usrpic");
            WRIVO.wr_info_userid = jo2.getString("wr_usrid");
            WRIVO.wr_info_houseImage = jo2.getString("wr_sticker");
            WRIVO.wr_info_replycount = jo2.getString("wr_reply_count");
            WRIVO.wr_info_scrapcount = jo2.getString("wr_scrap_count");
            WRIVO.wr_info_hash = new String[jo2.getJSONArray("wr_tag").length()];
            for(int i=0; i<jo2.getJSONArray("wr_tag").length();i++){
                WRIVO.wr_info_hash[i] = jo2.getJSONArray("wr_tag").getString(i);
            }
            jsonArray = jo2.getJSONArray("goods_id");

            WRIVO.wr_info_gd_id = new String[jsonArray.length()];
            WRIVO.wr_info_gd_name = new String[jsonArray.length()];
            WRIVO.wr_info_gd_series = new String[jsonArray.length()];
            WRIVO.wr_info_gd_link = new String[jsonArray.length()];
            WRIVO.wr_info_gd_price = new String[jsonArray.length()];
            WRIVO.wr_info_gd_desc = new String[jsonArray.length()];
            WRIVO.wr_info_gd_sticker = new String[jsonArray.length()];
            WRIVO.wr_info_gd_scrapcount = new String[jsonArray.length()];
//            Log.e("ssong _lengtj", jsonArray.length()+"");
            for(int i=0; i<jsonArray.length(); i++){
//                Log.e("ssong _lengtj", jsonArray.getJSONObject(i).getString("gd_id"));
                WRIVO.wr_info_gd_id[i] = jsonArray.getJSONObject(i).getString("gd_id");
                WRIVO.wr_info_gd_name[i] = jsonArray.getJSONObject(i).getString("gd_name");
                WRIVO.wr_info_gd_series[i] = jsonArray.getJSONObject(i).getString("gd_series");
                WRIVO.wr_info_gd_link[i] = jsonArray.getJSONObject(i).getString("gd_link");
                WRIVO.wr_info_gd_price[i] = jsonArray.getJSONObject(i).getString("gd_price");
                WRIVO.wr_info_gd_desc[i] = jsonArray.getJSONObject(i).getString("gd_desc");
                WRIVO.wr_info_gd_sticker[i] = jsonArray.getJSONObject(i).getString("gd_sticker");
                WRIVO.wr_info_gd_scrapcount[i] = jsonArray.getJSONObject(i).getString("gd_scarp_count");
            }

        }catch(Exception e){
            Log.e("getWRIVO", "JSON 파싱중 에러",e);
        }

        return WRIVO;
    }
    public static ArrayList<CommentValueObject> getJSONCommentRequestAllList(StringBuilder buf){
        ArrayList<CommentValueObject> jsonAllList = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonAllList = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("reply");


            for(int i=0; i<jsonArray.length(); i++){
                CommentValueObject CVO = new CommentValueObject();

                CVO.rp_nickname = jsonArray.getJSONObject(i).getJSONObject("rep_usrid").getString("usr_name");
                CVO.rp_profile = jsonArray.getJSONObject(i).getJSONObject("rep_usrid").getString("usr_pic");
                CVO.rp_time = jsonArray.getJSONObject(i).getString("rep_time");
                CVO.rp_text = jsonArray.getJSONObject(i).getString("rep_text");

                jsonAllList.add(CVO);
            }
        }catch(Exception e){
            Log.e("getComment", "JSON 파싱중 에러", e);
        }
        return jsonAllList;
    }
    public static MyRoomInfoValueObject getJSONMyRoomInfoRequestAllList(StringBuilder buf){
        MyRoomInfoValueObject jsonAll = null;
//        PictagValueObject pictagVO = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        JSONObject jo2 = null;
        try{
            jsonAll = new MyRoomInfoValueObject();
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("myroom_body");
            jsonAll.mr_info_nickname = jo2.getString("mr_usrname");
            jsonAll.mr_info_profile = jo2.getString("mr_usrpic");
            jsonAll.mr_info_title = jo2.getString("mr_title");
            jsonAll.mr_info_reply_text = jo2.getString("mr_reply_count");
            jsonAll.mr_info_scrap_text = jo2.getString("mr_scrap_count");
            jsonAll.mr_info_hit = jo2.getString("mr_hit");
            jsonAll.mr_info_date = jo2.getString("mr_date");
            jsonAll.mr_info_userid = jo2.getString("mr_usrid");

            jsonAll.mr_info_hash = new String[jo2.getJSONArray("mr_tag").length()];
            for(int i=0; i<jo2.getJSONArray("mr_tag").length();i++){
                jsonAll.mr_info_hash[i] = jo2.getJSONArray("mr_tag").getString(i);
            }

            jsonArray = jo2.getJSONArray("mr_pic");


            jsonAll.mr_info_myroomimage = new String[jsonArray.length()];
            jsonAll.mr_info_myroomtext = new String[jsonArray.length()];
            for(int i=0 ; i<jsonArray.length();i++){
                jsonAll.mr_info_myroomimage[i] = jsonArray.getJSONObject(i).getString("picurl");
//                Log.e("ssong", jsonArray.getJSONObject(i).getString("pictxt"));
                jsonAll.mr_info_myroomtext[i] = jsonArray.getJSONObject(i).getString("pictxt");
/*
                pictagVO = new PictagValueObject();
                int length = jsonArray.getJSONObject(i).getJSONArray("pictag").length();
                pictagVO.dataX = new double[length];
                pictagVO.dataY =new double[length];
                pictagVO.Text= new String[length];
                pictagVO.Color=new int[length];
//                Log.e("ssong ertstset   "+i, jsonArray.getJSONObject(i).getJSONArray("pictag").length()+"");
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("pictag").length();j++){
                    pictagVO.dataX[j] = jsonArray.getJSONObject(i).getJSONArray("pictag").getJSONObject(j).getDouble("dataX");
                    pictagVO.dataY[j] = jsonArray.getJSONObject(i).getJSONArray("pictag").getJSONObject(j).getDouble("dataY");
                    pictagVO.Text[j] = jsonArray.getJSONObject(i).getJSONArray("pictag").getJSONObject(j).getString("Text");
                    pictagVO.Color[j] = jsonArray.getJSONObject(i).getJSONArray("pictag").getJSONObject(j).getInt("Color");
                }
                jsonAll.pictag.add(pictagVO);*/
            }

        }catch(Exception e){
            Log.e("getMyRoomInfo", "JSON 파싱중 에러", e);
        }
        return jsonAll;
    }
    public static MagazineInfoValueObject getJSONMagazineInfoRequestAllList(StringBuilder buf){
        MagazineInfoValueObject jsonAll = null;
        JSONObject jo= null;
        JSONArray jsonArray = null;
        JSONObject jo2 = null;
        try{
            jsonAll = new MagazineInfoValueObject();
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("magazin_body");

            jsonAll.mgz_info_scrapcheck = jo2.getBoolean("mgz_scrap_check");
            jsonAll.mgz_info_id = jo2.getString("mgz_id");
            jsonAll.mgz_info_replycount = jo2.getString("mgz_reply_count");
            jsonAll.mgz_info_scrap_count = jo2.getString("mgz_scrap_count");
            jsonAll.mgz_info_series = jo2.getString("mgz_series");
//            if(jo2.getString("mgz_usevr").equals("true")){
//                jsonAll.mgz_info_usrvr = true;
//            }else{
//                jsonAll.mgz_info_usrvr = false;
//            }
            jsonAll.mgz_info_usrvr = jo2.getBoolean("mgz_usevr");

            if(jsonAll.mgz_info_usrvr){
//                Log.e("usevr" , "1");
                jsonAll.mgz_vr_pic = jo2.getString("mgz_vrpic");

            }
             jsonAll.mgz_info_title = jo2.getString("mgz_title");
            jsonAll.mgz_info_date = jo2.getString("mgz_date");

            jsonAll.mgz_info_pic = new String[jo2.getJSONArray("mgz_pic").length()];
            for(int i=0; i<jo2.getJSONArray("mgz_pic").length();i++){
                jsonAll.mgz_info_pic[i] = jo2.getJSONArray("mgz_pic").getString(i);
            }

            jsonAll.mgz_info_text = new String[jo2.getJSONArray("mgz_text").length()];
            for(int i=0; i<jo2.getJSONArray("mgz_text").length(); i++){
                jsonAll.mgz_info_text[i] = jo2.getJSONArray("mgz_text").getString(i);
            }
        }catch(Exception e){
            Log.e("getmagazineInfo", "JSON 파싱중 에러", e);
        }

        return jsonAll;
    }

    public static UserInfoValueObject getJSONProfileRequestAllList(StringBuilder buf){
        UserInfoValueObject userVO = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        JSONObject jo2 = null;
        try{
            userVO = new UserInfoValueObject();
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("userinfo");

            userVO.usr_jjimcount = jo2.getString("gdlikecnt");
            userVO.usr_id = jo2.getString("usr_id");
            userVO.usr_way = jo2.getString("usr_way");
            userVO.usr_name = jo2.getString("usr_name");
            userVO.usr_likecount = jo2.getString("usr_likecnt");
            userVO.usr_writecount = jo2.getString("usr_writecnt");
            userVO.usr_pic = jo2.getString("usr_pic");
            userVO.usr_desc = jo2.getString("usr_desc");
            userVO.usr_thumb = jo2.getString("usr_thumb");

        }catch(Exception e){
            Log.e("getWRIVO", "JSON 파싱중 에러",e);
        }
        return userVO;
    }

    public static ArrayList<GoodsItemValueObject> getHSONGoodsRequestAllList(StringBuilder buf){
        GoodsItemValueObject goodsVO = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        ArrayList<GoodsItemValueObject> list = null;
        try {
            list = new ArrayList<GoodsItemValueObject>();

            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("goodslist");
            for(int i=0; i<jsonArray.length(); i++){
                goodsVO = new GoodsItemValueObject();
                goodsVO.goods_id = jsonArray.getJSONObject(i).getString("gd_id");
                goodsVO.goods_series = jsonArray.getJSONObject(i).getString("gd_series");
                goodsVO.goods_image = jsonArray.getJSONObject(i).getString("gd_sticker");
                goodsVO.goods_name = jsonArray.getJSONObject(i).getString("gd_name");

                list.add(goodsVO);
            }
        }catch(Exception e){
            Log.e("get dbGoodItem", "JSON 파싱중 에러", e);
        }
        return list;
    }

    public static ArrayList<MagazineValueObject> getScrapMagazineList(StringBuilder buf){
        ArrayList<MagazineValueObject> jsonlist = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonlist = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("scraplist");

            for(int i=0; i<jsonArray.length(); i++){
                MagazineValueObject magazineVO = new MagazineValueObject();
                magazineVO.mg_id = jsonArray.getJSONObject(i).getString("mgz_id");
                magazineVO.mg_title = jsonArray.getJSONObject(i).getString("mgz_title");
                magazineVO.magazineImage = jsonArray.getJSONObject(i).getString("mgz_titlepic");

                jsonlist.add(magazineVO);
            }

        }catch(Exception e){

        }
        return jsonlist;

    }

    public static ArrayList<MyRoomValueObject> getScrapMyRoomlist(StringBuilder buf){
        ArrayList<MyRoomValueObject> jsonlist = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonlist = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("scraplist");

            for(int i=0; i< jsonArray.length(); i++){
                MyRoomValueObject myroomVO = new MyRoomValueObject();
                myroomVO.id = jsonArray.getJSONObject(i).getString("mr_id");

                myroomVO.myhouse = new String[1];
                myroomVO.myhouse[0] = jsonArray.getJSONObject(i).getString("mr_pic1");

                myroomVO.hash = new String[jsonArray.getJSONObject(i).getJSONArray("mr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("mr_tag").length(); j++){
                    myroomVO.hash[j] = jsonArray.getJSONObject(i).getJSONArray("mr_tag").getString(j);
                }

                myroomVO.nickname = jsonArray.getJSONObject(i).getJSONObject("mr_usrid").getString("usr_name");

                jsonlist.add(myroomVO);
            }
        }catch(Exception e){
            Log.e("파싱 에러", "에러", e);
        }
        return jsonlist;
    }

    public static ArrayList<WishRoomValueObject> getScrapWishRoomlist(StringBuilder buf){
        ArrayList<WishRoomValueObject> jsonlist = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonlist = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("scraplist");

            for(int i=0; i< jsonArray.length(); i++){
                WishRoomValueObject wishroomVO = new WishRoomValueObject();
                wishroomVO.id = jsonArray.getJSONObject(i).getString("wr_id");

                wishroomVO.wishhouse = jsonArray.getJSONObject(i).getString("wr_sticker");

                wishroomVO.hash = new String[jsonArray.getJSONObject(i).getJSONArray("wr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("wr_tag").length(); j++){
                    wishroomVO.hash[j] = jsonArray.getJSONObject(i).getJSONArray("wr_tag").getString(j);
                }

                wishroomVO.nickname = jsonArray.getJSONObject(i).getJSONObject("wr_usrid").getString("usr_name");

                jsonlist.add(wishroomVO);
            }
        }catch(Exception e){
            Log.e("파싱 에러", "에러", e);
        }
        return jsonlist;
    }
    public static ArrayList<MyRoomValueObject> getWriteMyRoomList(StringBuilder buf){
        ArrayList<MyRoomValueObject> jsonlist = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonlist = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("docs");

            for(int i=0; i< jsonArray.length(); i++){
                MyRoomValueObject myroomVO = new MyRoomValueObject();
                myroomVO.id = jsonArray.getJSONObject(i).getString("mr_id");

                myroomVO.title = jsonArray.getJSONObject(i).getString("mr_title");

                myroomVO.hash = new String[jsonArray.getJSONObject(i).getJSONArray("mr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("mr_tag").length(); j++){
                    myroomVO.hash[j] = jsonArray.getJSONObject(i).getJSONArray("mr_tag").getString(j);
                }
                myroomVO.myhouse = new String[1];
                myroomVO.myhouse[0] = jsonArray.getJSONObject(i).getString("mr_pic1");

                myroomVO.nickname = jsonArray.getJSONObject(i).getJSONObject("mr_usrid").getString("usr_name");

                jsonlist.add(myroomVO);
            }
        }catch(Exception e){
            Log.e("파싱 에러", "에러", e);
        }
        return jsonlist;
    }

    public static ArrayList<WishRoomValueObject> getWriteWishRoomList(StringBuilder buf){
        ArrayList<WishRoomValueObject> jsonlist = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            jsonlist = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("docs");

            for(int i=0; i< jsonArray.length(); i++){
                WishRoomValueObject wishroomVO = new WishRoomValueObject();
                wishroomVO.id = jsonArray.getJSONObject(i).getString("wr_id");

                wishroomVO.wishhouse = jsonArray.getJSONObject(i).getString("wr_sticker");

                wishroomVO.title = jsonArray.getJSONObject(i).getString("wr_title");
                wishroomVO.hash = new String[jsonArray.getJSONObject(i).getJSONArray("wr_tag").length()];
                for(int j=0; j<jsonArray.getJSONObject(i).getJSONArray("wr_tag").length(); j++){
                    wishroomVO.hash[j] = jsonArray.getJSONObject(i).getJSONArray("wr_tag").getString(j);
                }

                wishroomVO.nickname = jsonArray.getJSONObject(i).getJSONObject("wr_usrid").getString("usr_name");

                jsonlist.add(wishroomVO);
            }
        }catch(Exception e){
            Log.e("파싱 에러", "에러", e);
        }
        return jsonlist;
    }

    public static GoodsItemInfoValueObject getGoodsItem(StringBuilder buf){
        GoodsItemInfoValueObject list = null;
        JSONObject jo = null;
        JSONObject jo2 = null;
        try{
            list = new GoodsItemInfoValueObject();
            jo = new JSONObject(buf.toString());
            jo2 = jo.getJSONObject("goods_info");
            list.goods_image = jo2.getString("gd_sticker");
            list.goods_id = jo2.getString("gd_id");
            list.goods_desc = jo2.getString("gd_desc");
            list.goods_price = jo2.getString("gd_price");
            list.goods_scrap_check = jo2.getBoolean("gd_scrap_check");
            list.goods_scrap_count = jo2.getString("gd_scrap_count");
            list.goods_name = jo2.getString("gd_name");
        }catch(Exception e){
            Log.e("파싱 에러", "에러", e);
        }
        return list;
    }

    public static ArrayList<MagazineListInfoValueObject> getMagazineList(StringBuilder buf){
        ArrayList<MagazineListInfoValueObject> list = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try {
            list = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("magazin_list");
            for(int i=0; i<jsonArray.length();i++){
                MagazineListInfoValueObject magazinelistVO = new MagazineListInfoValueObject();
                magazinelistVO.mgz_id = jsonArray.getJSONObject(i).getString("mgz_id");
                magazinelistVO.mgz_image = jsonArray.getJSONObject(i).getString("mgz_titlepic");
                magazinelistVO.titile = jsonArray.getJSONObject(i).getString("mgz_title");
                magazinelistVO.mgz_series = jsonArray.getJSONObject(i).getString("mgz_series");
                magazinelistVO.mgz_date = jsonArray.getJSONObject(i).getString("mgz_date");
                list.add(magazinelistVO);
            }
        }catch(Exception e){
            Log.e("파싱중 에러", "에러에러",e);
        }
        return list;
    }

    public static ArrayList<GoodsItemInfoValueObject> getGoodsJjimItem(StringBuilder buf){
        ArrayList<GoodsItemInfoValueObject> list = null;
        JSONObject jo = null;
        JSONArray jsonArray = null;
        try{
            list = new ArrayList<>();
            jo = new JSONObject(buf.toString());
            jsonArray = jo.getJSONArray("scraplist");
            for(int i=0; i<jsonArray.length(); i++){
                GoodsItemInfoValueObject goodsVO = new GoodsItemInfoValueObject();
                goodsVO.goods_price = jsonArray.getJSONObject(i).getString("gd_price");
                goodsVO.goods_image = jsonArray.getJSONObject(i).getString("gd_sticker");
                goodsVO.goods_name = jsonArray.getJSONObject(i).getString("gd_name");
                goodsVO.goods_series = jsonArray.getJSONObject(i).getString("gd_series");
                goodsVO.goods_id = jsonArray.getJSONObject(i).getString("gd_id");
                list.add(goodsVO);
            }

        }catch(Exception e){
            Log.e("파싱중 에러", "ㄴㅇ럊대ㅔ서재ㅔㄷ서햐ㅐㄴ얼너ㅏㅇㄹ",e);
        }
        return list;
    }
}
