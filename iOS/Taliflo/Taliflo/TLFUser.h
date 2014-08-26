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
*streetAddress, *city, *state, *country, *zip, *summary, *description, *website, *profilePictureURL;
@property (nonatomic, assign) int totalDebits, totalCredits;
@property (nonatomic, assign) float balance, totalDebitsValue, totalCreditsValue;
@property (nonatomic, strong) NSArray *tags, *transactionsCreated, *transactionsAccepted, *supporters, *supportedCauses;

- (instancetype)initWithDictionary:(NSDictionary *)dict;

- (int)getSupportersCount;
- (int)getSupportedCausesCount;
- (NSString *)getTagsString;

@end
