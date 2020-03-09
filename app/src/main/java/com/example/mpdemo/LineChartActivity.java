package com.example.mpdemo;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LineChartActivity extends AppCompatActivity {


    @BindView(R.id.line_chart)
    LineChart lineChart;

    private List<Entry> yValue1=new ArrayList<>();
    private List<Entry> yValue2=new ArrayList<>();

    private float[] y1={22f, 24f, 18f, 29f, 25f, 22f};
    private float[] y2={14f, 11f, 16f, 11f, 12f, 13f,15f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.bind(this);

//       initData();
         initData2();
    }

    /** 综合案例*/
    private void initData2() {
        //设置上下左右偏移量
        lineChart.setExtraOffsets(24f,24f,24f,0);
//          lineChart.setBackgroundColor(Color.parseColor("#0f0f0f"));
          setDescription("过去一分钟的气温变化");
          //x轴y轴同时做动画
          lineChart.animateXY(3000,3000);
        //y轴做动画
//        lineChart.animateY(8000, Easing.EaseInElastic);
          setLegend();
          setXAxis();
          setYAxis();
          setChartData();
          setMarker();

    }

    private void setDescription(String descriptionStr){
//        //设置描述
//        Description description = new Description();
//        description.setText(descriptionStr);
//        //计算描述位置
//        WindowManager wm= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(displayMetrics);
//        Paint paint = new Paint();
//        paint.setTextSize(20f);
//        float x=displayMetrics.widthPixels/2+Utils.calcTextWidth(paint,descriptionStr);
//        float y=Utils.calcTextHeight(paint,descriptionStr)+Utils.convertDpToPixel(12);
//        //设置描述位置
//        description.setPosition(x,y);
//        description.setTextSize(20);
//        lineChart.setDescription(description);
        lineChart.getDescription().setText(descriptionStr);
        lineChart.getDescription().setPosition(500,30);
        lineChart.getDescription().setTextSize(18);

    }
    //设置图例
    private void setLegend(){
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE); // 图形：线
        legend.setFormSize(14f); // 图形大小
        legend.setFormLineWidth(9f); // 线宽小于如下大小绘制出平躺长方形
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT); // 图例在水平线上的对齐方式：右对齐
        legend.setTextColor(Color.RED);
    }


    //设置y轴
    private void setYAxis() {
        // 左边Y轴
        final YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(25.5f); // 设置Y轴最大值
        yAxisLeft.setAxisMinimum(14); // 设置Y轴最小值
        yAxisLeft.setGranularity(2f); // 设置间隔尺寸
        yAxisLeft.setTextSize(12f); // 文本大小为12dp
        yAxisLeft.setTextColor(Color.BLACK); // 文本颜色为黑色
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value == yAxisLeft.getAxisMinimum() ? (int) value + "" : (int) value +"";
            }
        });
        // 右侧Y轴
        lineChart.getAxisRight().setEnabled(false); // 不启用
    }

    //设置x轴
    private void setXAxis() {
        // X轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 在底部
        xAxis.setDrawGridLines(false); // 不绘制网格线
        xAxis.setLabelCount(20); // 设置标签数量
        xAxis.setTextColor(Color.WHITE); // 文本颜色为灰色
        xAxis.setTextSize(12f); // 文本大小为12dp
        xAxis.setTextColor(Color.BLACK);
        xAxis.setGranularity(3f); // 设置间隔尺寸
        xAxis.setAxisMinimum(0f); // 设置X轴最小值
        xAxis.setAxisMaximum(63f); // 设置X轴最大值

        LimitLine xLimitLine = new LimitLine(24f,"xL 测试");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        //给该轴添加一个新的 LimitLine(限制线)
        xAxis.addLimitLine(xLimitLine);

        // 设置标签的显示格式
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value == 0 ? "℃" : value == 63 ? "(S)" : value < 10 ? "0" + (int) value : (int) value + "";
            }
        });
    }


    //设置数据
    public void setChartData() {
        // 1.获取一或多组Entry对象集合的数据
        // 模拟数据1
        List<Entry> yVals1 = new ArrayList<>();
        float[] ys1 = new float[] {
                19f, 19f, 18f, 18f, 18f, 18f, 17f, 16f, 17f, 19f,
                21f, 21f, 23f, 23f, 24f, 24f, 25f, 25f, 25f, 24f};
        for (int i = 0; i < ys1.length; i++) {
            yVals1.add(new Entry((i + 1) * 3,ys1[i]));
        }

        // 2.分别通过每一组Entry对象集合的数据创建折线数据集
        LineDataSet lineDataSet1 = new LineDataSet(yVals1, "温度");
        lineDataSet1.setDrawCircleHole(false); // 不绘制圆洞，即为实心圆点
        lineDataSet1.setDrawCircles(true);
//        lineDataSet1.setCircleHoleColor(Color.BLUE);
        lineDataSet1.setColor(Color.RED); // 设置为红色
        /// 设置点击某个点时，横竖两条线的颜色(不想看到的话可以设置为透明)
        lineDataSet1.setHighLightColor(Color.BLUE);
//        lineDataSet1.setHighlightLineWidth(10);
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 设置为贝塞尔曲线
        lineDataSet1.setCubicIntensity(0.15f); // 强度
        lineDataSet1.setCircleColor(Color.GREEN); // 设置圆点为颜色
        //设置圆半径
        lineDataSet1.setCircleRadius(5f);
        lineDataSet1.setLineWidth(2f); // 设置线宽为2
        //启用填充
        lineDataSet1.setDrawFilled(true);
        //填充色
        lineDataSet1.setFillColor(Color.GREEN);
        //透明度
        lineDataSet1.setFillAlpha(65);
        // 3.将每一组折线数据集添加到折线数据中
        LineData lineData = new LineData(lineDataSet1);
        lineData.setDrawValues(false);
        // 4.将折线数据设置给图表
        lineChart.setData(lineData);
    }


    private void setMarker(){
        MyMarker myMarker = new MyMarker();
        lineChart.setMarker(myMarker);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                myMarker.refreshContent(e,h);
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * 这个方式也可以自定义marker
     */
//   public class MM extends MarkerView{
//
//
//        /**
//         * Constructor. Sets up the MarkerView with a custom layout resource.
//         *
//         * @param context
//         * @param layoutResource the layout resource to use for the MarkerView
//         */
//        public MM(Context context, int layoutResource) {
//            super(context, layoutResource);
//        }
//
//        @Override
//        public void refreshContent(Entry e, Highlight highlight) {
//            super.refreshContent(e, highlight);
//        }
//    }
    public class MyMarker implements IMarker{

        private Entry entry;
        @Override
        public MPPointF getOffset() {
            return null;
        }

        @Override
        public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
            return null;
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
           this.entry=e;
        }

        @Override
        public void draw(Canvas canvas, float posX, float posY) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.YELLOW);

            //绘制一个倒等腰三角形
            Path path = new Path();
            path.moveTo(posX,posY-Utils.convertDpToPixel(5));
            path.lineTo(posX-Utils.convertDpToPixel(8),posY-Utils.convertDpToPixel(18));
            path.lineTo(posX+Utils.convertDpToPixel(8),posY-Utils.convertDpToPixel(18));
            path.close();
            canvas.drawPath(path,paint);

            //绘制矩形
            RectF rectF = new RectF(posX - Utils.convertDpToPixel(24),
                    posY - Utils.convertDpToPixel(41),
                    posX + Utils.convertDpToPixel(24),
                    posY - Utils.convertDpToPixel(18));
            canvas.drawRect(rectF,paint);

            //绘制文字，在矩形正中间
            if(entry!=null){
                String text=entry.getY()+"";
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setTextSize(Utils.convertDpToPixel(18));
                textPaint.setColor(Color.RED);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                int dx= (int) (rectF.left+(rectF.width()-Utils.calcTextWidth(textPaint,text))/2);
                int dy= (int) (rectF.top+rectF.height()/2+((fontMetrics.descent-fontMetrics.ascent)/2-fontMetrics.descent));
                canvas.drawText(text,dx,dy,textPaint);
            }


        }
    }


    /** 基础案例*/
    private void initData() {
        lineChart.setNoDataText("没有图表数据");
        lineChart.setNoDataTextColor(Color.RED);

        for (int i = 0; i < y1.length; i++) {
            yValue1.add(new Entry(i,y1[i]));
        }

        for (int i = 0; i < y2.length; i++) {
            yValue2.add(new Entry(i,y2[i]));
        }
        //分别通过每一组Entry对象集合的数据创建折线数据集
        LineDataSet lineDataSet1 = new LineDataSet(yValue1, "最高温度");
        //设置折线的颜色和标记颜色
        lineDataSet1.setColor(Color.RED);
        //设置圆的颜色
        lineDataSet1.setCircleColor(Color.RED);
        //是否画圆（默认是画的）
//        lineDataSet1.setDrawCircles(false);
        //是画空心圆还是实心圆(默认是空心圆)
        lineDataSet1.setDrawCircleHole(false);
        //设置折线的宽度
        lineDataSet1.setLineWidth(2f);
        //设置折线点值的字体大小
        lineDataSet1.setValueTextSize(12f);
        //设置折线点值的格式（如xxx$,xxx%等）
        lineDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value+"°c";
            }
        });

        LineDataSet lineDataSet2 = new LineDataSet(yValue2, "最低温度");
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setLineWidth(4f);
        lineDataSet2.setValueTextSize(12f);
        lineDataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return value+"°c";
            }
        });

        List<ILineDataSet> dataSets=new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        lineChart.setExtraTopOffset(5f);

       //左边y轴
        YAxis axisLeft = lineChart.getAxisLeft();
        //不绘制y轴
        axisLeft.setDrawAxisLine(false);
        //不绘制y轴刻度
        axisLeft.setDrawLabels(false);
        axisLeft.setAxisMaximum(30f);
        axisLeft.setAxisMinimum(10f);
        axisLeft.setGranularity(3f);
        axisLeft.setDrawGridLines(false);

        //右边y轴
        YAxis axisRight = lineChart.getAxisRight();
        //右边y轴不启用
        axisRight.setEnabled(false);
//        axisRight.setAxisMaximum(30f);
//        axisRight.setAxisMinimum(10f);
//        axisRight.setGranularity(4f);
        //x轴
        XAxis xAxis = lineChart.getXAxis();
        //不绘制x轴
        xAxis.setDrawAxisLine(false);
        //不绘制网格线
        xAxis.setDrawGridLines(false);

        final String[] weekStrs = new String[]{"昨天", "今天", "明天","周四", "周五", "周六", "周日"};
        //设置x轴标签数量
        xAxis.setLabelCount(weekStrs.length);
        //设置x轴标签文本颜色
        xAxis.setTextColor(Color.GRAY);
        //设置x轴文本字体大小
        xAxis.setTextSize(12f);
        //设置x轴标签间隔大小
        xAxis.setGranularity(1f);

        xAxis.setAxisMaximum(6.1f);
        xAxis.setAxisMinimum(-0.1f);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return weekStrs[(int) value];
            }
        });

    }
}
