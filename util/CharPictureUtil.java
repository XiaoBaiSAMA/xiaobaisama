package com.xiaobaisama;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by xiaobai on 2018/8/13.
 */
public class CharPictureUtil {
    private CharPictureUtil(){}

    /**
     * 链式调用实例化入口方法
     * @return
     */
    public static CharPictureUtil getSelf(){
        return new CharPictureUtil();
    }

    private BufferedImage bi;
    private int maxY;
    private int maxX;

    /**
     * 初始化图片对象
     * @return
     * @throws IOException
     */
    public CharPictureUtil initBi(File file) throws IOException {
        bi = ImageIO.read(file);
        maxY = bi.getHeight();
        maxX = bi.getWidth();
        return this;
    }


    private Color[][] colorss;

    /**
     * 初始化原始颜色二维数组
     * @return
     */
    public CharPictureUtil initColorss() {
        colorss = new Color[maxY][maxX];
        for (int y = 0 ; y < colorss.length ; y++){
            for (int x = 0 ; x < colorss[y].length ; x++){
                colorss[y][x] = new Color(bi.getRGB(x,y));
            }
        }
        return this;
    }


    private int space = 10;

    /**
     * 设置字符大小
     * @param space
     * @return
     */
    public CharPictureUtil initSpace(int space) {
        this.space = space;
        return this;
    }

    /**
     * 设置平均颜色二维数组
     * @return
     */
    public CharPictureUtil initAvgColorss() {
        maxY = maxY / space;
        maxX = maxX / space;

        Color[][] oldColorss = colorss;
        colorss = new Color[maxY][maxX];
        for (int y = 0 ; y < colorss.length ; y++){
            for (int x = 0 ; x < colorss[y].length ; x++){
                Color[] colors = new Color[space * space];
                for (int i = 0 ; i < colors.length ; i++){
                    colors[i] = oldColorss[y * space + i / space][x * space + i % space];
                }
                colorss[y][x] = getAvgColor(colors);
            }
        }
        return this;
    }

    /**
     * 私有化方法，用来求平均颜色
     * @param colors
     * @return
     */
    private Color getAvgColor(Color[] colors) {
        int r = 0, g = 0, b = 0;
        for (Color c : colors){
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }
        r = r / colors.length;
        g = g / colors.length;
        b = b / colors.length;
        return new Color(r,g,b);
    }

    private Font font = new Font("黑体",Font.BOLD,space);

    /**
     * 初始化字体
     * @param font
     * @return
     */
    public CharPictureUtil initFont(Font font) {
        this.font = font;
        return this;
    }

    /**
     * 私有化方法，获取指定字符的黑色占比
     * @param s
     * @return
     */
    private double getCharAvg(String s) {
        BufferedImage bi = new BufferedImage(space,space,BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,space,space);
        g.setColor(Color.BLACK);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        Rectangle rect = new Rectangle(0,0,space,space);
        int x = rect.x + (rect.width - metrics.stringWidth(s)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(s,x,y);

        double count = 0;
        for (int y2 = 0 ; y2 < space ; y2++){
            for (int x2 = 0 ; x2 < space ; x2++){
                if(bi.getRGB(x2,y2) == Color.BLACK.getRGB()){
                    count++;
                }
            }
        }
        return count / (space*space);
    }

    /**
     * 内部类实现排序接口
     */
    class Char implements Comparable<Char>{
        String s;
        double avg;
        Char(String s,double avg) {
            this.s = s;
            this.avg = avg;
        }

        @Override
        public int compareTo(Char o) {
            return Double.compare(avg,o.avg);
        }

        @Override
        public String toString() {
            return "Char{" + "s='" + s + '\'' + ", avg=" + avg + '}';
        }
    }

    private Char[] charAvgs;

    /**
     * 初始化字符序列
     * @param tpl
     * @return
     */
    public CharPictureUtil initCharAvgs(String tpl){
        String[] strArr = tpl.split("");
        charAvgs = new Char[strArr.length];
        for(int i=0;i<strArr.length;i++){
            charAvgs[i] = new Char(strArr[i],getCharAvg(strArr[i]));
        }
        Arrays.sort(charAvgs);
        return this;
    }

    /**
     * 扩大颜色序列的方差，使这个序列拉长，让不同的字符有更大的几率被选择
     * @return
     */
    public CharPictureUtil growCharAvgs(){
        double[] temp = new double[charAvgs.length-1];
        for(int i=0;i<charAvgs.length-1;i++){
            double pro = (charAvgs[i+1].avg - charAvgs[i].avg) / (charAvgs[charAvgs.length-1].avg - charAvgs[0].avg);
            temp[i]= pro;
        }
        charAvgs[0].avg = 0;
        for(int i=1;i<charAvgs.length;i++){
            charAvgs[i].avg = temp[i-1] + charAvgs[i-1].avg;
        }
        return this;
    }

    /**
     * 私有化方法，从字符序列中找到相似字符
     * @param avg
     * @return
     */
    private Char findChar(double avg){
        double diff = Math.abs(charAvgs[0].avg - avg);
        int i=1;
        for(;i<charAvgs.length;i++){
            if(diff >= Math.abs(charAvgs[i].avg - avg)){
                diff = Math.abs(charAvgs[i].avg - avg);
            }else{
                break;
            }
        }
        i--;
        return charAvgs[i];
    }

    /**
     * 获取一个颜色的灰度值
     * @param color
     * @return
     */
    private double getAvgOfBlack(Color color){
        double sum = 0;
        sum += color.getRed();
        sum += color.getGreen();
        sum += color.getBlue();
        return 1 - (sum / (255*3));
    }

    /**
     * 获取图片的字符二维数组
     * @return
     */
    private Char[][] getCharss(){
        Char[][] charss = new Char[maxY][maxX];
        for(int y=0;y<maxY;y++){
            for(int x=0;x<maxX;x++){
                charss[y][x] = findChar(getAvgOfBlack(colorss[y][x]));
            }
        }
        return charss;
    }

    /**
     * 保存生成的字符图片
     * @param file
     * @param ignoreColor
     * @return
     * @throws IOException
     */
    public CharPictureUtil save(File file,boolean ignoreColor) throws IOException {
        Char[][] charss = getCharss();
        int width = this.bi.getWidth();
        int height = this.bi.getHeight();
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        for(int y=0;y<maxY;y++){
            for(int x=0;x<maxX;x++){
                Rectangle rect = new Rectangle(x*space,y*space,space,space);
                String s = charss[y][x].s;
                int centerX = rect.x + (rect.width - metrics.stringWidth(s)) / 2;
                int centerY = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
                if(!ignoreColor){
                    g.setColor(colorss[y][x]);
                }else{
                    g.setColor(Color.BLACK);
                }
                g.drawString(s,centerX,centerY);
            }
        }
        String fileName = file.getName();
        ImageIO.write(bi,fileName.substring(fileName.lastIndexOf(".")+1),file);
        return this;
    }

    public static void main(String[] args) throws IOException {
        int space = 10;
        Font font = new Font("黑体",Font.BOLD,10);
        CharPictureUtil util = CharPictureUtil.getSelf().initSpace(space).initFont(font).initCharAvgs("夜半清风.ybqf");

        util.initBi(new File("C:\\Users\\XiaoBai\\Desktop\\a.jpg")).initColorss().initAvgColorss().save(new File("C:\\Users\\XiaoBai\\Desktop\\head.png"),true);

    }
}
