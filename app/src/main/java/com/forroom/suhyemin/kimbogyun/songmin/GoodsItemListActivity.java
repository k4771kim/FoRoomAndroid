package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemValueObject;

import java.util.ArrayList;

public class GoodsItemListActivity extends AppCompatActivity {
    private RecyclerView rv;
    int seriesno;
    private static final String SERIESE11 ="소파";
    private static final String SERIESE12 = "의자&스툴";
    private static final String SERIESE13 = "책상&책장";
    private static final String SERIESE14 = "식탁세트&테이블";
    private static final String SERIESE21 = "거실장&소파테이블";
    private static final String SERIESE22 = "화장대&콘솔";
    private static final String SERIESE23 = "침대&협탁";
    private static final String SERIESE24 = "옷장&서랍장";
    private static final String SERIESE31 = "조명";
    private static final String SERIESE32 = "선반&행거";
    private static final String SERIESE33 = "커튼&패브릭";
    private static final String SERIESE34 = "소품&장식";
    private static final String SERIESE41 = "컬러";
    private static final String SERIESE42 = "패턴";
    private static final String SERIESE43 = "스타일";
    private static final String SERIESE44 = "기타";

    private GoodsItemValueObject goodsVO;
    private ImageView rightX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_item_list);
        rightX = (ImageView)findViewById(R.id.goods_right_x);
        rightX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        seriesno = intent.getIntExtra("toGoodsItemList",0);
//        Log.e("series Number =", "      "+seriesno);

        rv = (RecyclerView)findViewById(R.id.goods_item_recyclerview);
        rv.setLayoutManager(new GridLayoutManager(this, 4));
        ArrayList<GoodsItemValueObject> kbg = init(seriesno);

        rv.setAdapter(new GoodsRecyclerAdapter(init(seriesno)));

    }

    public class GoodsRecyclerAdapter extends RecyclerView.Adapter<GoodsRecyclerAdapter.ViewHolder>{
        private ArrayList<GoodsItemValueObject> goodsList;

        public GoodsRecyclerAdapter(ArrayList<GoodsItemValueObject> list){
            this.goodsList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            goodsVO = goodsList.get(position);
//            Log.e("ssong", goodsVO.goods_image);
//            File file = new File(getFilesDir() + "/foroom/", goodsVO.goods_id + ".png");
//            holder.iv.setImageResource(R.drawable.like);
//          holder.iv.setImageBitmap(new File(getFilesDir() + "/foroom/", goodsVO.goods_id + ".png")));

            Glide.with(ForRoomApplication.getFRContext()).load(getFilesDir() + "/" + goodsVO.goods_id+".png").into(holder.iv);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e("ssongddd", position+"");
//                    Log.e("눌렷니?", "ㅇㅇㅇㅇ");
//                    Log.e("ssong goods_id", goodsList.get(position).goods_id);
                    Intent intent = new Intent(GoodsItemListActivity.this, GoodsItemsActivity.class);
                    Log.e("ssong", goodsList.get(position).goods_id);

                    intent.putExtra("togoodsitems", goodsList.get(position).goods_id);
                    intent.putExtra("togoodsitems2", ForRoomApplication.USER_ID);
                    intent.putExtra("togooditemtype", "fromgooditemlist");
                    intent.putExtra("toseriesnum", seriesno);
                    startActivity(intent);
                    finish();
                }
            });
        }


        @Override
        public int getItemCount() {
            return goodsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public View mview;
            public ImageView iv;

            public ViewHolder(View v){
                super(v);
                mview = v;
                iv = (ImageView)v.findViewById(R.id.goods_item_image);
            }
        }
    }

    public ArrayList<GoodsItemValueObject> init(int seriesno){
        String series=null;
        switch (seriesno){
            case 11:
                series =SERIESE11;
                break;
            case 12:
                series =SERIESE12;
                break;
            case 13:
                series =SERIESE13;
                break;
            case 14:
                series =SERIESE14;
                break;
            case 21:
                series =SERIESE21;
                break;
            case 22:
                series =SERIESE22;
                break;
            case 23:
                series =SERIESE23;
                break;
            case 24:
                series =SERIESE24;
                break;
            case 31:
                series =SERIESE31;
                break;
            case 32:
                series =SERIESE32;
                break;
            case 33:
                series =SERIESE33;
                break;
            case 34:
                series =SERIESE34;
                break;
            case 41:
                series = SERIESE41;
                break;
            case 42:
                series = SERIESE42;
                break;
            case 43:
                series = SERIESE43;
                break;
            case 44:
                series = SERIESE44;
                break;
        }
        MainActivity ow = new MainActivity();

//        return MainActivity.select(series);
        return ow.select(series);
    }

}
