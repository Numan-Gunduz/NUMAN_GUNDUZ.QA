package core.listeners;
import core.utlis.Screenshots;
import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.nio.file.Path;

public class TestListener implements ITestListener {

    @Override public void onTestFailure(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Path png = Screenshots.takePng(name);
        try (FileInputStream fis = new FileInputStream(png.toFile())) {
            Allure.addAttachment("Failure - " + name, "image/png", fis, "png");
        } catch (Exception ignored) {}
    }

    @Override public void onStart(ITestContext context) { }
    @Override public void onFinish(ITestContext context) { }
    @Override public void onTestStart(ITestResult result) { }
    @Override public void onTestSuccess(ITestResult result) { }
    @Override public void onTestSkipped(ITestResult result) { }
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) { }
}
