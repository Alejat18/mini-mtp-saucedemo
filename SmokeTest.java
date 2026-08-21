package co.edu.lasalle.qa;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite smoke del proceso de compra de SauceDemo.
 *
 * TC-001  Ingreso con credencial valida (requisito REQ-01).
 * TC-006  Agregar un producto al carrito actualiza el contador (requisito REQ-03).
 *
 * Las credenciales no se escriben en el codigo: se leen de las variables de
 * entorno SAUCE_USER y SAUCE_PASSWORD.
 */
class SmokeTest {

    private static final String URL = "https://www.saucedemo.com/";

    private WebDriver driver;
    private WebDriverWait espera;

    private static String variable(String nombre) {
        String valor = System.getenv(nombre);
        if (valor == null || valor.isBlank()) {
            throw new IllegalStateException(
                    "Falta la variable de entorno " + nombre
                            + ". Configurela como secreto del repositorio antes de ejecutar.");
        }
        return valor;
    }

    @BeforeEach
    void abrirNavegador() {
        ChromeOptions opciones = new ChromeOptions();
        opciones.addArguments("--headless=new");
        opciones.addArguments("--no-sandbox");
        opciones.addArguments("--disable-dev-shm-usage");
        opciones.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(opciones);
        espera = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(URL);
    }

    private void iniciarSesion() {
        espera.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
              .sendKeys(variable("SAUCE_USER"));
        driver.findElement(By.id("password")).sendKeys(variable("SAUCE_PASSWORD"));
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    @DisplayName("TC-001 - El ingreso con credencial valida lleva al catalogo")
    void ingresoConCredencialValida() {
        iniciarSesion();

        espera.until(ExpectedConditions.urlContains("inventory.html"));
        String titulo = espera
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("title")))
                .getText();

        assertEquals("Products", titulo, "El catalogo deberia titularse Products");
        assertTrue(driver.findElements(By.className("inventory_item")).size() > 0,
                "El catalogo deberia mostrar al menos un producto");
    }

    @Test
    @DisplayName("TC-006 - Agregar un producto actualiza el contador del carrito")
    void agregarProductoActualizaContador() {
        iniciarSesion();
        espera.until(ExpectedConditions.urlContains("inventory.html"));

        espera.until(ExpectedConditions.elementToBeClickable(
                By.id("add-to-cart-sauce-labs-backpack"))).click();

        String contador = espera
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.className("shopping_cart_badge")))
                .getText();

        assertEquals("1", contador,
                "El contador del carrito deberia mostrar un producto");
    }

    @AfterEach
    void cerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
