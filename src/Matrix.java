import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import static java.lang.Math.*;

public class Matrix {
    Vector3D[] matrix;

    public Matrix(double x, char axis) {
        double d = toRadians(x);
        switch (axis) {
            case 'x':
                matrix = new Vector3D[]
                        {
                                new Vector3D(1, 0, 0),
                                new Vector3D(0, cos(d), -sin(d)),
                                new Vector3D(0, sin(d), cos(d))
                        };
                break;
            case 'y':
                matrix = new Vector3D[]
                        {
                                new Vector3D(cos(d), 0, sin(d)),
                                new Vector3D(0, 1, 0),
                                new Vector3D(sin(d), 0, cos(d))
                        };
                break;
            case 'z':
                matrix = new Vector3D[]
                        {
                                new Vector3D(cos(d), -sin(d), 0),
                                new Vector3D(sin(d), cos(d), 0),
                                new Vector3D(0, 0, 1)
                        };
                break;
        }
    }

    public static Vector3D multVectorMatrix(Matrix m, Vector3D v){
        return new Vector3D(m.matrix[0].dotProduct(v),
                            m.matrix[1].dotProduct(v),
                            m.matrix[2].dotProduct(v)
                            );

    }
}
