import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static java.lang.Math.pow;

/**
 * Main class for ray tracing exercise.
 */

public class RayTracer {
    int cntr = 0;

    private int imageWidth;
    private int imageHeight;
    private Settings settings;
    private Camera camera;
    private ArrayList<Material> materials;
    private List<Surface> surfaces;
    private ArrayList<Light> lights;
    private Vector3D upperLeftScreenCorner = null;

    public RayTracer() {
        camera = null;
        settings = null;
        lights = new ArrayList<>();
        surfaces = new ArrayList<>();
        materials = new ArrayList<>();
    }

    /**
     * Runs the ray tracer. Takes scene file, output image file and image size as input.
     */
    public static void main(String[] args) {
        try {

            RayTracer tracer = new RayTracer();

            // Default values:
            tracer.imageWidth = 500;
            tracer.imageHeight = 500;

            if (args.length < 2)
                throw new RayTracerException("Not enough arguments provided. Please specify an input scene file and an output image file for rendering.");

            String sceneFileName = args[0];
            String outputFileName = args[1];

            if (args.length > 3) {
                tracer.imageWidth = Integer.parseInt(args[2]);
                tracer.imageHeight = Integer.parseInt(args[3]);
            }


            // Parse scene file:
            tracer.parseScene(sceneFileName);

            // Render scene:
            tracer.renderScene(outputFileName);

//		} catch (IOException e) {
//			System.out.println(e.getMessage());
        } catch (RayTracerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Parses the scene file and creates the scene. Change this function so it generates the required objects.
     */
    public void parseScene(String sceneFileName) throws IOException, RayTracerException {
        FileReader fr = new FileReader(sceneFileName);

        BufferedReader r = new BufferedReader(fr);
        String line;
        int lineNum = 0;
        System.out.println("Started parsing scene file " + sceneFileName);


        while ((line = r.readLine()) != null) {
            line = line.trim();
            ++lineNum;

            if (line.isEmpty() || (line.charAt(0) == '#')) {  // This line in the scene file is a comment
                continue;
            } else {
                String code = line.substring(0, 3).toLowerCase();
                // Split according to white space characters:
                String[] params = line.substring(3).trim().toLowerCase().split("\\s+");

                if (code.equals("cam")) {
                    // Add code here to parse camera parameters
                    assert params.length == 11 : String.format("cam Expected parameters: 11, Actual: %d", params.length);
                    camera = new Camera(params);
                    System.out.println(String.format("Parsed camera parameters (line %d)", lineNum));
                } else if (code.equals("set")) {
                    // Add code here to parse general settings parameters
                    assert params.length == 5 : String.format("set Expected parameters: 5, Actual: %d", params.length);
                    settings = new Settings(params);
                    System.out.println(String.format("Parsed general settings (line %d)", lineNum));
                } else if (code.equals("mtl")) {
                    // Add code here to parse material parameters
                    assert params.length == 11 : String.format("mtl Expected parameters: 11, Actual: %d", params.length);
                    materials.add(new Material(params));
                    System.out.println(String.format("Parsed material (line %d)", lineNum));
                } else if (code.equals("sph")) {
                    // Add code here to parse sphere parameters
                    assert params.length == 5 : String.format("sph Expected parameters: 5, Actual: %d", params.length);
                    surfaces.add(new Sphere(params));

                    System.out.println(String.format("Parsed sphere (line %d)", lineNum));
                } else if (code.equals("pln")) {
                    // Add code here to parse plane parameters
                    assert params.length == 5 : String.format("pln Expected parameters: 5, Actual: %d", params.length);
                    surfaces.add(new Plane(params));
                    System.out.println(String.format("Parsed plane (line %d)", lineNum));
                } else if (code.equals("cyl")) {
                    // Add code here to parse cylinder parameters
                    assert params.length == 9 : String.format("cyl Expected parameters: 9, Actual: %d", params.length);
                    surfaces.add(new Cylinder(params));
                    System.out.println(String.format("Parsed cylinder (line %d)", lineNum));
                } else if (code.equals("lgt")) {
                    // Add code here to parse light parameters
                    assert params.length == 9 : String.format("lgt Expected parameters: 9, Actual: %d", params.length);
                    lights.add(new Light(params));
                    System.out.println(String.format("Parsed light (line %d)", lineNum));
                } else {
                    System.out.println(String.format("ERROR: Did not recognize object: %s (line %d)", code, lineNum));
                }
            }
        }

        // It is recommended that you check here that the scene is valid,
        // for example camera settings and all necessary materials were defined.
        System.out.println("Finished parsing scene file " + sceneFileName);

    }


    /**
     * Renders the loaded scene and saves it to the specified file location.
     */
    public void renderScene(String outputFileName) {
        System.out.println("Starting to render");
        long startTime = System.currentTimeMillis();
        fixUpVectorGetCameraHeightGetCorner();

        // Create a byte array to hold the pixel data:
        byte[] rgbData = new byte[this.imageWidth * this.imageHeight * 3];

//        Image RayCast(Camera camera, Scene scene, int width, int height) {
//            Image image = new Image(width, height);
        for (int i = 0; i < this.imageWidth; i++) {
            for (int j = 0; j < this.imageHeight; j++) {
                Vector3D ray = ConstructRayTowardsPixel(i, j);
                Color color = calculateColor(0, ray, camera.getPosition(), findSortIntersections(ray));
                setPixelLocationRGB(i, j, rgbData, color);

            }
        }
//            return image;
//        }
        // Put your ray tracing code here!
        //

        long endTime = System.currentTimeMillis();
        Long renderTime = endTime - startTime;

        // The time is measured for your own conveniece, rendering speed will not affect your score
        // unless it is exceptionally slow (more than a couple of minutes)
        System.out.println("Finished rendering scene in " + renderTime.toString() + " milliseconds.");

        // This is already implemented, and should work without adding any code.
        saveImage(this.imageWidth, rgbData, outputFileName);

        System.out.println("Saved file " + outputFileName);

    }

    private Color calculateColor(int recursionLevel, Vector3D ray, Vector3D position, List<Intersection> intersections) {
        System.out.println("calculating color");
        if (recursionLevel == settings.getMaxRecursion() || intersections.isEmpty()) {
            return settings.getBackgroundColor();
        }
        System.out.println(cntr);
        cntr++;
        Color resolvedColor = new Color();
        Intersection firstIntersection = intersections.get(0);
        Vector3D newPosition = firstIntersection.getLocation();
        Vector3D newRay = firstIntersection.getNormal().
                scalarMultiply(ray.dotProduct(firstIntersection.getNormal()) * (-2)).
                add(ray).
                normalize();
        System.out.println(2);

        for (Light light : lights) {
            resolvedColor = Color.sum(resolvedColor, calculateDiffSpecColor(light, firstIntersection));
        }
        resolvedColor = Color.sum(resolvedColor,
                Color.multiplyWithFactor(
                        calculateColor(recursionLevel + 1,
                                newRay,
                                newPosition,
                                findSortIntersections(newRay)),
                        materials.get(firstIntersection.getMaterialIndex()-1).getReflectionColor(),
                        1));
        System.out.println(3);
        return resolvedColor;
    }

    private Color calculateDiffSpecColor(Light light, Intersection intersection) {
        System.out.println("light");
        Vector3D newPosition = intersection.getLocation();
        Vector3D newRay = light.getPosition().subtract(newPosition).normalize();
        System.out.println("nop light?");

        double shadowIntensity = 1;
        List<Intersection> lightIntersections = findSortIntersections(newRay, newPosition);

        if (lightIntersections.size() > 0) {
            Vector3D nextLocation = lightIntersections.get(0).getLocation();
            double distanceToFirstLocation = nextLocation.subtract(newPosition).getNorm();
            double distanceToLight = light.getPosition().subtract(newPosition).getNorm();
            shadowIntensity = distanceToFirstLocation < distanceToLight ?
                    1 - light.getShadowIntensity() : shadowIntensity;
        }
        System.out.println("nop light1?");

        double lightDir = intersection.getNormal().dotProduct(newRay);
        System.out.println("nop light2?");

        double directionToLight = lightDir < 0 ? 0 : lightDir;
        System.out.println("nop light20?");

        Color diffColor = calculateDiffuseColor(intersection, light, directionToLight, shadowIntensity);
        System.out.println("nop light21?");

        Color specColor = calculateSpecularColor(intersection, light, shadowIntensity, newPosition, newRay);
        System.out.println("nop light22?");

        return Color.sum(diffColor, specColor);
    }

    private Color calculateSpecularColor(Intersection intersection, Light light, double shadowIntensity, Vector3D newPosition, Vector3D newRay) {
        Vector3D x = camera.getPosition().subtract(newPosition).normalize();
        Vector3D y = intersection.getNormal().scalarMultiply(2 * newRay.dotProduct(intersection.getNormal())).subtract(newRay).normalize();
        double phongCoeffTemp = pow(x.dotProduct(y), materials.get(intersection.getMaterialIndex() - 1).getSpecularityCoefficient());
        double phongCoeff = phongCoeffTemp < 0 ? 0 : phongCoeffTemp;
        return Color.multiplyWithFactor(materials.get(intersection.getMaterialIndex() - 1).getSpecularColor(),
                light.getLightColor(),
                phongCoeff * shadowIntensity * light.getSpecularIntensity());
    }

    private Color calculateDiffuseColor(Intersection intersection, Light light, double directionToLight, double shadowIntensity) {
        System.out.println(light);
        System.out.println(intersection);
        System.out.println(intersection.getMaterialIndex()-1);
        int index = intersection.getMaterialIndex() - 1 >= 0 ? intersection.getMaterialIndex() - 1 : 0;
        return Color.multiplyWithFactor(materials.get(intersection.getMaterialIndex() - 1).getDiffuseColor(),
                light.getLightColor(),
                directionToLight * shadowIntensity);
    }


    private List<Intersection> findSortIntersections(Vector3D ray, Vector3D position) {
        List<Intersection> intersections = new ArrayList<>();
        Intersection tempIntersection;
        //find intersections
        for (Surface surface : surfaces) {
            tempIntersection = surface.getIntersection(ray, position);
            if (tempIntersection != null) {
                intersections.add(tempIntersection);
            }
        }
        //sort intersections by distance
        Collections.sort(intersections, new Comparator<Intersection>() {
            @Override
            public int compare(Intersection a, Intersection b) {
                return Double.compare(a.getDistance(), b.getDistance());
            }
        });

        return intersections;
    }

    private List<Intersection> findSortIntersections(Vector3D ray) {
        return findSortIntersections(ray, camera.getPosition());
    }

    private Vector3D ConstructRayTowardsPixel(int i, int j) {
        double x = (double) i;
        double y = (double) j;
        Vector3D V = upperLeftScreenCorner.subtract(camera.getLeftSideScreenNormalized().scalarMultiply(x * camera.getScreenWidth() / imageWidth));
        V = V.subtract(camera.getUpFixedNormalized().scalarMultiply(camera.getScreenHeight() * (y / imageHeight)));
        V = V.subtract(camera.getPosition()).normalize();
        return V;
    }
/*

    private Color GetColor(Vector3D ray, Intersection hit) {
    }
*/

    private void setPixelLocationRGB(int x, int y, byte[] rgbData, Color color) {
        rgbData[(y * this.imageWidth + x) * 3] = color.getRed();
        rgbData[(y * this.imageWidth + x) * 3 + 1] = color.getGreen();
        rgbData[(y * this.imageWidth + x) * 3 + 2] = color.getBlue();

    }
    //////////////////////// FUNCTIONS TO SAVE IMAGES IN PNG FORMAT //////////////////////////////////////////

    /*
     * Saves RGB data as an image in png format to the specified location.
     */
    public static void saveImage(int width, byte[] rgbData, String fileName) {
        try {

            BufferedImage image = bytes2RGB(width, rgbData);
            ImageIO.write(image, "png", new File(fileName));

        } catch (IOException e) {
            System.out.println("ERROR SAVING FILE: " + e.getMessage());
        }

    }

    /*
     * Producing a BufferedImage that can be saved as png from a byte array of RGB values.
     */
    public static BufferedImage bytes2RGB(int width, byte[] buffer) {
        int height = buffer.length / width / 3;
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        SampleModel sm = cm.createCompatibleSampleModel(width, height);
        DataBufferByte db = new DataBufferByte(buffer, width * height);
        WritableRaster raster = Raster.createWritableRaster(sm, db, null);
        BufferedImage result = new BufferedImage(cm, raster, false, null);

        return result;
    }

    public static class RayTracerException extends Exception {
        public RayTracerException(String msg) {
            super(msg);
        }
    }

    private void fixUpVectorGetCameraHeightGetCorner() {
        // adjust camera settings
        camera.setToScreenNormalized(camera.getLookAt().subtract(camera.getPosition()).normalize());
        camera.setToCenterScreen(camera.getToScreenNormalized().scalarMultiply(camera.getScreenDistance()));
        camera.setLeftSideScreenNormalized(camera.getToScreenNormalized().crossProduct(camera.getUp()).normalize());
        camera.setUpFixedNormalized(camera.getLeftSideScreenNormalized().crossProduct(camera.getToScreenNormalized()).normalize());
        camera.setScreenHeight(camera.getScreenWidth() * imageHeight / imageWidth);
        // adjust screen settings
        upperLeftScreenCorner = camera.getPosition().add(camera.getToCenterScreen());
        upperLeftScreenCorner = upperLeftScreenCorner.add(camera.getLeftSideScreenNormalized().scalarMultiply(camera.getScreenWidth() / 2));
        upperLeftScreenCorner = upperLeftScreenCorner.add(camera.getUpFixedNormalized().scalarMultiply(camera.getScreenHeight() / 2));
    }

}
