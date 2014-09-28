//
//  TLFUser.h
//  Taliflo
//
//  Created by NR-Mac on 1/26/2014.
//  Copyright (c) 2014 Taliflo Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TLFUser : NSObject

@property (nonatomic, copy) NSString *ID, *role, *firstName, *lastName, *companyName, *email, *phone,
*streetAddress, *city, *state, *country, *zip, *summary, *descript, *website
, *profilePictureURL, *picOriginal, *picMedium, *picThumb, *authToken;
@property (nonatomic, copy) NSNumber *totalDebits, *totalCredits;
@property (nonatomic, copy) NSNumber *balance, *totalFundsRaised;
@property (nonatomic, strong) NSMutableArray *tags, *transactionsCreated, *transactionsAccepted, *supporters, *supportedCauses, *redeemableBusinesses;
@property (nonatomic, strong) NSMutableArray *transactionsAll;
@property (nonatomic, strong) NSDate *createdAt, *updatedAt;

- (instancetype)initWithDictionary:(NSDictionary *)dict;

- (int)getSupportersCount;
- (int)getSupportedCausesCount;
- (NSString *)getTagsString;
- (NSString *)getSupportersCountStr;
- (NSString *)getSupportedCausesStr;
- (NSArray *)getSupportedCauses;
- (NSArray *)getSupporters;
- (void)determineRedeemableBusinesses;
- (BOOL)checkReemableBusiness:(TLFUser *)business;

@end
