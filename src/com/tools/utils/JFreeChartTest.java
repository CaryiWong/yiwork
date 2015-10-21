package com.tools.utils;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartTest {
	public static void main(String[] args) {
		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		DefaultPieDataset dpd = new DefaultPieDataset(); // 建立一个默认的饼图
		dpd.setValue("管理人员", 20); // 输入数据
		dpd.setValue("市场人员", 25);
		dpd.setValue("开发人员", 45);
		dpd.setValue("其他人员", 10);
		JFreeChart chart0 = ChartFactory.createPieChart("某公司人员组织数据图", dpd,
				true, true, false);
		// 可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "JAVA", "1");
		dataset.addValue(200, "js", "1");
		dataset.addValue(200, "C++", "2");
		dataset.addValue(300, "C", "3");
		dataset.addValue(350, "C#", "3");
		dataset.addValue(400, "HTML", "4");
		dataset.addValue(400, "CSS", "5");
		JFreeChart chart1 = ChartFactory.createBarChart3D("编程语言统计", "语言",
				"学习人数", dataset, PlotOrientation.VERTICAL, true, false, false);
		JFrame frame = new JFrame();

		frame.add(new ChartPanel(chart0));
		frame.add(new ChartPanel(chart1));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		ChartFrame chartFrame = new ChartFrame("某公司人员组织数据图", chart0);
		// chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。

		try {
			File file0 = new File("D:/chart0.jpeg");
			File file1 = new File("D:/chart1.jpeg");
			if (!file0.exists() | !file0.isFile())
				file0.createNewFile();
			ChartUtilities.saveChartAsJPEG(file0, chart0, 800, 600);
			if (!file1.exists() | !file1.isFile())
				file1.createNewFile();
			ChartUtilities.saveChartAsJPEG(file1, chart1, 800, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chartFrame.pack(); // 以合适的大小展现图形
		chartFrame.setLocationRelativeTo(null);
		chartFrame.setVisible(true);// 图形是否可见
	}
}
