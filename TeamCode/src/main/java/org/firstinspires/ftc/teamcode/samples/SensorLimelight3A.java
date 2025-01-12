/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of FIRST nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.samples;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

/*
 * This OpMode illustrates how to use the Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */
@TeleOp(name = "Sensor: Limelight3A", group = "Sensor")
// @Disabled
public class SensorLimelight3A extends LinearOpMode {

    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException
    {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(3);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            LLStatus status = limelight.getStatus();
            /*
            telemetry.addData("Name", "%s",
                    status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
                    status.getTemp(), status.getCpu(),(int)status.getFps());
             */
            telemetry.addData("Pipeline", "Index: %d, Type: %s",
                    status.getPipelineIndex(), status.getPipelineType());

            LLResult result = limelight.getLatestResult();
            if (result != null) {
                // Access general information
                /*Pose3D botpose = result.getBotpose();
                double captureLatency = result.getCaptureLatency();
                double targetingLatency = result.getTargetingLatency();
                double parseLatency = result.getParseLatency();
                telemetry.addData("LL Latency", captureLatency + targetingLatency);
                telemetry.addData("Parse Latency", parseLatency);
                telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));
                 */
                
                if (result.isValid())
                {
                    double tx = result.getTxNC();
                    double ty = result.getTyNC();

                    // tx is positive below the crosshair, as mounted.
                    // ty is positive to the right, as mounted.
                    // Camera sensor is 8" from ground, 13" behind robot front.
                    // Tilted about 15 degs forward.
                    // Cross hair is 35" horizontally from camera lens.
                    // Calculate camera tilt.
                    final double ca = Math.atan(35 / 8);
                    double rd = 8 * Math.tan(ca - Math.toRadians(tx)) - 13;
                    double sd = (rd + 13) * Math.tan(Math.toRadians(ty));

                    // Orientation
                    List<LLResultTypes.ColorResult> crs = result.getColorResults();
                    LLResultTypes.ColorResult cr1 = crs.get(0);
                    List<List<Double>> ll = cr1.getTargetCorners();
                    // Since camera is rotated, we will sort decending by Y (left to right)
                    ll.sort((i1, i2) -> (int) (i2.get(1) - i1.get(1)));
                    List<Double> crd = ll.get(0);
                    Double dx = crd.get(0);
                    Double dy = crd.get(1);
                    double da = Math.toDegrees(Math.atan((dx - cr1.getTargetXPixels()) / (dy - cr1.getTargetYPixels())));
                    double dab = Math.abs(da);
                    double[] rs = ranges(ll);

                    // System.out.println(ll.toString());
                    // System.out.println(rs[0] + " " + rs[1]);

                    // Camera is not able to determine the angle of the sample with the corner points
                    // Aspect ratio looks same at any angle, except pure vertical.

                    telemetry.addData("Sample", "YELLOW");
                    telemetry.addData("Forward", "%.1f in", rd);
                    telemetry.addData("Go Right", "%.1f in", sd);
                    telemetry.addData("Easy Pick", (rs[1] / rs[0] < 1.5) ? "Yes" : "No");
                    telemetry.addData("Range ratio", "%.2f", rs[1] / rs[0]);
                    telemetry.addLine();
                    telemetry.addData("tx", tx);
                    telemetry.addData("ty", ty);
                    telemetry.addData("Ranges", "H: " + rs[1] + "V: " + rs[0]);
                    // telemetry.addData("txnc", result.getTxNC());
                    // telemetry.addData("tync", result.getTyNC());
                    // telemetry.addData("Botpose", botpose.toString());

                    // tx is forward distance. +14.5 is close to the robot.
                    //  0 is the cross-hair, about 22.5" away.
                    // ty is lateral distance.
                    //  right = 13, or about +9" inches.
                    //  left = -17, or about -9" inches


                    /*
                    // Access barcode results
                    List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
                    for (LLResultTypes.BarcodeResult br : barcodeResults) {
                        telemetry.addData("Barcode", "Data: %s", br.getData());
                    }

                    // Access classifier results
                    List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
                    for (LLResultTypes.ClassifierResult cr : classifierResults) {
                        telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
                    }

                    // Access detector results
                    List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
                    for (LLResultTypes.DetectorResult dr : detectorResults) {
                        telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
                    }

                    // Access fiducial results
                    List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                    for (LLResultTypes.FiducialResult fr : fiducialResults) {
                        telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(),fr.getTargetXDegrees(), fr.getTargetYDegrees());
                    }
                    */

                    // Access color results
                    List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
                    for (LLResultTypes.ColorResult cr : colorResults) {
                        telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXPixels(), cr.getTargetYPixels());
                        break;
                    }

                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            telemetry.update();
            sleep(5000);
        }
        limelight.stop();
    }

    private double[] ranges(List<List<Double>> corners)
    {
        double minX = 10000, maxX = 0, minY = 10000, maxY = 0;

        for (List<Double> i: corners) {
            if (i.get(0) < minX)    minX = i.get(0);
            if (i.get(0) > maxX)    maxX = i.get(0);
            if (i.get(1) < minY)    minY = i.get(1);
            if (i.get(1) > maxY)    maxY = i.get(1);
        }

        return new double[] { maxX - minX, maxY - minY };
    }
}
