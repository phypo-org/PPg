	//------------------------------------------------


int kernelWidth = 3;
int kernelHeight = 3;

int xOffset = (kernelWidth – 1) / 2;
int yOffset = (kernelHeight – 1) / 2;

BufferedImage newSource = new BufferedImage(
  sourceImage.getWidth() + kernelWidth – 1,
  sourceImage.getHeight() + kernelHeight – 1,
  BufferedImage.TYPE_INT_ARGB);
Graphics2D g2 = newSource.createGraphics();
g2.drawImage(sourceImage, xOffset, yOffset, null);
g2.dispose();

ConvolveOp op = new ConvolveOp(kernel,
    ConvolveOp.EDGE_NO_OP, null);
dstImage = op.filter(newSource, null);




BufferedImage dstImage = null;
float[] sharpen = new float[] {
     0.0f, -1.0f, 0.0f,
    -1.0f, 5.0f, -1.0f,
     0.0f, -1.0f, 0.0f
};
Kernel kernel = new Kernel(3, 3, sharpen);
ConvolveOp op = new ConvolveOp(kernel);
dstImage = op.filter(sourceImage, null);




float[] kernel = new float[blurMagnitude * blurMagnitude];
for (int i = 0, n = kernel.length; i < n; i++) {
    kernel[i] = 1f / n;
}
 
ConvolveOp blur = new ConvolveOp(new Kernel(blurMagnitude, blurMagnitude,
    kernel), ConvolveOp.EDGE_NO_OP, null);
bufferedImage = blur.filter(bufferedImage, null);
 
graphics.drawImage(bufferedImage, 0, 0, null);
 
bufferedImage = null;
graphics = null;




      private final float[] IDENTITY = {0, 0, 0,
                                        0, 1, 0,
                                        0, 0, 0};

      private final float[] EDGE = {0, 1, 0,
                                    1, 0, 1,
                                    0, 1, 0};

      private final float[] CORNER = {1, 0, 1,
                                      0, 0, 0,
                                      1, 0, 1};



      private Kernel getKernel(
        int corner, int edge, int identity) {
         float[] kernel = new float[9];
         int sum = corner * 4 + edge * 4 + identity;
         if (sum == 0) sum = 1;
         for (int i = 0; i < 9; i++) {
           kernel[i] = (corner * CORNER[i]
             + edge * EDGE[i]
             + identity * IDENTITY[i]) / sum;
         }
        return new Kernel(3, 3, kernel);
      }



After you have the kernel, you perform the convolution much as before.

   void convolveImage(Kernel kernel) {
      BufferedImageOp convolve
        = new ConvolveOp(kernel);
      buffImage = convolve.filter(buffImage, null);
      repaint();
   }






      private final float[] IDENTITY = {0, 0, 0,
                                        0, 1, 0,
                                        0, 0, 0};

      private final float[] EDGE = {0, 1, 0,
                                    1, 0, 1,
                                    0, 1, 0};

      private final float[] CORNER = {1, 0, 1,
                                      0, 0, 0,
                                      1, 0, 1};

