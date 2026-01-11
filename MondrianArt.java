//Jonas Himawan
//5/14/2025
//CSE 123
//C2: Mondrian Art
//TA: Cady Xia
//The Mondrian class generates abstract artwork inspired by the Piet Mondrian's style.
//The Mondrian object can produce two types of artwork, a basic one that includes a canvas of 
//random colors and random rectangles and a complex one consisting of a colored canvas of 
//random squares rectangles, where the top left is mostly white, the top right is mostly cyan,
//the bottom left is mostly yellow, and the bottom right is mostly red. Each piece of artwork 
//is unique.
import java.util.*;
import java.awt.*;
import java.util.Random;

public class Mondrian {
    private static final Random rand = new Random();
    private static final Color[] palette = {Color.CYAN, Color.RED, Color.WHITE, Color.YELLOW};
    private static final int border = 20;
    private static final int minBoardSize = 300;
    
    //Behavior:
    // - Generates a basic canvas of random rectangles filled with a random color.
    // - Each rectangles height and width is smaller than 1/4 of the canvas's height and width.
    // - The canvas must be at least 300 pixels wide and tall.
    // - The pixels in the canvas cannot be null.
    //Exceptions:
    // - Throws an IllegalArgumentException if pixels is null.
    // - Throws an IllegalArgumentException if the length or width of pixels is less than 300.
    //Returns:
    // - None
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    public void paintBasicMondrian(Color[][] pixels){
        notAllowed(pixels);
        paintMondrian(pixels, 1, pixels[0].length -1, 1, pixels.length - 1, false);
    }
    
    //Behavior:
    // - Helper method used to create the artwork. 
    // - Helps create both mondrian artworks, the basic one and the complex one.
    // - Generates random rectangles within the canvas where the rectangles height and width 
    //   is smaller than 1/4 of the canvas's height and width.
    //Exceptions:
    // - None
    //Returns:
    // - None
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    // - int x1: The left boundary of the rectangular region to be processed.
    // - int x2: The right boundary of the rectangular region to be processed.
    // - int y1: The top boundary of the rectangular region to be processed.
    // - int y2: The bottom boundary of the rectangular region to be processed.
    // - boolean isComplex: Indicates whether the artwork being generated is complex or basic.
    private void paintMondrian(Color[][] pixels, int x1, int x2, int y1, int y2, boolean isComplex){
        int height = pixels.length;
        int width = pixels[0].length;
        int xDiff = x2 - x1;
        int yDiff = y2 - y1;
        int quarterHeight = height / 4;
        int quarterWidth = width / 4;
        
        if (yDiff < quarterHeight && xDiff < quarterWidth){ 
            fill(pixels, x1, x2, y1, y2, isComplex);
        }
        if (xDiff >= quarterWidth && yDiff >= quarterHeight) {
            int randX = randNum(x1, x2);
            int randY = randNum(y1, y2);

            paintMondrian(pixels, x1, randX, y1, randY, isComplex);
            paintMondrian(pixels, randX, x2, y1, randY, isComplex);
            paintMondrian(pixels, x1, randX, randY, y2, isComplex);
            paintMondrian(pixels, randX, x2, randY, y2, isComplex);
        } else if (xDiff >= quarterWidth) {
            int randX = randNum(x1, x2);

            paintMondrian(pixels, x1, randX, y1, y2, isComplex);
            paintMondrian(pixels, randX, x2, y1, y2, isComplex);
        } else if (yDiff >= quarterHeight) {
            int randY = randNum(y1, y2);

            paintMondrian(pixels, x1, x2, y1, randY, isComplex);
            paintMondrian(pixels, x1, x2, randY, y2, isComplex);
        } 
    }

    //Behavior:
    // - Helper method that returns a random number for creating the random rectangles.
    // - This helper method makes sure that the rectangles aren't creates where they are
    //   to close to the edges.
    //Exceptions:
    // - None.
    //Returns:
    // - An integer representing a random number used to create the random rectangles.
    //Parameters:
    // - int low: The lower bound of the range of the random number.
    // - int high: The upper bound of the range of the random number.
    private int randNum(int low, int high){
        if(high - low <= border){
            return (low + high) / 2;
        } 
        int randomNum = rand.nextInt(high - low - border) + low + border/2;
        return randomNum;
    }
    
    //Behavior:
    // - Generates a complex canvas of random rectangles filled with colors so that
    //   the top left of the canvas is mostly white, the top right is mostly cyan, 
    //   the bottom left is mostly yellow, and the bottom right is mostly red.
    // - Each rectangles height and width is smaller than 1/4 of the canvas's height and width.
    // - The canvas must be at least 300 pixels wide and tall.
    // - The pixels in the canvas cannot be null.
    //Exceptions:
    // - Throws an IllegalArgumentException if pixels is null.
    // - Throws an IllegalArgumentException if the length or width of pixels is less than 300.
    //Returns:
    // - None
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    public void paintComplexMondrian(Color[][] pixels){
        notAllowed(pixels);
        paintMondrian(pixels, 1, pixels[0].length -1, 1, pixels.length - 1, true);
    }

