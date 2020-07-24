/**
# =================================================================
Author:          <AP-team5, Mohammad Sadegh Majidi Yazdi>
Create date:     <13 July 2020>
Description:     <online shop - Automation shop - System version>
# =================================================================
**/

CREATE DATABASE OnlineShopProject;

--------------------------------------------------------------------------------------------------------------------

USE OnlineShopProject;


/* base informations for shop*/

CREATE TABLE ShopBankAccount(
	AccountId	varchar(50)		charset utf8	NOT NULL	 unique,
	Username	varchar(100)	charset utf8	NOT NULL	 unique,
    `Password`  varchar(100)	charset utf8	NOT NULL,
    minimumAmount	int		NOT NULL,
    FeePercent		int		NOT NULL,
    ModificationDate	date	NOT NULL  DEFAULT(NOW()) ,
    CONSTRAINT	PK_Shop_ShopBankAccount_AccountId	PRIMARY KEY(AccountId)
);

/*
====================================================================================================================
==============================================================================================================Person
====================================================================================================================
====================================================================================================================
*/

CREATE TABLE Persons 
(
	PersonId		bigint	NOT NULL	auto_increment,
	UserName		varchar(70) charset utf8	NOT NULL unique,
	FirstName	varchar(50) charset utf8	NULL,
	LastName		varchar(60) charset utf8	NULL,
	Email			varchar(120) charset utf8	NULL,
	PhoneNumber	varchar(15) charset utf8	NULL,
    `Password`		varchar(128) charset utf8		NOT NULL,
	ModificationDate	date	NOT NULL	DEFAULT (NOW()),
    CONSTRAINT	PK_Person_Persons_PersonID	PRIMARY KEY(PersonId)
);


CREATE TABLE Company
(
	CompanyID		int		NOT NULL,
	`Name`			varchar(100) charset utf8	NOT NULL,
	Website		varchar(80) charset utf8	NULL,
	PhoneNumber	varchar(15) charset utf8	NULL,
	FaxNumber		varchar(15) charset utf8	NULL,
	Address		varchar(300) charset utf8	NULL,
	ModificationDate	date	NOT NULL  DEFAULT(NOW()) ,
    CONSTRAINT	PK_Person_Company_CompanyID	PRIMARY KEY(CompanyID)
);


