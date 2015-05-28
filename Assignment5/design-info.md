# ***Design Decisions and Assumptions***


### Payment and Rewards Management System for Mobile Stalls

#### Assumptions
- Assumption is that only one system is available with one configuration. Multiple systems / multiple system configurations 
are not designed
- Purchase, Transaction and Payment are considered synonyms
- Rewards and Discounts are considered synonyms. Discounts are considered as type of rewards
- No classes are provided for depicting user interfaces. This class diagram only depicts the requirements as
specified in the assignment
- No class is created for the 'Stall Manager' / 'User' accessing the system as he is the only person accessing the system
- No classes are provided for explicitly handling Security

#### Design Decisions

#####1. `SystemMain` class
- This class represents the complete system. There are two system level classes - `CustomerManager` and `PaymentsManager` which provide the complete functionality for handling customer information and payments

#####2. `CustomerManager` class
- This class handles all aspects with adding/editing/retrieving a customer 
- Typically it might contain a list of all customer entities. The transactions, discounts and credit-card are all associated 
with a customer
- Note that this design only handles 1 credit card per customer as per the multiplicity shown between the `Customer` class
 and `CreditCard` class

#####3. `PaymentsManager` class
- This class gets a reference handle to the respective `customer` object from the parent main-system class through
which it can get the details of transactions, discounts and credit-card details for the customer 
- This class processes new payments, updates the transactions for the customer and also processes discounts/rewards
- As part of payments processing, this class also sends email for the various rewards/discounts received by
the customer

#####4. `Transaction` class
- A base class `Transaction` and child class `CreditCardTransaction` are provided even though the requirements indicate 
that only credit card transactions are possible. This is to have possible future support for other types of transactions
- The `CreditCardTransaction` class also stores the credit card that was used for the particular transaction as the user
might change the credit card for future transactions
- Only the last-used credit card is stored in the `Customer` object for future use within the system

#####5. `CreditCardScanner` and `PaymentProcessingService` classes
- The credit card reader hardware device is abstracted as `CreditCardScanner` class. And the externally available payments
processing service provider is abstracted as `PaymentProcessingService` class. These are just representative classes and 
all the integration details are not shown in this class diagram
- Assumption is that the hardware device reads the credit card and returns the details as a `CreditCard` object which can be associated with a `Customer`. And for the payment processing, the external `PaymentProcessingService` is used and a 
success/failure boolean value is returned.
- The `PaymentsManager` has the responsibility of calculating the amount to be charged based on discounts/rewards available
for the customer, tying the `CreditCard` details and processed payment details to the respective customer.

#####6. `EmailServer` and `Email` classes
These classes represent a standard SMTP email server that performs email processing and a sample 'email' represented as
`Email` object. In reality, these may be just services used by the application. But in this class diagram, the 
`PaymentsManager` uses the Email server to send emails to the customers regarding the various discounts earned.


