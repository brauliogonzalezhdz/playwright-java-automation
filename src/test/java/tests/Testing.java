package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddCart;
import pages.Pago;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class Testing {


    private String baseUrl = "https://rad-dusk-4a8199.netlify.app/";
    Playwright playwright;
    Browser browser;
    //BrowserContext context;
    Page page;
    AddCart add;
    Pago payment;


    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );
        page = browser.newPage();
        page.navigate(baseUrl);
        add = new AddCart(page);
        payment = new Pago(page);

    }

    @Test
    public void openPage() {
        System.out.println("Open the page");
        //page.waitForURL(baseUrl);
        String title = page.title();
        System.out.println(title);
        assertEquals(title, "La Huerta — Sitio de pruebas");
    }


    @Test
    public void addPotatoes() {

        add.Potatoes();
        add.Potatoes();
        add.Carrot();
        assertEquals(add.cart(), "3");
        add.openCar();
        assertThat(page.getByText("Tu carrito")).hasText("Tu carrito");
        assertThat(page.getByTestId("cart-item-p2")
                .locator("h4")).hasText("Zanahoria");
        assertThat(page.getByTestId("cart-item-p4")
                .locator("h4")).hasText("Papa");
        assertThat(add.getTotal()).hasText("$2.010");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/carrito.png")));
    }

    @Test
    public void increase() {

        System.out.println("Add two carrots");
        add.Carrot();
        assertEquals(add.cart(), "1");
        add.openCar();
        assertThat(page.getByText("Tu carrito")).hasText("Tu carrito");
        page.locator("[data-testid='increment-p2']").click();
        page.locator("[data-testid='increment-p2']").click();
        assertThat(page.getByTestId("cart-item-p2")
                .locator("h4")).hasText("Zanahoria");
        assertThat(page.locator("[data-testid='drawer-total']")).hasText("$2.210");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/3carrots.png")));
    }

    @Test
    public void decrease() {

        System.out.println("Add two carrots");
        add.Carrot();
        add.Carrot();
        assertEquals(add.cart(), "2");
        add.openCar();
        assertThat(page.getByText("Tu carrito")).hasText("Tu carrito");
        page.locator("[data-testid='increment-p2']").click();
        page.locator("[data-testid='increment-p2']").click();
        page.locator("[data-testid='decrement-p2']").click();
        page.locator("[data-testid='decrement-p2']").click();
        assertThat(page.getByTestId("cart-item-p2")
                .locator("h4")).hasText("Zanahoria");
        assertThat(page.locator("[data-testid='drawer-total']")).hasText("$1.590");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/2carrots.png")));
        payment.Pagar();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/pago.png")));
        payment.pedidoConfirmed();
        assertThat(page.locator("[data-testid='back-to-shop']")).isVisible();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/pedido hecho.png")));
    }

    @Test
    public void InvalidTarjet() {
        add.Potatoes();
        add.Potatoes();
        add.openCar();
        payment.noPagar("1");
        assertThat(page.locator("[data-testid='error-card']")).isVisible();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/invalidcard.png")));

    }

    @Test
    public void Invalidcvv()
    {
        add.Carrot();
        add.Potatoes();
        add.openCar();
        page.locator("[data-testid='place-order-btn']").isVisible();
        payment.invalidCvv("Hola mundo jeje");
        assertThat(page.locator("[data-testid='error-cvv']")).isVisible();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/invalidcvv.png")));
    }

    @AfterMethod
    public void tearDown() {
        browser.close();
        playwright.close();

    }
}
