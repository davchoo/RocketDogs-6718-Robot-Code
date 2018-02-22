package frc.team6718;

import frc.team6718.robot.commands.Command;
import frc.team6718.robot.commands.CommandGroup;
import frc.team6718.robot.commands.MoveDistanceCommand;
import frc.team6718.robot.commands.TurnCommand;
import frc.team6718.robot.commands.auto.LLAutoCommandGroup;
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
import java.util.ArrayList;

import static jaci.pathfinder.Pathfinder.d2r;

public class Test extends Application {
    public Waypoint[] convert(CommandGroup group, double y) {
        double lastHeading = 90;
        ArrayList<Waypoint> points = new ArrayList<>();
        double lX = 0, lY = y;
        for (Command c : group.commands) {
            if (c instanceof TurnCommand) {
                lastHeading += ((TurnCommand) c).getHeading();
            }else{
                lX += Math.sin(d2r(lastHeading)) * ((MoveDistanceCommand) c).distance / 12d;
                lY += Math.cos(d2r(lastHeading)) * ((MoveDistanceCommand) c).distance / 12d;
                points.add(new Waypoint(lX, lY, lastHeading));
            }
        }
        return points.toArray(new Waypoint[points.size()]);
    }

    @Override
    public void start(Stage primaryStage) {
        //Add waypoints here
        //The heading is in radians so call d2r to change deg to rads
        Waypoint[] waypoints = convert(new LLAutoCommandGroup(), 23.34);
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
