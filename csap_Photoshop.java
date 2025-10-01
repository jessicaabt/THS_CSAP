import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.lang.reflect.*;
/**
 * This Photoshop class is a simple image editor program
 * For this assignment, you need to fill in the blank
 * methods at the end that say "YOUR CODE GOES HERE!"
 */
public class Photoshop implements ActionListener {
    /**
     * These class variables are accessible in every method
     * You'll only need to modify image for this assignment
     */
    private Color[][] image;
    private JFrame frame;
    private JMenuItem[] menuItems;
    private ImagePanel imagePanel;

    /**
     * This main method just calls the constructor
     */
    public static void main(String[] args) throws Exception {
        new Photoshop();
    }

    /**
     * This constructor loads an image and makes the window
     */
    public Photoshop() throws Exception {
        File file = new File("STARTINGPICTURE.jpg");
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
        }
        catch(Exception e) {
            bufferedImage = new BufferedImage(500,500,1);
        }
        frame = new JFrame("My Photoshop Program");
        frame.setLayout(new BorderLayout());
        this.image = convertToColorArray(bufferedImage);
        this.setupMenuBar();
        this.imagePanel = new ImagePanel();
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
    }

    /**
     * This ImagePanel class displays the current picture
     */
    public class ImagePanel extends JPanel {
        /**
         * This constructor sets up the ImagePanel
         */
        public ImagePanel() {
            setBackground(new Color(0,0,0));
            setPreferredSize(new Dimension(image[0].length, image.length));
        }

        /**
         * This paintComponent method draws the image on the panel
         * @param g The graphics context for this JPanel
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setPreferredSize(new Dimension(image[0].length, image.length));
            BufferedImage bufferedImage = convertToBufferedImage(image);
            g.drawImage(bufferedImage, 0, 0, this);
        }
    }

    /**
     * This convertToColorArray method converts BufferedImages to Color[][]
     * @param bufferedImage The BufferedImage
     * @return The 2D Color Array
     */
    public Color[][] convertToColorArray(BufferedImage bufferedImage) {
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        Color[][] image = new Color[height][width];
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                image[r][c] = new Color(bufferedImage.getRGB(c,r));
            }
        }
        return image;
    }

    /**
     * This convertToBufferedImage method converts Color[][] to BufferedImages
     * @param image The 2D Color Array
     * @return The BufferedImage
     */
    public BufferedImage convertToBufferedImage(Color[][] image) {
        int height = image.length;
        int width = image[0].length;
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                bufferedImage.setRGB(c,r,image[r][c].getRGB());
            }
        }
        return bufferedImage;
    }

    /**
     * This setupMenuBar method initializes the top menu bar
     */
    public void setupMenuBar() throws Exception {
        JMenuBar menuBar = new JMenuBar();
        Font font = new Font("Arial", Font.PLAIN, 20);

        String[] menuNames = {"File", "Edit", "Bonus"};
        JMenu[] menus = new JMenu[3];
        for(int i=0; i<menus.length; i++) {
            menus[i] = new JMenu(menuNames[i]);
            String firstLetter = menuNames[i].substring(0,1);
            Field field = KeyEvent.class.getField("VK_"+firstLetter);
            menus[i].setMnemonic(field.getInt(null));
            menus[i].setFont(font);
            menuBar.add(menus[i]);
        }

        String[] menuItemNames = {"Save Picture", "Load Picture",
                "Increase Brightness", "Decrease Brightness",
                "Increase Contrast", "Decrease Contrast",
                "Flip Horizontally", "Flip Vertically",
                "Rotate Clockwise", "Rotate Counterclockwise",
                "Black and White", "Gray Scale",
                "My Bonus Filter #1", "My Bonus Filter #2"};
        menuItems = new JMenuItem[14];
        String usedMneumonics = "";
        for(int i=0; i<menuItems.length; i++) {
            menuItems[i] = new JMenuItem(menuItemNames[i]);
            menuItems[i].setFont(font);
            menuItems[i].addActionListener(this);
            String firstLetter = menuItemNames[i].substring(0,1);
            Field field = KeyEvent.class.getField("VK_"+firstLetter);
            menuItems[i].setMnemonic(field.getInt(null));
            usedMneumonics += firstLetter;
            if(i < 2)       {menus[0].add(menuItems[i]);}
            else if(i < 12) {menus[1].add(menuItems[i]);}
            else            {menus[2].add(menuItems[i]);}
        }

        frame.setJMenuBar(menuBar);
    }

    /**
     * This actionPerformed method handles JButton click events
     * @param event The ActionEvent that was generated
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == menuItems[0]) {save();}
        if(event.getSource() == menuItems[1]) {load();}
        if(event.getSource() == menuItems[2]) {increaseBrightness();}
        if(event.getSource() == menuItems[3]) {decreaseBrightness();}
        if(event.getSource() == menuItems[4]) {increaseContrast();}
        if(event.getSource() == menuItems[5]) {decreaseContrast();}
        if(event.getSource() == menuItems[6]) {flipHorizontally();}
        if(event.getSource() == menuItems[7]) {flipVertically();}
        if(event.getSource() == menuItems[8]) {rotateClockwise();}
        if(event.getSource() == menuItems[9]) {rotateCounterclockwise();}
        if(event.getSource() == menuItems[10]){blackAndWhite();}
        if(event.getSource() == menuItems[11]){grayScale();}
        if(event.getSource() == menuItems[12]){bonusFilter1();}
        if(event.getSource() == menuItems[13]){bonusFilter2();}
        Dimension dim = new Dimension(image[0].length, image.length);
        imagePanel.setPreferredSize(dim);
        imagePanel.repaint();
        frame.pack();
    }

    /**
     * This save method saves the current image into a specified file
     */
    public void save() {
        JFileChooser jfc = new JFileChooser();
        File currentFolder = new File(System.getProperty("user.dir"));
        jfc.setCurrentDirectory(currentFolder);
        int returnVal = jfc.showSaveDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            BufferedImage bufferedImage = convertToBufferedImage(image);
            try {
                ImageIO.write(bufferedImage, "jpg", file);
            }
            catch(Exception e) {
                System.out.println("Error, could not save!");
                e.printStackTrace();
            }
        }
    }

    /**
     * This load method gets a picture from a file into the program
     */
    public void load() {
        JFileChooser jfc = new JFileChooser();
        File currentFolder = new File(System.getProperty("user.dir"));
        jfc.setCurrentDirectory(currentFolder);
        int result = jfc.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                image = convertToColorArray(bufferedImage);
            }
            catch(Exception e) {
                System.out.println("Error, could not load!");
                e.printStackTrace();
            }
        }
    }

    /**
     * This increaseBrightness method should increase the brightness
     * of the picture by adding 5 to the red, green, and blue values. 
     * Do not let a value be larger than the max value of 255 though!
     */
    public void increaseBrightness() {
        // THIS METHOD IS ALREADY DONE TO HELP YOU GET STARTED
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                red += 5;
                green += 5;
                blue += 5;
                if(red > 255)   {red = 255;}
                if(green > 255) {green = 255;}
                if(blue > 255)  {blue = 255;}
                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This decreaseBrightness method should decrease the brightness
     * of the picture by subtracting 5 from the red, green, and blue values. 
     * Do not let a value be smallet than the min value of 0 though!
     */
    public void decreaseBrightness() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                red -= 5;
                green -= 5;
                blue -=  5;
                if(red < 0)   {red = 0;}
                if(green < 0) {green = 0;}
                if(blue < 0)  {blue = 0;}
                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This increaseContrast method should look at each color value
     * and add 5 if the average is >= 127 and subtract 5 from it otherwise.
     * Remember to keep color values in the range of 0 to 255!
     */
    public void increaseContrast() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                int avg = ((red + green + blue) / 3);

                if(avg >= 127){
                    red += 5;
                    blue += 5;
                    green += 5;
                    if(red >= 255){
                        red = 255;
                    }
                    if(blue >= 255){
                        blue = 255;
                    }
                    if(green >= 255){
                        green = 255;
                    }
                }
                else{
                    red -= 5;
                    green -= 5;
                    blue -= 5;
                    if(red <= 0){
                        red = 0;
                    }
                    if(blue <= 0){
                        blue = 0;
                    }
                    if(green <= 0){
                        green = 0;
                    }
                }

                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This decreaseContrast method should average RGB values and
     * subtract 5 if the average is >= 127 and add 5 otherwise.
     * Remember to keep RGB values between 0 and 255!
     */
    public void decreaseContrast() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                int avg = ((red + green + blue) / 3);

                if(avg >= 127){
                    red -= 5;
                    blue -= 5;
                    green -= 5;
                    if(red <= 0){
                        red = 0;
                    }
                    if(blue <= 0){
                        blue = 0;
                    }
                    if(green <= 0){
                        green = 0;
                    }
                }
                else{
                    red += 5;
                    green += 5;
                    blue += 5;
                    if(red >= 255){
                        red = 255;
                    }
                    if(blue >= 255){
                        blue = 255;
                    }
                    if(green >= 255){
                        green = 255;
                    }
                }

                image[r][c] = new Color(red, green, blue);
            }
        }     
    }

    /**
     * This flipHorizontally method should flip the image horizontally.
     * To do this, you need to reverse each row in the 2D Color Array.
     * Use a temp color and three lines of code to swap values.
     */
    public void flipHorizontally() {
        //YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length / 2; c++) {
                Color temp = image[r][c];
                image[r][c] = image[r][image[r].length - 1 - c];
                image[r][image[r].length - 1 - c] = temp;
            }
        }
    }

    /**
     * This flipVertically method should flip the image vertically.
     * To do this, you could swap the first row with the last, then
     * swap the second row with the second to last, and so forth.
     * This process could use just one for-loop and a Color[] temp.
     */
    public void flipVertically() {
        // YOUR CODE GOES HERE
        for(int c=0; c<image[0].length; c++) {
            for(int r=0; r<image.length / 2; r++) {
                Color temp = image[r][c];
                image[r][c] = image[image.length - 1 - r][c];
                image[image.length - 1 - r][c] = temp;
            }
        }
    }

    /**
     * This rotateClockwise method should rotate the image
     * 90 degrees clockwise. To do this, make a new Color[][]
     * and fill it up with the appropriate data. Afterwards,
     * make sure to reassign image as the new 2D array.
     */
    public void rotateClockwise() {
        // YOUR CODE GOES HERE
        Color[][] newPic = new Color[image[0].length][image.length];
        for(int r=0; r<newPic.length; r++) {
            int r2 = newPic[r].length - 1;
            for(int c=0; c<newPic[r].length; c++) {
                newPic[r][c] = image[r2][r];
                r2--;
            }
        }
        image = newPic;
    }

    /**
     * This rotateCounterclockwise method should rotate the image
     * 90 degrees counterclockwise. To do this, make a new Color[][]
     * and fill it up with the appropriate data. Afterwards,
     * make sure to reassign image as the new 2D array.
     */
    public void rotateCounterclockwise() {
        // YOUR CODE GOES HERE
        Color[][] newPic = new Color[image[0].length][image.length];
        for(int r=0; r<image.length; r++) {
            int c2 = image[r].length - 1;
            for(int c=0; c<image[r].length; c++) {
                newPic[c][r] = image[r][c2 - c];
            }
        }
        image = newPic;
    }

    /**
     * This blackAndWhite method should change the colors to only
     * black or white. Average each color's RGB values. If the average
     * is >= 127.0 then make them all 255, otherwise make them all 0.
     */
    public void blackAndWhite() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                double avg = (red + green + blue) / 3.0;

                if(avg >= 127.0){
                    red = 255;
                    blue = 255;
                    green = 255;
                }
                else{
                    red = 0;
                    blue = 0;
                    green = 0;
                }

                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This grayScale method should change the colors to only shades
     * of gray. Average together each color's RGB values and assign
     * red, green, and blue all the average so they are the same value.
     */
    public void grayScale() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                double avg = (red + green + blue) / 3.0;

                red = (int)avg;
                blue = (int)avg;
                green = (int)avg;

                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This bonusFilter1 method can do whatever you want it to do.
     * Brainstorm an idea! It can be something new and silly, or a
     * common algorithm like inverting colors, blurring, sharpening,
     * swirling, mirroring, shrinking, posterizing, or even deep frying!
     */
    public void bonusFilter1() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                int temp = green;

                red = green;
                green = blue;
                blue = temp;

                image[r][c] = new Color(red, green, blue);
            }
        }
    }

    /**
     * This bonusFilter2 method can do whatever you want it to do.
     * Brainstorm an idea! It can be something new and silly, or a
     * common algorithm like inverting colors, blurring, sharpening,
     * swirling, mirroring, shrinking, posterizing, or even deep frying!
     */
    public void bonusFilter2() {
        // YOUR CODE GOES HERE
        for(int r=0; r<image.length; r++) {
            for(int c=0; c<image[r].length; c++) {
                int red = image[r][c].getRed();
                int green = image[r][c].getGreen();
                int blue = image[r][c].getBlue();
                int temp = red;

                red = blue;
                blue = green;
                green = temp;

                image[r][c] = new Color(red, green, blue);
            }
        }
    }
}