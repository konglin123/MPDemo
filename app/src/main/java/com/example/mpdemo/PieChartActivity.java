package com.example.mpdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PieChartActivity extends AppCompatActivity {

    @BindView(R.id.pie_chart)
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //设置偏移量
        pieChart.setExtraOffsets(20,0,20,40);
        //是否可以手动旋转
        pieChart.setRotationEnabled(false);
        //是否绘制中心圆洞(测试之后发现设置为false之后连透明圆都不绘制)
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setHoleRadius(15f);
//        //透明圈
//        pieChart.setTransparentCircleRadius(30f);
//        pieChart.setTransparentCircleColor(Color.WHITE); //设置透明圆圈的颜色
//        pieChart.setTransparentCircleAlpha(125); //设置透明圆圈的透明度
        //设置动画
        pieChart.animateY(3000);
        //设置描述
        setDescription("考试成绩分布图");
        //设置图例
        setLegend();
        //设置数据
        setChartData();
    }

    private void setLegend() {
        Legend legend = pieChart.getLegend();
        //设置图例形状
//        legend.setForm(Legend.LegendForm.SQUARE);
//        legend.setForm(Legend.LegendForm.LINE); // 线
//        legend.setFormSize(32f); // 图形大小
//        // 设置虚线
//        legend.setFormLineDashEffect(
//                new DashPathEffect(new float[]{12f,12f,12f}, 10f));
        legend.setFormSize(10);
        legend.setTextSize(10);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        List<LegendEntry> entries = new ArrayList<>();
        entries.add(new LegendEntry(
                "A",
                Legend.LegendForm.LINE,
                14f,
                9f,
                null,
                Color.RED)
        );
        entries.add(new LegendEntry(
                "B",
                Legend.LegendForm.CIRCLE,
                14f,
                0f,
                null,
                Color.GREEN)
        );
        entries.add(new LegendEntry(
                "C",
                Legend.LegendForm.SQUARE,
                14f,
                0f,
                null,
                Color.BLUE));
        //设置自定义图例
        legend.setCustom(entries);
    }

    private void setChartData() {
        List<PieEntry> dataSet=new ArrayList<>();
        dataSet.add(new PieEntry(15f,"不及格"));
        dataSet.add(new PieEntry(30f,"优秀"));
        dataSet.add(new PieEntry(55f,"良好"));

        List<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#4A92FC"));
        colors.add(Color.parseColor("#ee6e55"));
        colors.add(Color.parseColor("#3b6e5d"));

        PieDataSet pieDataSet = new PieDataSet(dataSet, "");
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(5f);//设置饼块之间的间隔
//        pieDataSet.setSelectionShift(12f);//设置饼块选中时偏离饼图中心的距离
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
    }


    private void setDescription(String text) {
        // 计算描述位置
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        Description description = new Description();
        description.setText(text);
        description.setTextSize(18f);
        description.setTextColor(Color.parseColor("#4A92FC"));
        //字体因为默认对齐方式是右对齐，我们要改成居中对齐
        description.setTextAlign(Paint.Align.CENTER);
        Paint paint = new Paint();
        //这个设置大小只会影响字体高度，并不会影响字体大小，字体大小还是要用description来设置
        paint.setTextSize(45);
        float x=outMetrics.widthPixels/2;
        float height=Utils.calcTextHeight(paint,text);
        float y=Utils.convertDpToPixel(height+12);
        description.setPosition(x,y);
        pieChart.setDescription(description);
    }
}
