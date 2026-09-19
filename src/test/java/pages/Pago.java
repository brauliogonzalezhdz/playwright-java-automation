package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Pago {

    private Page page;
    private Locator pay;
    private Locator nombre;
    private Locator correo;
    private Locator direccion;
    private Locator ciudad;
    private Locator zipCode;
    private Locator Tarjet;
    private Locator fecha;
    private Locator Cvv;
    private Locator confirm;
    private Locator volver;


    public Pago(Page page)
    {
        this.page = page;
        this.pay = page.locator("[data-testid='go-checkout-btn']");
        //this.pay = page.getByLabel("Ir a pagar"); no funciona el html forzosamente debe tener getbylabel
        this.nombre = page.locator("[data-testid='input-name']");
        this.correo = page.locator("[data-testid='input-email']");
        this.direccion = page.locator("[data-testid='input-address']");
        this.ciudad = page.locator("[data-testid='input-city']");
        this.zipCode = page.locator("[data-testid='input-zip']");
        this.Tarjet = page.locator("[data-testid='input-card']");
        this.fecha = page.locator("[data-testid='input-expiry']");
        this.Cvv = page.locator("[data-testid='input-cvv']");
        this.confirm = page.locator("[data-testid='place-order-btn']");
        this.volver = page.locator("[data-testid='back-to-shop']");

    }

public void Pagar()
{
    pay.click();
    nombre.fill("Braulio Hernandez");
    correo.fill("noemail@noemail.com");
    direccion.fill("Address 1");
    ciudad.fill("Mexico");
    zipCode.fill("15800");
    Tarjet.fill("4242424242424242");
    fecha.fill("12/28");
    Cvv.fill("123");
    confirm.click();
  }

    public void noPagar(String tarjeta)
    {

        pay.click();
        nombre.fill("Braulio Hernandez");
        correo.fill("noemail@noemail.com");
        direccion.fill("Address 1");
        ciudad.fill("Mexico");
        zipCode.fill("15800");
        Tarjet.fill(tarjeta);
        fecha.fill("12/28");
        Cvv.fill("123");
        confirm.click();
    }

    public void invalidCvv(String cvv)
    {


        pay.click();
        nombre.fill("Braulio Hernandez");
        correo.fill("noemail@noemail.com");
        direccion.fill("Address 1");
        ciudad.fill("Mexico");
        zipCode.fill("15800");
        Tarjet.fill("4242424242424242");
        fecha.fill("12/28");
        Cvv.fill(cvv);
        confirm.click();
    }

    public void pedidoConfirmed()
    {

        assertThat(volver).hasText("Volver a la tienda");
    }

}

