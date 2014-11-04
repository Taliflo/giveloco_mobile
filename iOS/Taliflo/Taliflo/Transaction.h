//
//  Transaction.h
//  Taliflo
//
//  Created by Ntokozo Radebe on 2014-11-04.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

@class User;

@interface Transaction : NSManagedObject

@property (nonatomic, retain) NSNumber * transactionID;
@property (nonatomic, retain) NSNumber * stripeID;
@property (nonatomic, retain) NSNumber * fromUserID;
@property (nonatomic, retain) NSNumber * toUserID;
@property (nonatomic, retain) NSString * transactionType;
@property (nonatomic, retain) NSString * fromName;
@property (nonatomic, retain) NSString * toName;
@property (nonatomic, retain) NSString * fromUserRole;
@property (nonatomic, retain) NSString * toUserRole;
@property (nonatomic, retain) NSString * amount;
@property (nonatomic, retain) NSString * status;
@property (nonatomic, retain) NSDate * cancelledAt;
@property (nonatomic, retain) NSDate * completedAt;
@property (nonatomic, retain) NSDate * createdAt;
@property (nonatomic, retain) NSDate * updatedAt;
@property (nonatomic, retain) NSSet *users;
@end

@interface Transaction (CoreDataGeneratedAccessors)

- (void)addUsersObject:(User *)value;
- (void)removeUsersObject:(User *)value;
- (void)addUsers:(NSSet *)values;
- (void)removeUsers:(NSSet *)values;

@end
