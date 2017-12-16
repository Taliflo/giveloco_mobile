//
//  User.h
//  taliflo
//
//  Created by Ntokozo Radebe on 2014-11-04.
//  Copyright (c) 2014 taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class Transaction;

@interface User : NSManagedObject

@property (nonatomic, retain) NSString * userID;
@property (nonatomic, retain) NSString * role;
@property (nonatomic, retain) NSString * firstName;
@property (nonatomic, retain) NSString * lastName;
@property (nonatomic, retain) NSString * companyName;
@property (nonatomic, retain) NSString * email;
@property (nonatomic, retain) NSString * phone;
@property (nonatomic, retain) NSString * streetAddress;
@property (nonatomic, retain) NSString * city;
@property (nonatomic, retain) NSString * state;
@property (nonatomic, retain) NSString * country;
@property (nonatomic, retain) NSString * zip;
@property (nonatomic, retain) NSString * summary;
@property (nonatomic, retain) NSString * descript;
@property (nonatomic, retain) NSString * website;
@property (nonatomic, retain) NSString * pictureOriginalSized;
@property (nonatomic, retain) NSString * authenticationToken;
@property (nonatomic, retain) NSNumber * totalDebits;
@property (nonatomic, retain) NSNumber * totalCredits;
@property (nonatomic, retain) NSNumber * balance;
@property (nonatomic, retain) NSNumber * totalFundsRaised;
@property (nonatomic, retain) NSData * tags;
@property (nonatomic, retain) NSData * transactionsCreated;
@property (nonatomic, retain) NSData * transactionsAccepted;
@property (nonatomic, retain) NSData * supporters;
@property (nonatomic, retain) NSData * supportedCauses;
@property (nonatomic, retain) NSData * redeemableBusinesses;
@property (nonatomic, retain) NSData * donors;
@property (nonatomic, retain) NSData * transactionsAll;
@property (nonatomic, retain) NSString * pictureMediumSized;
@property (nonatomic, retain) NSString * pictureThumbnailSized;
@property (nonatomic, retain) NSDate * createdAt;
@property (nonatomic, retain) NSDate * updatedAt;
@property (nonatomic, retain) NSNumber * isPublished;
@property (nonatomic, retain) NSNumber * isFeatured;
@property (nonatomic, retain) NSSet *transactions;
@end

@interface User (CoreDataGeneratedAccessors)

- (void)addTransactionsObject:(Transaction *)value;
- (void)removeTransactionsObject:(Transaction *)value;
- (void)addTransactions:(NSSet *)values;
- (void)removeTransactions:(NSSet *)values;

@end
