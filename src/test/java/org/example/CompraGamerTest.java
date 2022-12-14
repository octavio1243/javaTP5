package org.example;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;

import org.junit.Test;
import org.openqa.selenium.io.FileHandler;

import javax.management.RuntimeErrorException;
import java.io.File;

public class CompraGamerTest {
    //"chrome" or "firefox"
    private final String browser = "chrome";
    private WebDriver driver;
    private String OS;

    @Before
    public void setDriver() {
        SeleniumConfig seleniumConfig = new SeleniumConfig(browser);
        this.driver = seleniumConfig.getDriver();
        this.OS = seleniumConfig.getOS();
    }

    @Test
    public void probarAgregarProductosDistintos() {
        String cantidad = "0";
        try {
            driver.get("https://compragamer.com/?seccion=3");
            driver.findElement(By.xpath("//div[3]/button/span")).click();
            driver.findElement(By.xpath("//cgw-product-alone[2]/div/div[2]/div[3]/button/span")).click();
            driver.findElement(By.xpath("//cgw-product-alone[3]/div/div[2]/div[3]/button/span")).click();
            driver.findElement(By.xpath("//cgw-product-alone[4]/div/div[2]/div[3]/button/span")).click();
            cantidad = driver.findElement(By.xpath("//p[@class='badge-button']//span")).getText();
            System.out.println("Cantidad productos distintos en carrito: '" + cantidad + "'");
        } catch (Exception e) {
            //Aca tomar alguna captura y mandarla a la carpeta de errores
            TakesScreenshot ts=(TakesScreenshot)driver;
            File source=ts.getScreenshotAs(OutputType.FILE);
            try {
                String rutaScreenshot = (OS == "Windows" ? "\\Screeshots\\productosDistintosCG.png" : "./Screenshots/productosDistintosCG.png");
                FileHandler.copy(source, new File(rutaScreenshot));
            }catch (Exception e2){
                System.out.println(e2);
            }
            System.out.println("Screenshot taken");
        }
        //timeDelay(3000);
        driver.close();
        assert (cantidad.equals("4"));
    }

    @Test
    public void probarCambioCantidadProductos() {
        String cantidad = "0";
        try {
            driver.get("https://compragamer.com/producto/Memoria_GeiL_DDR4_16GB_3000MHz_Orion_RGB_Black_11807?tipo_pago=3");
            driver.findElement(By.xpath("//button[@class='mat-focus-indicator button-ok-width mat-button mat-raised-button mat-button-base mat-primary']")).click();
            driver.findElement(By.xpath("//mat-icon[contains(text(),'shopping_cart')]")).click();
            for (int i = 0; i < 4; i++) {
                driver.findElement(By.xpath("//mat-icon[contains(.,\'add\')]")).click();
            }
            for (int i = 0; i < 2; i++) {
                driver.findElement(By.xpath("//mat-icon[contains(.,\'remove\')]")).click();
            }
            cantidad = driver.findElement(By.xpath("//span[@class='ng-star-inserted']")).getText();
            System.out.println("Cantidad productos del mismo tipo en carrito: '" + cantidad + "'");
        } catch (Exception e) {
            //Aca tomar alguna captura y mandarla a la carpeta de errores
            TakesScreenshot ts=(TakesScreenshot)driver;
            File source=ts.getScreenshotAs(OutputType.FILE);
            try {
                String rutaScreenshot = (OS == "Windows" ? "\\Screeshots\\cambioCantProductosCG.png" : "./Screenshots/cambioCantProductosCG.png");
                FileHandler.copy(source, new File(rutaScreenshot));
            }catch (Exception e2){
                System.out.println(e2);
            }
            System.out.println("Screenshot taken");
        }
        //timeDelay(3000);
        driver.close();
        assert (cantidad.equals("3"));
    }

    public void timeDelay(long t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
        }
    }
}
