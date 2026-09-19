package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddCart  {
    private Page page;
    private Locator potato;
    private Locator zanahoria;
    private Locator cart;
    private Locator openCart;
    private Locator zanahoriaCard;
    private Locator Total;



    //Constuctor
    public AddCart(Page page) {
        this.page = page;
        this.potato = page.locator("[data-testid='add-to-cart-p4']");
        this.zanahoria = page.locator("[data-testid='add-to-cart-p2']");
        this.zanahoriaCard = page.getByTestId("cart-item-p2")
                .locator("h4");
        this.cart = page.locator("[data-testid='cart-count']");
        this.openCart = page.locator("[data-testid='open-cart']");
        this.Total = page.locator("[data-testid='drawer-total']");


    }


    public void Potatoes()
    {
      potato.click();
    }

    public void Carrot()
    {
        zanahoria.click();
    }

    public String cart()
    {
        return cart.textContent();
    }

    public void openCar()
    {
        openCart.click();
    }

    public Locator getZanahoriaCard()
    {
        return zanahoriaCard;
    }

    public Locator getTotal()
    {
        return Total;
    }


}
