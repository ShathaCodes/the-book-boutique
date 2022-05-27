describe("Test the Book Shop", () => {

  beforeEach(() => {
    cy.visit("http://localhost:9000");
    cy.get(".btn").click();
  })

  it("Create a new book", () => {
  
    cy.get("#title").type("Circe");
    cy.get("#author").type("Madeline Miller");
    cy.get("#quantity").type(5);
    cy.get("#genre").type("Historical Fiction");
    cy.get("#price").type(25.99);

    cy.get('#submit-form').click();

    let new_row = cy.get("table").get("tr").last();

    new_row.contains('Circe').should("be.visible");

  });

  it("Edit a book", () => {

    cy.get(".edit").last().click();

    cy.get("#title").clear().type("The song of Achilles");
    cy.get("#author").clear().type("Madeline Miller");
    cy.get("#quantity").clear().type(15);
    cy.get("#genre").clear().type("Novel");
    cy.get("#price").clear().type(10);

    cy.get('#submit-form').click();

    let new_row = cy.get("table").get("tr").last();

    new_row.contains('The song of Achilles').should("be.visible");

  });

  it("Delete a book", () => {
    cy.get("table").find('tr').then(listing => {
      const listingCount = Cypress.$(listing).length;

      cy.get(".delete").last().click();
      cy.get("table").find('tr').should('have.length', listingCount-1)

    });
    

  });

});
