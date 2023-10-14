# Grocery Price API

API that turns a function into a resource. Also used to sample the Atlassian Compass tool as a developer portal.

## How it works

Our resource is the `PriceCalculator`. It somewhat emulates a graph mutation taking an Input (`CalculationRequest`) and returning values that complete the resource of a calculation.


```mermaid
classDiagram
class LineItem {
    +String productId
    +Integer qty

    +getProductId() String
    +getQty() Integer
}

class CalculationType {
    <<enumeration>>
    TOTALS
    TAXES
    ALL
}

class CalculationRequest {
    <<request>>
    String customerId
    CalculationType calculationType
    LineItem[] lineItems

    +getCustomerId() String
    +getCalculationType() String 
    +getLineItems() LineItem[]
}

CalculationRequest o-- LineItem

class CalculationResponse {
    <<response>>
    +String calculationId
    +LineItem[] calculations
    +TaxItem[] taxes

    +getCalculationId() String
    +getCalculations() LineItem[]
    +getTaxes() TaxItem[]
}

class TaxItem {
    +String taxType
    +Double taxPercentage
    +Float totalCost

    +getTaxtType() String
    +getTaxPercentage() Double
    +getTotalCost() Float
}

CalculationResponse o-- LineItem
CalculationResponse o-- TaxItem
```

When making a request, the value of the `CalculationType` must be one of:

* Totals
* Taxes
* All (Totals and Taxes)
