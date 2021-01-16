package edu.kis.powp.jobs2d.drivers.extensions;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.extensions.ExtensionDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class UsageMonitorExtension implements ExtensionDriver {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private double nonOperationalDistance = 0;
    private double operationalDistance = 0;

    private double lastX = 0;
    private double lastY = 0;

    private Job2dDriver driver;

    @Override
    public void setPosition(int x, int y) {
        nonOperationalDistance += calculateDistance(x, y);
        driver.setPosition(x, y);

        lastX = x;
        lastY = y;

        updateLogger();
    }

    @Override
    public void operateTo(int x, int y) {
        operationalDistance += calculateDistance(x, y);
        driver.operateTo(x, y);

        lastX = x;
        lastY = y;

        updateLogger();
    }

    private double calculateDistance(int x, int y)
    {
        return sqrt(pow(lastX - x, 2) + pow(lastY - y, 2));
    }

    private void updateLogger() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        logger.info("Non operational distance: " + decimalFormat.format(nonOperationalDistance) +
                    "\n   Operational distance: " + decimalFormat.format(operationalDistance));
    }

    @Override
    public void setDriver(Job2dDriver driver) {
        this.driver = driver;
    }
}
