package io.stephen.shield.core.properties;

/**
 * @author zhoushuyi
 * @since 2018/4/29
 */
public class ImageCodeProperties extends SmsCodeProperties{

    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
