package com.example.mpdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartActivity extends AppCompatActivity {
    @BindView(R.id.bar_chart)
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        //禁止y轴缩放
//        barChart.setScaleYEnabled(false);
        //禁止x轴缩放
//        barChart.setScaleXEnabled(false);
        //x,y轴都禁止缩放
        barChart.setScaleEnabled(false);
        //捏缩放
//        barChart.setPinchZoom(true);
        //双击能否放大（设置x，y轴都禁止缩放时，此功能就无效，如果禁止x轴缩放，双击就在垂直方向放大，反之同理）
//        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setExtraOffsets(24f, 48f, 24f, 24f);
        //y轴做动画2秒完成
        barChart.animateY(2000);
        //是否绘制边框
//        barChart.setDrawBorders(true);
        //绘制边框的颜色
//        barChart.setBorderColor(Color.YELLOW);
        //设置描述
        setDescription("年龄群体车辆违章的占比统计");
        //设置x轴
        setXAxis();
        //设置y轴
        setYAxis();
        //设置图例
        setLegend();
        //设置数据
        setChartData();
        /** 要注意只有在设置数据之后调用才有用*/
        barChart.setVisibleXRangeMaximum(5);
        //设置点击
        setListener();
    }

    //这个点击只会点击最高的，考虑一下怎么都能点到，并且设置marker（堆叠图）
    private void setListener() {
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(BarChartActivity.this,e.getY()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setChartData() {
        List<BarEntry> yValue1=new ArrayList<>();
        yValue1.add(new BarEntry(0f, 396));
        yValue1.add(new BarEntry(1f, 1089));
        yValue1.add(new BarEntry(2f, 963));
        yValue1.add(new BarEntry(3f, 756));
        yValue1.add(new BarEntry(4f, 287));
        yValue1.add(new BarEntry(5f, 396));

        /** 因为两根柱子要并列显示，所以第二个柱子的x值要比第一根柱子大0.3f，同时柱子的宽度也要设置0.3f*/
        List<BarEntry> yValue2=new ArrayList<>();
        yValue2.add(new BarEntry(0.3f, 245));
        yValue2.add(new BarEntry(1.3f, 520));
        yValue2.add(new BarEntry(2.3f, 504));
        yValue2.add(new BarEntry(3.3f, 517));
        yValue2.add(new BarEntry(4.3f, 186));
        yValue2.add(new BarEntry(5.3f, 245));


        BarDataSet dataSet = new BarDataSet(yValue1, "有违章");
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.RED);
        dataSet.setValueTextSize(Utils.convertDpToPixel(10));
//        dataSet.setValueFormatter(new ValueFormatter() {
//
//            /** 经测试getAxisLabel和getPointLabel都没有调用
//             * 而getFormattedValue(float value）只能获取y轴的值，获取不了x轴的值
//             * */
//            @Override
//            public String getBarLabel(BarEntry barEntry) {
//                float value = barEntry.getY();
//                return new DecimalFormat("##.0").format(value / (yValue2.get((int) barEntry.getX()).getY() + value) * 100) + "%";
//            }
//        });

        BarDataSet dataSet2 = new BarDataSet(yValue2, "无违章");
        dataSet2.setColor(Color.YELLOW);
        //启用/禁用 绘制所有 DataSets 数据对象包含的数据的值文本。
        /** 就是是否绘制柱子上方的值和x轴下方的值 */
//        dataSet2.setDrawValues(false);

        List<IBarDataSet> dataSetList=new ArrayList<>();
        dataSetList.add(dataSet);
        dataSetList.add(dataSet2);
        BarData barData = new BarData(dataSetList);
        barData.setBarWidth(0.3f);
        barChart.setData(barData);

    }

    private void setLegend() {
        Legend legend = barChart.getLegend();
        legend.setTextSize(14);
        legend.setXOffset(24);
        //图例在水平线上向右对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //图例在垂直线上向上对齐
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //图例条目垂直方向排列
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        //绘制在图表内部
        legend.setDrawInside(true);
        //设置垂直图例之间的间隔
//        legend.setYEntrySpace(20);
        //设置图例form到文字的间距
//        legend.setFormToTextSpace(20);
        //设置自定义图例
//        legend.setCustom();
    }

    private void setYAxis() {
        //左边y轴
        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setAxisMinimum(0); // 最小值为0
        //如果设置为true，该轴将被反转，这意味着最高值将在底部，顶部的最低值。
//        axisLeft.setInverted(true);
        // 启用网格线的虚线模式中得出，比如像这样“ - - - - - - ”。
//        axisLeft.enableGridDashedLine(3,3,0);
        //设置y轴标签距离y轴的距离（因为是水平距离，所以是XOffset）
//        axisLeft.setXOffset(10);
//        axisLeft.setAxisMaximum(1200); // 最大值为1200
        //右边y轴不启用
        barChart.getAxisRight().setEnabled(false);
    }

    private void setXAxis() {
        XAxis xAxis = barChart.getXAxis();
        //不绘制x轴网格线
        xAxis.setDrawGridLines(false);
        //如果设置为true，则在绘制时会避免“剪掉”在x轴上的图表或屏幕边缘的第一个和最后一个坐标轴标签项。
        xAxis.setAvoidFirstLastClipping(true);
        //设置在底部(BOTTOM是x轴刻度在x轴外面，BOTTOM_INSIDE是x轴刻度在x轴里面)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        LimitLine xLimitLine = new LimitLine(2f,"is Behind");
        xLimitLine.setLineColor(Color.BLUE);
        xLimitLine.setTextColor(Color.BLUE);
        xAxis.addLimitLine(xLimitLine);
        //控制 LimitLines 与 actual data 之间的 z-order 。
        // 如果设置为 true，LimitLines 绘制在 actual data 的后面，否则在其前面。
        // 默认值：false
        xAxis.setDrawLimitLinesBehindData(true);

        //设置最小值 最小值-0.3f，为了使左侧留出点空间
//        xAxis.setAxisMinimum(-0.3f);
        //设置x轴标签距离x轴的距离（因为是垂直距离，所以是YOffset）
//        xAxis.setYOffset(30);
        //设置最大值
        /**这里有一个问题要注意，如果设置了最大值的话，自定义value得到的值是根据最大值来分配的
         * 而不是根据barData来分配的，这点要注意
         * */
//        xAxis.setAxisMaximum(14f);（这个要去掉）
        //设置间隔
        xAxis.setGranularity(1f);
        //设置字体大小
        xAxis.setTextSize(Utils.convertDpToPixel(5));
        //设置字体样式
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return 9-(int)value+"0后";
            }
        });
    }

    private void setDescription(String text) {
        barChart.getDescription().setText(text);
        barChart.getDescription().setTextSize(18);
        barChart.getDescription().setTextAlign(Paint.Align.CENTER);
        // 计算描述位置
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        float x = outMetrics.widthPixels / 2;
        float y = Utils.convertDpToPixel(30);
        barChart.getDescription().setPosition(x, y);
    }
}
