package com.tools.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.ByteMatrix;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*******************************************************************************
 * 缩略图类（通用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换。 具体使用方法
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,背景色,是否等比缩放(默认为true))
 */
public class ImageUtil {
	private File file = null; // 文件对象
	private String inputDir; // 输入图路径
	private String outputDir; // 输出图路径
	private String inputFileName; // 输入图文件名
	private String outputFileName; // 输出图文件名
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private Color backgroundColor;// 输出图片背景色
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

	public ImageUtil() { // 初始化变量
		inputDir = "";
		outputDir = "";
		inputFileName = "";
		outputFileName = "";
		outputWidth = 100;
		outputHeight = 100;
	}

	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public void setOutputWidth(int outputWidth) {
		this.outputWidth = outputWidth;
	}

	public void setOutputHeight(int outputHeight) {
		this.outputHeight = outputHeight;
	}

	public void setWidthAndHeight(int width, int height) {
		this.outputWidth = width;
		this.outputHeight = height;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/*
	 * 获得图片大小 传入参数 String path ：图片路径
	 */
	public long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}

	// 图片处理
	public String compressPic() {
		try {
			// 获得源文件
			file = new File(inputDir, inputFileName);
			if (!file.exists()) {
				return "";
			}
			File targetDir = new File(outputDir);
			if (!targetDir.exists())
				targetDir.mkdirs();
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return "no";
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (this.proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = 0.1;
					double rate2 = 0.1;
					if(img.getWidth(null)==0){
						rate1 = ((double) img.getWidth(null))/ (double) outputWidth + 0.1;
					}else {
						rate1 = ((double) img.getWidth(null))/ (double) outputWidth;
					}
					if(img.getHeight(null)==0){
						rate2 = ((double) img.getHeight(null))/ (double) outputHeight + 0.1;
					}else {
						rate2 = ((double) img.getHeight(null))/ (double) outputHeight;
					}
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				Graphics2D dg = (Graphics2D) tag.getGraphics();
				dg.setBackground(backgroundColor);
				dg.clearRect(0, 0, newWidth, newHeight);
				dg.drawImage(img.getScaledInstance(newWidth, newHeight,
						Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(new File(outputDir,
						outputFileName));
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "ok";
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		return compressPic();
	}

	/**
	 * 缩放图片
	 * 
	 * @param inputDir
	 *            源目录
	 * @param outputDir
	 *            目标目录
	 * @param inputFileName
	 *            源图片名
	 * @param outputFileName
	 *            目标图片名
	 * @param width
	 *            输出图片宽
	 * @param height
	 *            输出图片高
	 * @param color
	 *            输出图片背景色
	 * @param gp
	 *            true:等比，false:非等比
	 * @return
	 */
	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			Color color, boolean gp) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		// 设置图片长宽
		setWidthAndHeight(width, height);
		// 背景色
		this.backgroundColor = color;
		// 是否是等比缩放 标记
		this.proportion = gp;
		return compressPic();
	}
	
	/**
	 * 根据字条串生成二维码
	 * @param content 内容
	 * @param width 二维码图片宽
 	 * @param height 二维码图片高
	 * @param targetFile 二维码图片
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 * @author Lee.J.Eric
	 * @time 2014年12月15日 下午2:25:10
	 */
	public static File generateQRcode(String content, Integer width, Integer height,
			File targetFile) throws WriterException, IOException {
		String name = targetFile.getName();
		String format = targetFile.getName().substring(name.lastIndexOf(".")+1);
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		ByteMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToFile(matrix, format, targetFile);
		return targetFile;
	}
	
	/**
	 * 根据字条串生成二维码
	 * @param content 内容
	 * @param width 二维码图片宽
 	 * @param height 二维码图片高
	 * @param stream 输出流
	 * @throws WriterException
	 * @throws IOException
	 * @author Lee.J.Eric
	 * @time 2014年12月17日 下午4:00:07
	 */
	public static void generateQRcode(String content, Integer width, Integer height,OutputStream stream) throws WriterException, IOException{
		if(width==null||width<0)
			width = 200;
		if(height==null||height<0)
			height = 200;
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		ByteMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		MatrixToImageWriter.writeToStream(matrix, "png", stream);
	}
	
	public static void createNumber(String str,Integer width, Integer height,File output) {
		str=RandomUtil.getRandomeStringFornum(10);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g=image.createGraphics();
		image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		
		g.fillRect(0, 0, width, height);
	    Font font = new Font("North Central Province", 0, 18);
	    g.setFont(font);
	    g.setColor(Color.RED);
	    g.drawString(str, 2, 20);
	    
	    //
	    g.dispose();
	    try {
	      ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
	      ImageIO.write(image, "PNG", imageOut);
	      imageOut.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }

	}
	
	public static void main(String[] args) {
		 File output = new File("E:\\81.png");
		 createNumber("",110,24,output);
	}

	// main测试
	// compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
	
	 /* public static void main(String[] arg) { 
		  ImageUtil mypic = new ImageUtil(); 
		  String inDir = "E:/素材"; 
		  String outDir = "E:/素材"; 
		  String  inPic = "6349-110Q323594572.jpg"; 
		  String ext = inPic.substring(inPic.lastIndexOf(".")); 
		  String outPic =RandomUtil.get32RandomUUID() + ext; 
		  mypic.compressPic(inDir, outDir,inPic, outPic,1435, 1080,null, false); }
	 */
}