CREATE TABLE Customer
(
	PersonId		bigint		NOT NULL	,
    BankAccountId	varchar(100) charset utf8	NULL	unique,
	Credit	bigint		NOT NULL,
    RowGuid	CHAR(38)    NOT NULL		DEFAULT (UUID()),
    CONSTRAINT	PK_Person_Customer_PersonId	PRIMARY KEY(PersonId),
    CONSTRAINT	FK_Person_Customer_PersonId	FOREIGN KEY(PersonId) REFERENCES Persons(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Manager
(
	PersonId		bigint		NOT NULL	,
	RowGuid	CHAR(38)    NOT NULL		DEFAULT (UUID()),
    CONSTRAINT	PK_Person_Manager_PersonId	PRIMARY KEY(PersonId),
    CONSTRAINT	FK_Person_Manager_PersonId	FOREIGN KEY(PersonId) REFERENCES Persons(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Seller
(
	PersonId		bigint		NOT NULL	,
	CompanyID		int			NOT NULL	,
    BankAccountId	varchar(100) charset utf8	NULL	unique,
    Balance		bigint		NOT NULL,
	RowGuid	CHAR(38)    NOT NULL		DEFAULT (UUID()),
    CONSTRAINT	PK_Person_Seller_PersonId	PRIMARY KEY(PersonId),
    CONSTRAINT	FK_Person_Seller_PersonId	FOREIGN KEY(PersonId) REFERENCES Persons(PersonId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_Person_Seller_CompanyID	FOREIGN KEY(CompanyID)	REFERENCES Company(CompanyID) ON DELETE CASCADE ON UPDATE CASCADE
);


/*
====================================================================================================================
====================================================================================================================
==============================================================================================================Person
====================================================================================================================
*/


/*
====================================================================================================================
==========================================================================================================Production
====================================================================================================================
====================================================================================================================
*/

CREATE TABLE Category
(
	CategoryId	int		NOT NULL	auto_increment,
	`Name`	varchar(50) charset utf8	NOT NULL	unique,
	ModificationDate	date	NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_Production_Category_CategoryId	PRIMARY KEY(CategoryId)
);


CREATE TABLE DetailOfEachCategory
(
	DetailCategoryId	bigint	NOT NULL	auto_increment,
	Category	int		NOT NULL,
	Property	varchar(30) charset utf8	NOT NULL,
    CONSTRAINT	PK_Production_DetailOfEachCategory_DetailCategoryId	PRIMARY KEY(DetailCategoryId),
    CONSTRAINT	FK_Production_DetailOfEachCategory_Category	FOREIGN KEY(Category)	REFERENCES	Category(CategoryId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE SubCategory
(
	SubCategoryId	int 	NOT NULL	auto_increment,
	`Name`		varchar(50) charset utf8	NOT NULL	unique,
	Category	int		NOT NULL	,
	ModificationDate	date	NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_Production_SubCategory_SubCategoryId	PRIMARY KEY(SubCategoryId),
    CONSTRAINT	FK_Production_SubCategory_Category	FOREIGN KEY(Category)	REFERENCES Category(CategoryId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE DetailOfEachSubCategory
(
	DetailSubCategoryId		bigint	NOT NULL 	auto_increment,
	SubCategory	int	NOT NULL	,
	Property	varchar(30) charset utf8	NOT NULL,
    CONSTRAINT	PK_Production_DetailOfEachSubCategory_DetailSubCategoryId	PRIMARY KEY(DetailSubCategoryId),
    CONSTRAINT	FK_Production_DetailOfEachSubCategory_SubCategory	FOREIGN KEY(SubCategory)	REFERENCES	SubCategory(SubCategoryId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Product
(
	ProductID	bigint	NOT NULL	auto_increment	,
	`Name`	varchar(80) charset utf8	NOT NULL,
	Brand		varchar(40) charset utf8		NULL,
	ProductStatus		enum('BUILTPROCESSING', 'CONFIRMED', 'NOTAVAILABLE', 'EDITINGPROCESSING')	NOT NULL,
	AverageRate		decimal		NOT NULL,
	SubCategoryId	int		NOT NULL	,  
	`Description`		varchar(5000) charset utf8	NULL,
	SeenNumber	int			NOT NULL,
	ModificationDate	date	NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_Production_Product_ProductID		PRIMARY KEY(ProductID),
    CONSTRAINT FK_Production_Product_SubCategoryId		FOREIGN KEY(SubCategoryId)	REFERENCES	SubCategory(SubCategoryId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE ValueOfEachCategoryProperty
(
	ProductID		bigint	NOT NULL	,
	/*DetailCategoryId		bigint	NOT NULL,	*/
    Property	varchar(30) charset utf8	NOT NULL,
	`Value`			varchar(80) charset utf8	NULL,
    CONSTRAINT	FK_Production_ValueOfEachCategoryProperty_ProductID	FOREIGN KEY(ProductID)	REFERENCES	Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
    /*CONSTRAINT	FK_Production_ValueOfEachCategoryProperty_DetailCategoryId	FOREIGN KEY(DetailCategoryId)	REFERENCES	DetailOfEachCategory(DetailCategoryId),
    CONSTRAINT	PK_Production_ValueOfEachCategoryProperty_ProductDetailCategory	PRIMARY KEY(ProductID, DetailCategoryId)*/
);

/*
CREATE TABLE ValueOfEachSubCategoryProperty
(
	ProductID	bigint	NOT NULL,
	DetailSubCategoryId		bigint	NOT NULL,		
	`Value`			varchar(80) charset utf8	NULL,
    CONSTRAINT	FK_Production_ValueOfEachSubCategoryProperty_ProductID	FOREIGN KEY(ProductID)	REFERENCES	Product(ProductID),
    CONSTRAINT	FK_Production_ValueOfEachSubCategoryProperty_DetailSubCategoryId	FOREIGN KEY(DetailSubCategoryId)	REFERENCES	DetailOfEachSubCategory(DetailSubCategoryId),
    CONSTRAINT	PK_Production_ValueOfEachSubCategoryProperty_ProductDetailSubCat	PRIMARY KEY(ProductID, DetailSubCategoryId)
);
*/

CREATE TABLE ProductAndSeller
(
	ProductAndSellerId	bigint	NOT NULL auto_increment,
	ProductID		bigint		NOT NULL,
	SellerId	bigint	NOT NULL,
	Price		bigint		NOT NULL,
	NumberOfAvailables	int		NOT NULL,
    CONSTRAINT	PK_Production_ProductAndSeller_ProductAndSellerId		PRIMARY KEY(ProductAndSellerId),
    CONSTRAINT	FK_Production_ProductAndSeller_ProductID	FOREIGN KEY(ProductID)	REFERENCES Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_Production_ProductAndSeller_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Seller(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Rate
(
	RateID		bigint	NOT NULL	auto_increment,
	CustomerId	bigint	NOT NULL,
	ProductID		bigint	NOT	NULL,
	Rate		int			NOT NULL,
	ModificationDate	date		NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_Production_Rate_RateID	PRIMARY KEY(RateID),
    CONSTRAINT  FK_Production_Rate_CustomerId	FOREIGN KEY(CustomerId)	REFERENCES	Customer(PersonId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  FK_Production_Rate_ProductID	FOREIGN KEY(ProductID)	REFERENCES	Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `Comment`
(
	CommentID	bigint	NOT NULL	auto_increment,
	CustomerId	bigint	NOT NULL,
	ProductID		bigint	NOT	NULL,
	Title		varchar(50) charset utf8	NOT NULL,
	Content	varchar(5000) charset utf8	NULL,
	CommentStatus	enum('ACCEPTED', 'INPROGRESS')	NOT NULL,
	isCommenterBuysThisProduct	bool		NOT NULL,
	ModificationDate	date		NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_Production_Comment_CommentID	PRIMARY KEY(CommentID),
    CONSTRAINT  FK_Production_Comment_CustomerId	FOREIGN KEY(CustomerId)	REFERENCES	Customer(PersonId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  FK_Production_Comment_ProductID	FOREIGN KEY(ProductID)	REFERENCES	Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE
);


/*
====================================================================================================================
====================================================================================================================
==========================================================================================================Production
====================================================================================================================
*/

--------------------------------------------------------------------------------------------------------------------------------

/*
====================================================================================================================
=========================================================================================================OrderDetail
====================================================================================================================
====================================================================================================================
*/


CREATE TABLE `Order` (
	OrderId		bigint	NOT NULL	 auto_increment,
    TotalPrice		bigint	NOT NULL,
    OrderStatus		enum('PROCESSING', 'READYTOSEND', 'SENT', 'RECEIVED')	NOT NULL,
    ModificationDate	date	NOT NULL	DEFAULT(NOW()),
    CONSTRAINT	PK_OrderDetail_Order_OrderId	primary key(OrderId)
);


CREATE TABLE GoodInCart(
	GoodInCartId	bigint	NOT NULL	auto_increment,
    SellerId	bigint	NOT NULL,
    ProductId	bigint	NOT NULL,
    numberOfProducts	int		NOT NULL,
    CONSTRAINT	PK_OrderDetail_GoodInCart_ProductAndSellerId		PRIMARY KEY(GoodInCartId),
    CONSTRAINT	FK_OrderDetail_GoodInCart_ProductID	FOREIGN KEY(ProductID)	REFERENCES Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_OrderDetail_GoodInCart_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Seller(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE	OrderForSeller(
	OrderId		bigint		NOT NULL,
    SellerId	bigint		NOT NULL,
    CustomerName	varchar(70) charset utf8	NULL,
    OffDeduct	bigint		NOT NULL	default(0),
    CONSTRAINT	PK_OrderDetail_OrderForSeller_OrderId	PRIMARY KEY(OrderId),
    CONSTRAINT	FK_OrderDetail_OrderForSeller_OrderId	FOREIGN KEY(OrderId) REFERENCES `Order`(OrderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_OrderDetail_OrderForSeller_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Seller(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE	OrderForCustomer(
	OrderId		bigint		NOT NULL,
    CustomerID	bigint			NOT NULL,
    `Name`		varchar(70) charset utf8	NULL,
    Address		varchar(2000) charset utf8	NOT NULL,
    PhoneNumber		varchar(15) charset utf8	NOT NULL,
    PostalCode		varchar(15) charset utf8	NOT NULL,
    DiscountAmount		bigint		NOT NULL	default(0),
    CONSTRAINT	PK_OrderDetail_OrderForCustomer_OrderId	PRIMARY KEY(OrderId),
    CONSTRAINT	FK_OrderDetail_OrderForCustomer_OrderId	FOREIGN KEY(OrderId) REFERENCES `Order`(OrderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_OrderDetail_OrderForCustomer_CustomerID	FOREIGN KEY(CustomerID) REFERENCES Customer(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE	OrderAndGoodInCart(
	OrderId		bigint		NOT NULL,
    GoodInCartId	bigint	NOT NULL,
    CONSTRAINT	FK_OrderDetail_OrderAndGoodInCart_OrderId	FOREIGN KEY(OrderId) REFERENCES `Order` (OrderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_OrderDetail_OrderAndGoodInCart_GoodInCartId	FOREIGN KEY(GoodInCartId) REFERENCES GoodInCart(GoodInCartId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	PK_OrderDetail_OrderAndGoodInCart_OrderId_GoodInCartId	PRIMARY KEY(OrderId, GoodInCartId) 
);


/*
====================================================================================================================
====================================================================================================================
=========================================================================================================OrderDetail
====================================================================================================================
*/




/*
====================================================================================================================
====================================================================================================SpecialSaleItems
====================================================================================================================
====================================================================================================================
*/


CREATE TABLE Discount
(
	DiscountId	bigint		NOT NULL	auto_increment,
	DiscountCode	varchar(15) charset utf8	NOT NULL	unique,
	Percent		int		NOT NULL,
	MaxDiscountAmount		bigint	NOT NULL,
	StartDate		date	NOT NULL,
	EndDate		date	NOT NULL,
	ModificationDate	date	NOT NULL DEFAULT(NOW()),
    CONSTRAINT	PK_SpecialSaleItems_Discount_DiscountId	PRIMARY KEY(DiscountId)
);


CREATE TABLE DiscountPerson
(
	DiscountId		bigint	NOT NULL,
	CustomerId	bigint	NOT NULL,
	RemainingNumber	int		NOT NULL,
    CONSTRAINT	FK_SpecialSaleItems_DiscountPerson_DiscountId	FOREIGN KEY(DiscountId)	REFERENCES	Discount(DiscountId) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT	FK_SpecialSaleItems_DiscountPerson_CustomerId	FOREIGN KEY(CustomerId)	REFERENCES Customer(PersonId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	PK_SpecialSaleItems_DiscountPerson_DiscountId_CustomerId	PRIMARY KEY(DiscountId,CustomerId)
);
	

CREATE TABLE Off
(
	OffID			bigint	NOT NULL	auto_increment,
	OffStatus		enum('VALIDATING', 'ACCEPTED', 'EDITING')	NOT NULL,
	SellerId			bigint	NOT NULL,
	StartFrom		date		NOT NULL,
	EndTo			date		NOT NULL,
	MaxDiscount		bigint		NOT NULL,
	DiscountPercent		int		NOT NULL,
	ModificationDate	date	NOT NULL	DEFAULT(NOW()),
	CONSTRAINT PK_SpecialSaleItems_Off_OffID	PRIMARY KEY(OffID),
    CONSTRAINT	FK_SpecialSaleItems_Off_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Seller(PersonId) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE OffAndGoods(
    OffId	bigint	NOT NULL,
    ProductId	bigint 	NOT NULL,
    CONSTRAINT	FK_SpecialSaleItems_OffAndGoods_OffId	FOREIGN KEY(OffId)	REFERENCES	Off(OffID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT	FK_SpecialSaleItems_OffAndGoods_ProductId	FOREIGN KEY(ProductId)	REFERENCES	Product(ProductID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT  PK_SpecialSaleItems_OffAndGoods_offAndGoodsId	PRIMARY KEY(OffId, ProductId)
);




/*
====================================================================================================================
====================================================================================================================
====================================================================================================SpecialSaleItems
====================================================================================================================
*/




/*
====================================================================================================================
============================================================================================================Requests
====================================================================================================================
====================================================================================================================
*/



CREATE TABLE Request
(
	RequestID		bigint	NOT NULL	auto_increment,
	ModificationDate	date	NOT NULL	DEFAULT(NOW()),
    CONSTRAINT PK_Requests_Request_RequestID	PRIMARY KEY(RequestID)
);


CREATE TABLE AddingCommentRequest
(
	RequestID		bigint		NOT NULL,
    CommentID		bigint 		NOT NULL,
    /*
	CustomerId	bigint	NOT NULL,
	ProductID		bigint			NOT NULL,
	Title			varchar(100) charset utf8	NOT NULL,
	Content		varchar(5000) charset utf8	NULL,
	isCommenterBoughtGood		bool	NOT NULL,
    */
    CONSTRAINT FK_Requests_AddingCommentRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_AddingCommentRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_AddingCommentRequest_CommentID	FOREIGN KEY(CommentID) REFERENCES	Comment(CommentID)
    /*
    CONSTRAINT	FK_Requests_AddingCommentRequest_CustomerId	FOREIGN KEY(CustomerId)	REFERENCES	Persons(PersonId),
    CONSTRAINT	FK_Requests_AddingCommentRequest_ProductID	FOREIGN KEY(ProductID) REFERENCES	Product(ProductID)
    */
);

CREATE TABLE RegisteringSellerRequest
(
	RequestID		bigint		NOT NULL,
    SellerId		bigint		NOT NULL,
    /*
	Username			varchar(70) charset utf8	NOT NULL,
	FirstName			varchar(50) charset utf8		NULL,
	LastName			varchar(60) charset utf8	NULL,
	Email				varchar(120) charset utf8	NULL,
	PhoneNumber		varchar(15) charset utf8		NULL,
	Password			varchar(40) charset utf8	NOT NULL,
	CompanyName		varchar(100) charset utf8	NOT NULL,
	CompanyWebsite		varchar(80) charset utf8	NULL,
	CompanyPhoneNumber	varchar(15) charset utf8	NULL,
	CompanyFaxNumber		varchar(15) charset utf8	NULL,
	CompanyAddress		varchar(300) charset utf8	NULL,
    */
	CONSTRAINT FK_RegisteringSellerRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_RegisteringSellerRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_RegisteringSellerRequest_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Persons(PersonId)
);

-- edit these tables
CREATE TABLE AddingGoodRequest(
	RequestID		bigint		NOT NULL,
    ProductID		bigint		NOT NULL,
    ProductSellerID		bigint	NOT NULL,
    /*
    GoodName	varchar(80) charset utf8	NOT NULL,
    GoodBrand	varchar(40) charset utf8	NOT NULL,
    GoodDescription	varchar(5000) charset utf8	NOT NULL,
    SubCategoryId	int		NOT NULL,
    SellerId	bigint		NOT NULL,
    price		bigint		NOT NULL,
    AvailableNumber	int		NOT NULL,
    */

    CONSTRAINT  FK_Requests_AddingGoodRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_AddingGoodRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_AddingGoodRequest_ProductID	FOREIGN KEY(ProductID)	REFERENCES	Product(ProductID),
    CONSTRAINT	FK_Requests_AddingGoodRequest_ProductSellerID	FOREIGN KEY(ProductSellerID)	REFERENCES	ProductAndSeller(ProductAndSellerId)
    /*
    CONSTRAINT	FK_Requests_AddingGoodRequest_SellerId	FOREIGN KEY(SellerId)	REFERENCES	Persons(PersonId),
    CONSTRAINT	FK_Requests_AddingGoodRequest_SubCategoryId	FOREIGN KEY(SubCategoryId)	REFERENCES	SubCategory(SubCategoryId)
    */
); 	


CREATE TABLE 	AddingOffRequest(
	RequestID		bigint		NOT NULL,
    OffID			bigint		NOT NULL,
    /*
    StartDate		date	NOT NULL,
    EndDate			date	NOT NULL,
    MaxDiscount		bigint		NOT NULL,
    DiscountPercent 	int		NOT NULL,
    SellerId		bigint		NOT NULL,
    */
	CONSTRAINT FK_Requests_AddingOffRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_AddingOffRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_AddingOffRequest_OffID	FOREIGN KEY(OffID)	REFERENCES	Off(OffID)
);


CREATE TABLE 	EditingGoodRequest(
	RequestID		bigint		NOT NULL,
    ProductId		bigint		NOT NULL,
    SellerId		bigint		NOT NULL,
    CONSTRAINT FK_Requests_EditingGoodRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_EditingGoodRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_EditingGoodRequest_SellerId		FOREIGN KEY(SellerId)	REFERENCES	Persons(PersonId),
    CONSTRAINT	FK_Requests_EditingGoodRequest_ProductID	FOREIGN KEY(ProductID) REFERENCES	Product(ProductID)
);

CREATE TABLE 	EditingOffRequest(
	RequestID		bigint		NOT NULL,
    OffID		bigint		NOT NULL,
    CONSTRAINT FK_Requests_EditingOffRequest_RequestID	FOREIGN KEY(RequestID)	REFERENCES	Request(RequestID),
    CONSTRAINT	PK_Requests_EditingOffRequest_RequestId	primary key(RequestId),
    CONSTRAINT	FK_Requests_EditingOffRequest_ProductID	FOREIGN KEY(OffID) REFERENCES	Off(OffID)
);


CREATE	TABLE 	editedFields(
	RequestID		bigint		NOT NULL,
    field		varchar(40) charset utf8	NOT NULL,
    `value`		varchar(80) charset utf8	NOT NULL,
    CONSTRAINT	FK_Requests_EditingOffRequest_ProductID	FOREIGN KEY(OffID) REFERENCES	Off(OffID)
);


/*
====================================================================================================================
====================================================================================================================
============================================================================================================Requests
====================================================================================================================
*/

insert into person.Company (CompanyID, Name, Website, PhoneNumber, FaxNumber, Address) values (1, 'Test', 'www.test.com', '09361457810', '66633333', 'sdsdsdsdsdsdsd sdsdds');
drop database onlineshopProject;
drop table Customer;