    //Behavior:
    // - Helper method used to fill the color of each rectangle inside the Mondrian artwork.
    // - If the artwork is basic, it fills each rectangle with white.
    // - If the artwork is complex, it fills each rectangle with the color depending on 
    //   its location in the canvas. If the rectangle is in the top left of the canvas 
    //   it will most likely be white, the top right is mostly cyan, the bottom left is 
    //   mostly yellow, and the bottom right is mostly red.
    //Exceptions:
    // - None.
    //Returns:
    // - None.
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    // - int x1: The left boundary of the rectangular region to be processed.
    // - int x2: The right boundary of the rectangular region to be processed.
    // - int y1: The top boundary of the rectangular region to be processed.
    // - int y2: The bottom boundary of the rectangular region to be processed.
    // - boolean isComplex: Indicates whether the artwork being generated is complex or basic.
    private void fill(Color[][] pixels, int x1, int x2, int y1, int y2, boolean isComplex){
        Color complexColor = cornerColor(pixels, x1, x2, y1, y2);
        Color chosen = randomMondrianColor();
        for (int i = y1+1; i < y2-1; i++) {
            for (int j = x1+1; j < x2-1; j++) {
                if(isComplex){
                    pixels[i][j] = complexColor;
                } else {
                    pixels[i][j] = chosen;
                }
            }
        }
    }

    //Behavior:
    // - Helper method that returns the color for each rectangle inside
    //   the complex artwork.
    // - Determines the color based off its location on the canvas.
    // - If the rectangle is in the top left of the canvas it will most likely be white,
    //   the top right is mostly cyan, the bottom left is mostly yellow, and the bottom 
    //   right is mostly red.
    //Exceptions:
    // - None
    //Returns:
    // - The color for each rectangle inside the complex artwork.
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    // - int x1: The left boundary of the rectangular region to be processed.
    // - int x2: The right boundary of the rectangular region to be processed.
    // - int y1: The top boundary of the rectangular region to be processed.
    // - int y2: The bottom boundary of the rectangular region to be processed.
    private Color cornerColor(Color[][] pixels, int x1, int x2, int y1, int y2){
        int halfWid = pixels[0].length / 2;
        int halfHeight = pixels.length / 2;
        int randomNum = rand.nextInt(4);
        Color chosen = randomMondrianColor();

        //if top left, 3:1 odds white
        if(x2 <= halfWid && y2 <= halfHeight){
            if (randomNum < 3){
                return Color.WHITE;
            } 
        }

        //if top right, 3:1 odds cyan
        if(x1 >= halfWid && y2 <= halfHeight){
           if (randomNum < 3){
                return Color.CYAN;
            } 
        }

        //if bottom left, 3:1 odds yellow
        if(x2 <= halfWid && y1 >= halfHeight){
            if (randomNum < 3){
                return Color.YELLOW;
            } 
        }

        //if bottom right, 3:1 odds red
        if(x1 >= halfWid && y1 >= halfHeight){
            if (randomNum < 3){
                return Color.RED;
            }
        }
        return chosen;
    }

    //Behavior:
    // - Helper method used for the complex artwork that generates a random color
    //   out of the 4 options, cyan, red, white, and yellow.
    // - Each color has the same probability of being chosen.
    //Exceptions:
    // - None.
    //Returns:
    // - A random color that's either cyan, red, white, or yellow.
    //Parameters:
    // - None.
    private Color randomMondrianColor() {
        return palette[rand.nextInt(palette.length)];
    }

    //Behavior:
    // - Throws the exceptions for each mondrian artwork.
    // - Each mondrian artwork must be at least 300 pixels wide and tall.
    // - The pixels in the artwork cannot be null.
    //Exceptions:
    // - Throws an IllegalArgumentException if pixels is null.
    // - Throws an IllegalArgumentException if the length or width of pixels is less than 300.
    //Returns:
    // - None
    //Parameters:
    // - Color[][] pixels: A 2D array representing the canvas. Each element inside the 2d array
    //   represents a pixel.
    private void notAllowed(Color[][] pixels){
        if(pixels == null){
            throw new IllegalArgumentException("Pixels cannot be null");
        }
        if(pixels.length < minBoardSize || pixels[0].length < minBoardSize){
            throw new IllegalArgumentException("Length or width of pixels is too small.");
        }
    }
}
