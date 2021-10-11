package PartI;
import java.io.*;
import java.util.ArrayList;

import PartI.shapes.*;

/* your tasks:
 * create a class called ShapeException
 * createShape should throw a ShapeException
 * in main(), you should catch the ShapeException
 * 
 */
public class ReadShapeFile {

	public static GeometricObject createShape(String shapeName) throws IOException, ShapeException{
		
		/* if shapeName is "Circle" return Circle();
		 * if shapeName is "Square" return Square();
		 * if shapeName is "Rectangle" return Rectangle();
		 * if it is not any one of these, it should throw
		 * a ShapeException
		 */

		if (shapeName.equals("Circle")) return new Circle();
		else if (shapeName.equals("Square")) return new Square();
		else if (shapeName.equals("Rectangle")) return new Rectangle();
		else throw new ShapeException("fuck");
	}
	
	public static void main(String[] args) throws IOException, ShapeException{
		ArrayList<GeometricObject> shapeList = new ArrayList<GeometricObject>();
		File f = new File("./src/shapes.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String inString = br.readLine();

		/* create a loop to read the file line-by-line */

		while (inString != null) {
			try {
				GeometricObject shape = createShape(inString);
				shapeList.add(shape);
			} catch (ShapeException | IOException e) {
				System.err.println("Cannot create shape: " + e.getMessage());
			}
			inString = br.readLine();
		}

		System.out.println(shapeList);
		br.close();
	}
}
