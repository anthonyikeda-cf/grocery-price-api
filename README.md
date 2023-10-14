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

class CalculationRequest {
    String customerId
    LineItem[] lineItems

    +getCustomerId() String 
    +getLineItems() LineItem[]
}

CalculationRequest o-- LineItem

class CalculationResponse {
    +String calculationId
    +LineItem[] calculations
}

class TaxItem {
    +String taxType
    +Double taxPercentage
    +Float totalCost
}
```

Domain is about the resource (this line to be deleted)
