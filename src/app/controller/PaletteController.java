package app.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

public class PaletteController {
    private static final int DEFAULT_QUALITY = 10;
    private static final boolean DEFAULT_IGNORE_WHITE = true;

    public static int[] getColor(BufferedImage sourceImage) {
        int[][] palette = getPalette(sourceImage, 5);
        if(palette == null) {
            return null;
        }
        int[] dominantColor = palette[0];
        return dominantColor;
    }

    public static int[] getColor(BufferedImage sourceImage, int quality, boolean ignoreWhite) {
        int[][] palette = getPalette(sourceImage, 9, quality, ignoreWhite);
        if(palette == null) {
            return null;
        }
        int[] dominantColor = palette[0];
        return dominantColor;
    }

    public static int[][] getPalette(BufferedImage sourceImage, int colorCount){
        MMCQ.CMap cmap = getColorMap(sourceImage, colorCount);
        if(cmap == null) {
            return null;
        }
        return cmap.palette();
    }

    public static int[][] getPalette(BufferedImage sourceImage, int colorCount, int quality, boolean ignoreWhite){
        MMCQ.CMap cmap = getColorMap(sourceImage, colorCount, quality, ignoreWhite);
        if(cmap == null) {
            return null;
        }
        return cmap.palette();
    }

    public static MMCQ.CMap getColorMap(BufferedImage sourceImage, int colorCount) {
        return getColorMap(sourceImage, colorCount, DEFAULT_QUALITY, DEFAULT_IGNORE_WHITE);
    }

    public static MMCQ.CMap getColorMap(BufferedImage sourceImage, int colorCount, int quality, boolean ignoreWhite) {
        if(quality< 1) {
            throw new IllegalArgumentException("Specified quality shoulb be grater than 0");
        }
        int [][] pixelArray;
        switch(sourceImage.getType()) {
            case BufferedImage.TYPE_3BYTE_BGR:
            case BufferedImage.TYPE_4BYTE_ABGR:
                pixelArray = getPixelsFast(sourceImage, quality, ignoreWhite);
                break;
            default:
                pixelArray = getPixelsSlow(sourceImage, quality, ignoreWhite);
        }
        MMCQ.CMap cmap = MMCQ.quantize(pixelArray, colorCount);
        return cmap;
    }

    private static int[][] getPixelsFast(
            BufferedImage sourceImage,
            int quality,
            boolean ignoreWhite) {
        DataBufferByte imageData = (DataBufferByte) sourceImage.getRaster().getDataBuffer();
        byte[] pixels = imageData.getData();
        int pixelCount = sourceImage.getWidth() * sourceImage.getHeight();

        int colorDepth;
        int type = sourceImage.getType();
        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                colorDepth = 3;
                break;

            case BufferedImage.TYPE_4BYTE_ABGR:
                colorDepth = 4;
                break;

            default:
                throw new IllegalArgumentException("Unhandled type: " + type);
        }

        int expectedDataLength = pixelCount * colorDepth;
        if (expectedDataLength != pixels.length) {
            throw new IllegalArgumentException(
                    "(expectedDataLength = " + expectedDataLength + ") != (pixels.length = "
                            + pixels.length + ")");
        }

        // Store the RGB values in an array format suitable for quantize function

        // numRegardedPixels must be rounded up to avoid an ArrayIndexOutOfBoundsException if all
        // pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;
        int[][] pixelArray = new int[numRegardedPixels][];
        int offset, r, g, b, a;

        // Do the switch outside of the loop, that's much faster
        switch (type) {
            case BufferedImage.TYPE_3BYTE_BGR:
                for (int i = 0; i < pixelCount; i += quality) {
                    offset = i * 3;
                    b = pixels[offset] & 0xFF;
                    g = pixels[offset + 1] & 0xFF;
                    r = pixels[offset + 2] & 0xFF;

                    // If pixel is not white
                    if (!(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                        pixelArray[numUsedPixels] = new int[] {r, g, b};
                        numUsedPixels++;
                    }
                }
                break;

            case BufferedImage.TYPE_4BYTE_ABGR:
                for (int i = 0; i < pixelCount; i += quality) {
                    offset = i * 4;
                    a = pixels[offset] & 0xFF;
                    b = pixels[offset + 1] & 0xFF;
                    g = pixels[offset + 2] & 0xFF;
                    r = pixels[offset + 3] & 0xFF;

                    // If pixel is mostly opaque and not white
                    if (a >= 125 && !(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                        pixelArray[numUsedPixels] = new int[] {r, g, b};
                        numUsedPixels++;
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("Unhandled type: " + type);
        }

        // Remove unused pixels from the array
        return Arrays.copyOfRange(pixelArray, 0, numUsedPixels);
    }

    /**
     * Gets the image's pixels via BufferedImage.getRGB(..). Slow, but the fast method doesn't work
     * for all color models.
     *
     * @param sourceImage
     *            the source image
     * @param quality
     *            1 is the highest quality settings. 10 is the default. There is a trade-off between
     *            quality and speed. The bigger the number, the faster the palette generation but
     *            the greater the likelihood that colors will be missed.
     * @param ignoreWhite
     *            if <code>true</code>, white pixels are ignored
     *
     * @return an array of pixels (each an RGB int array)
     */
    private static int[][] getPixelsSlow(BufferedImage sourceImage, int quality, boolean ignoreWhite) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        int pixelCount = width * height;

        // numRegardedPixels must be rounded up to avoid an ArrayIndexOutOfBoundsException if all
        // pixels are good.
        int numRegardedPixels = (pixelCount + quality - 1) / quality;

        int numUsedPixels = 0;

        int[][] res = new int[numRegardedPixels][];
        int r, g, b;

        for (int i = 0; i < pixelCount; i += quality) {
            int row = i / width;
            int col = i % width;
            int rgb = sourceImage.getRGB(col, row);

            r = (rgb >> 16) & 0xFF;
            g = (rgb >> 8) & 0xFF;
            b = (rgb) & 0xFF;
            if (!(ignoreWhite && r > 250 && g > 250 && b > 250)) {
                res[numUsedPixels] = new int[] {r, g, b};
                numUsedPixels++;
            }
        }

        return Arrays.copyOfRange(res, 0, numUsedPixels);
    }

}
