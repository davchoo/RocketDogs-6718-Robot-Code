package frc.team6718;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.File;

import static jaci.pathfinder.Pathfinder.d2r;

/**
 * Created by davchoo
 * Date: 1/26/2018
 * Time: 9:50 PM
 */
public class Test extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Add waypoints here
        //The heading is in radians so call d2r to change deg to rads
        Waypoint[] waypoints = new Waypoint[]{
                new Waypoint(0, 0, d2r(0)),
                new Waypoint(24, 24, d2r(90)),
                new Waypoint(48, 48, d2r(0)),
        };
        //Configure the settings for Pathfinder
        //TODO Change max_velocity, max_acceleration and max_jerk to correct measurements on the robot
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 36, 6, 1);
        Trajectory t = Pathfinder.generate(waypoints, config);
        //Create a graph of velocity, acceleration, jerk, and heading
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Path");

        XYChart.Series<Number, Number> vel = new XYChart.Series<>();
        XYChart.Series<Number, Number> accel = new XYChart.Series<>();
        XYChart.Series<Number, Number> jerk = new XYChart.Series<>();
        XYChart.Series<Number, Number> heading = new XYChart.Series<>();
        vel.setName("Velocity");
        accel.setName("Acceleration");
        jerk.setName("Jerk");
        heading.setName("Heading");

        double time = 0;
        for (Trajectory.Segment s : t.segments) {
            vel.getData().add(new XYChart.Data<>(time, s.velocity));
            accel.getData().add(new XYChart.Data<>(time, s.acceleration));
            jerk.getData().add(new XYChart.Data<>(time, s.jerk));
            heading.getData().add(new XYChart.Data<>(time, s.heading));
            time += 0.02;
        }

        File file = new File("path.csv");
        Pathfinder.writeToCSV(file, t);

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(vel);
        lineChart.getData().add(accel);
        lineChart.getData().add(jerk);
        lineChart.getData().add(heading);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
